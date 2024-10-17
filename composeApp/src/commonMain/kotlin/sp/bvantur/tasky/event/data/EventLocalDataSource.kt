package sp.bvantur.tasky.event.data

import sp.bvantur.tasky.core.data.AttendeeEntity
import sp.bvantur.tasky.core.data.TaskyDatabase

class EventLocalDataSource(private val database: TaskyDatabase) {
    suspend fun saveAttendee(attendee: AttendeeEntity) {
        database.getAttendeeDao().insert(attendee)
    }

    suspend fun getAttendeeByEmail(email: String): AttendeeEntity? = database.getAttendeeDao().getAttendeeByEmail(email)
}
