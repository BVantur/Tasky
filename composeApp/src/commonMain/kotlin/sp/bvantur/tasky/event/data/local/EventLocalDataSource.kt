package sp.bvantur.tasky.event.data.local

import androidx.sqlite.SQLiteException
import sp.bvantur.tasky.core.data.TaskyDatabase
import sp.bvantur.tasky.core.data.local.AttendeeEntity
import sp.bvantur.tasky.core.data.local.EventEntity
import sp.bvantur.tasky.core.data.local.SecurePersistentStorageProvider
import sp.bvantur.tasky.core.domain.TaskyEmptyResult
import sp.bvantur.tasky.core.domain.TaskyError
import sp.bvantur.tasky.core.domain.TaskyResult
import sp.bvantur.tasky.core.domain.asEmptyDataResult

class EventLocalDataSource(
    private val database: TaskyDatabase,
    private val secureStorageProvider: SecurePersistentStorageProvider
) {
    suspend fun saveAttendee(attendee: AttendeeEntity) {
        database.getAttendeeDao().insert(attendee)
    }

    suspend fun getAttendeeByEmail(email: String): AttendeeEntity? = database.getAttendeeDao().getAttendeeByEmail(email)

    suspend fun saveEvent(event: EventEntity): TaskyEmptyResult<TaskyError> = try {
        database.getEventDao().insert(event)
        TaskyResult.Success(Unit).asEmptyDataResult()
    } catch (ignore: SQLiteException) {
        TaskyResult.Error(TaskyError.SqlError)
    }

    suspend fun removeEvent(event: EventEntity) {
        database.getEventDao().removeById(event.id)
    }

    suspend fun removeAttendeesByEventId(eventId: String) {
        database.getAttendeeDao().deleteByEventId(eventId)
    }

    suspend fun saveAttendees(attendees: List<AttendeeEntity>): TaskyEmptyResult<TaskyError> = try {
        database.getAttendeeDao().insertItems(attendees)
        TaskyResult.Success(Unit).asEmptyDataResult()
    } catch (ignore: SQLiteException) {
        TaskyResult.Error(TaskyError.SqlError)
    }

    fun getHostId(): String? = secureStorageProvider.kVault.string(SecurePersistentStorageProvider.USER_ID)
}
