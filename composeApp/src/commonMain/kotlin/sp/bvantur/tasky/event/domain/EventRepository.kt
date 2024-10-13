package sp.bvantur.tasky.event.domain

import sp.bvantur.tasky.core.domain.CommunicationError
import sp.bvantur.tasky.core.domain.TaskyResult
import sp.bvantur.tasky.event.domain.model.Attendee

interface EventRepository {
    suspend fun getAttendee(email: String): TaskyResult<Attendee, CommunicationError>
}
