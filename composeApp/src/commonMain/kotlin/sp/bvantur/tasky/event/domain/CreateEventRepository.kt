package sp.bvantur.tasky.event.domain

import sp.bvantur.tasky.event.domain.model.Attendee

interface CreateEventRepository {
    suspend fun getAttendee(email: String): Result<Attendee?>
}
