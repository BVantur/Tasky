package sp.bvantur.tasky.event.data

import sp.bvantur.tasky.core.data.mappers.asAttendeeEntity
import sp.bvantur.tasky.core.data.remote.EventRemoteDataSource
import sp.bvantur.tasky.core.domain.TaskyEmptyResult
import sp.bvantur.tasky.core.domain.TaskyError
import sp.bvantur.tasky.core.domain.TaskyResult
import sp.bvantur.tasky.core.domain.asEmptyDataResult
import sp.bvantur.tasky.core.domain.isError
import sp.bvantur.tasky.core.domain.onError
import sp.bvantur.tasky.core.domain.onSuccess
import sp.bvantur.tasky.event.data.local.EventLocalDataSource
import sp.bvantur.tasky.event.data.mappers.asCreateEventRequest
import sp.bvantur.tasky.event.data.mappers.asEventEntity
import sp.bvantur.tasky.event.domain.EventRepository
import sp.bvantur.tasky.event.domain.model.Attendee
import sp.bvantur.tasky.event.domain.model.Event

class EventRepositoryImpl(
    private val remoteDataSource: EventRemoteDataSource,
    private val localDataSource: EventLocalDataSource
) : EventRepository {
    override suspend fun getAttendee(email: String): TaskyResult<Attendee, TaskyError> {
        val localData = localDataSource.getAttendeeByEmail(email)
        if (localData != null) {
            return TaskyResult.Success(
                Attendee(
                    userId = localData.userId,
                    name = localData.name,
                    email = localData.email
                )
            )
        }

        return when (val response = remoteDataSource.getAttendee(email)) {
            is TaskyResult.Error -> {
                response
            }

            is TaskyResult.Success -> {
                if (!response.data.userExists) {
                    TaskyResult.Error(TaskyError.UnknownError()) // TODO better error handling
                } else {
                    localDataSource.saveAttendee(response.data.attendee.asAttendeeEntity())
                    TaskyResult.Success(
                        Attendee(
                            userId = response.data.attendee.userId,
                            name = response.data.attendee.name,
                            email = response.data.attendee.email
                        )
                    )
                }
            }
        }
    }

    override suspend fun createEvent(event: Event): TaskyEmptyResult<TaskyError> {
        // TODO if hostId doesn't exist we need to logout before executing this action
        val eventEntity = event.asEventEntity(localDataSource.getHostId() ?: "")
        val attendeeEntities = event.attendees.map {
            it.asAttendeeEntity(
                eventId = eventEntity.id,
                remindAt = eventEntity.remindAt
            )
        }
        val saveEventResponse = localDataSource.saveEvent(eventEntity)
        val saveAttendeesResponse = localDataSource.saveAttendees(attendeeEntities)

        if (saveEventResponse.isError() || saveAttendeesResponse.isError()) {
            localDataSource.removeEvent(eventEntity)
            localDataSource.removeAttendeesByEventId(eventEntity.id)
            return saveEventResponse.asEmptyDataResult()
        }

        return remoteDataSource.createEvent(
            event.asCreateEventRequest(
                eventId = eventEntity.id
            )
        ).onError {
            localDataSource.saveEvent(eventEntity.copy(isSynced = false))
            localDataSource.saveAttendees(attendeeEntities.map { it.copy(isSynced = false) })
        }.onSuccess {
            localDataSource.saveEvent(eventEntity.copy(isSynced = true))
            localDataSource.saveAttendees(attendeeEntities.map { it.copy(isSynced = false) })
        }.asEmptyDataResult()
    }
}
