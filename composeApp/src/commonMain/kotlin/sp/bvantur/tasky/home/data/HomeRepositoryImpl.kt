package sp.bvantur.tasky.home.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import sp.bvantur.tasky.core.data.mappers.asAttendeeEntity
import sp.bvantur.tasky.core.domain.CommunicationError
import sp.bvantur.tasky.core.domain.TaskyEmptyResult
import sp.bvantur.tasky.core.domain.asEmptyDataResult
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
    private val remoteDataSource: HomeRemoteDataSource
) : HomeRepository {
    override suspend fun getTodayAgenda(time: Long): TaskyEmptyResult<CommunicationError> =
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
}
