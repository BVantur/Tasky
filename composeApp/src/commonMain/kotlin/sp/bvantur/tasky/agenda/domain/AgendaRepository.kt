package sp.bvantur.tasky.agenda.domain

import sp.bvantur.tasky.agenda.domain.model.Attendee
import sp.bvantur.tasky.agenda.domain.model.Event
import sp.bvantur.tasky.core.domain.TaskyEmptyResult
import sp.bvantur.tasky.core.domain.TaskyError
import sp.bvantur.tasky.core.domain.TaskyResult

interface AgendaRepository {
    suspend fun getAttendee(email: String): TaskyResult<Attendee, TaskyError>
    suspend fun createEvent(event: Event): TaskyEmptyResult<TaskyError>
    suspend fun getEventById(eventId: String): TaskyResult<Event?, TaskyError>
}
