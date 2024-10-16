package sp.bvantur.tasky.event.data

import kotlinx.coroutines.withContext
import sp.bvantur.tasky.core.data.AttendeeEntity
import sp.bvantur.tasky.core.data.TaskyDatabase
import sp.bvantur.tasky.core.domain.DispatcherProvider

class EventLocalDataSource(private val database: TaskyDatabase, private val dispatcherProvider: DispatcherProvider) {
    suspend fun saveAttendee(attendee: AttendeeEntity) {
        withContext(dispatcherProvider.io) {
            database.getAttendeeDao().insert(attendee)
        }
    }

    suspend fun getAttendeeByEmail(email: String): AttendeeEntity? = withContext(dispatcherProvider.io) {
        database.getAttendeeDao().getAttendeeByEmail(email)
    }
}
