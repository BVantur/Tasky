package sp.bvantur.tasky.event.data.local

import androidx.room.immediateTransaction
import androidx.room.useWriterConnection
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

    suspend fun saveAttendees(attendees: List<AttendeeEntity>): TaskyEmptyResult<TaskyError> = try {
        database.getAttendeeDao().insertItems(attendees)
        TaskyResult.Success(Unit).asEmptyDataResult()
    } catch (ignore: SQLiteException) {
        TaskyResult.Error(TaskyError.SqlError)
    }

    fun getHostId(): String? = secureStorageProvider.kVault.string(SecurePersistentStorageProvider.USER_ID)

    suspend fun saveEventWithAttendees(
        event: EventEntity,
        attendees: List<AttendeeEntity>
    ): TaskyEmptyResult<TaskyError> = try {
        database.useWriterConnection {
            it.immediateTransaction {
                database.getEventDao().insert(event)
                database.getAttendeeDao().insertItems(attendees)
            }
        }
        TaskyResult.Success(Unit).asEmptyDataResult()
    } catch (ignore: SQLiteException) {
        TaskyResult.Error(TaskyError.SqlError)
    }
}
