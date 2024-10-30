package sp.bvantur.tasky.home.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import sp.bvantur.tasky.core.data.TaskySyncScheduler
import sp.bvantur.tasky.core.data.local.SyncStep
import sp.bvantur.tasky.core.data.mappers.asAttendeeEntity
import sp.bvantur.tasky.core.data.remote.EventRemoteDataSource
import sp.bvantur.tasky.core.domain.TaskyEmptyResult
import sp.bvantur.tasky.core.domain.TaskyError
import sp.bvantur.tasky.core.domain.asEmptyDataResult
import sp.bvantur.tasky.core.domain.getSuccessResultOrNull
import sp.bvantur.tasky.core.domain.isError
import sp.bvantur.tasky.core.domain.onError
import sp.bvantur.tasky.core.domain.onSuccess
import sp.bvantur.tasky.home.data.local.HomeLocalDataSource
import sp.bvantur.tasky.home.data.mappers.asAgendaItem
import sp.bvantur.tasky.home.data.mappers.asEventEntity
import sp.bvantur.tasky.home.data.remote.HomeRemoteDataSource
import sp.bvantur.tasky.home.domain.HomeRepository
import sp.bvantur.tasky.home.domain.model.AgendaItem

class HomeRepositoryImpl(
    private val localDataSource: HomeLocalDataSource,
    private val remoteDataSource: HomeRemoteDataSource,
    private val eventRemoteDataSource: EventRemoteDataSource,
    private val syncScheduler: TaskySyncScheduler
) : HomeRepository {
    override suspend fun getAgendaForTheDay(time: Long): TaskyEmptyResult<TaskyError> =
        remoteDataSource.getTodayAgenda(time).onSuccess { response ->
            val eventsAttendeesPair = response.events.map { event ->
                event.asEventEntity() to event.attendees.map { it.asAttendeeEntity() }
            }

            val events = eventsAttendeesPair.map { it.first }.toList()
            val attendees = eventsAttendeesPair.flatMap { it.second }.toList()

            localDataSource.storeEvents(events)
            localDataSource.storeAttendees(attendees)
        }.onError {
            // TODO handle error
            println(it.toString())
        }.asEmptyDataResult()

    override suspend fun observeAgendaItems(): Flow<List<AgendaItem>> = localDataSource.getEvents().map { items ->
        items.map { event ->
            event.asAgendaItem()
        }
    }

    override suspend fun syncPendingAgendaItems() {
        localDataSource.getPendingAgendaItems().collect { items ->
            syncScheduler.scheduleAgendaSync(items)
        }
    }

    override suspend fun getProfileName(): String? = localDataSource.getProfileName()

    override suspend fun logoutUser(): TaskyEmptyResult<TaskyError> = localDataSource.clearLocalData().onSuccess {
        remoteDataSource.logoutUser()
    }

    override suspend fun deleteEventById(id: String): TaskyEmptyResult<TaskyError> {
        val localResult = localDataSource.deleteEventById(id)

        if (localResult.isError()) {
            // TODO handle database error
            return localResult.asEmptyDataResult()
        }
        return eventRemoteDataSource.deleteEventById(id).onError {
            localDataSource.changeSyncStepForEvent(
                localResult.getSuccessResultOrNull() ?: return@onError,
                SyncStep.DELETE
            )
        }.asEmptyDataResult()
    }
}
