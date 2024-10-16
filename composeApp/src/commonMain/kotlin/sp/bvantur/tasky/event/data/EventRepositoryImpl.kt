package sp.bvantur.tasky.event.data

import sp.bvantur.tasky.core.domain.CommunicationError
import sp.bvantur.tasky.core.domain.TaskyEmptyResult
import sp.bvantur.tasky.core.domain.TaskyResult
import sp.bvantur.tasky.event.data.mappers.asCreateEventRequest
import sp.bvantur.tasky.event.domain.EventRepository
import sp.bvantur.tasky.event.domain.model.Attendee
import sp.bvantur.tasky.event.domain.model.Event

class EventRepositoryImpl(private val remoteDataSource: EventRemoteDataSource) : EventRepository {
    override suspend fun getAttendee(email: String): TaskyResult<Attendee, CommunicationError> =
        when (val response = remoteDataSource.getAttendee(email)) {
            is TaskyResult.Error -> {
                response
            }
            is TaskyResult.Success -> {
                if (!response.data.userExists) {
                    TaskyResult.Error(CommunicationError.UnknownError()) // TODO better error handling
                } else {
                    // TODO store data to database
                    TaskyResult.Success(
                        Attendee(
                            userId = response.data.attendee.userId,
                            name = response.data.attendee.name
                        )
                    )
                }
            }
        }

    override suspend fun createEvent(event: Event): TaskyEmptyResult<CommunicationError> =
        remoteDataSource.createEvent(event.asCreateEventRequest()) // TODO store data to database
}
