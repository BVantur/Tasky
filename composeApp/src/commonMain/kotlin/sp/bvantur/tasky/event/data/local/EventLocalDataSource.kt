package sp.bvantur.tasky.event.data.local

import sp.bvantur.tasky.core.data.TaskyDatabase
import sp.bvantur.tasky.core.data.local.AttendeeEntity
import sp.bvantur.tasky.core.data.local.EventEntity

class EventLocalDataSource(private val database: TaskyDatabase) {
    suspend fun saveAttendee(attendee: AttendeeEntity) {
        database.getAttendeeDao().insert(attendee)
    }

    suspend fun getAttendeeByEmail(email: String): AttendeeEntity? = database.getAttendeeDao().getAttendeeByEmail(email)

    suspend fun saveEvent(event: EventEntity) {
        database.getEventDao().insert(event)
    }
}
