package sp.bvantur.tasky.event.domain

import sp.bvantur.tasky.core.domain.CommunicationError
import sp.bvantur.tasky.core.domain.TaskyEmptyResult
import sp.bvantur.tasky.core.domain.TaskyResult
import sp.bvantur.tasky.event.domain.model.Attendee
import sp.bvantur.tasky.event.domain.model.Event

interface EventRepository {
    suspend fun getAttendee(email: String): TaskyResult<Attendee, CommunicationError>
    suspend fun createEvent(event: Event): TaskyEmptyResult<CommunicationError>
}
