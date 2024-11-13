package sp.bvantur.tasky.agenda.data.local

import androidx.room.immediateTransaction
import androidx.room.useWriterConnection
import androidx.sqlite.SQLiteException
import sp.bvantur.tasky.core.data.TaskyDatabase
import sp.bvantur.tasky.core.data.local.AttendeeEntity
import sp.bvantur.tasky.core.data.local.EventEntity
import sp.bvantur.tasky.core.data.local.ReminderEntity
import sp.bvantur.tasky.core.data.local.SecurePersistentStorageProvider
import sp.bvantur.tasky.core.data.local.TaskEntity
import sp.bvantur.tasky.core.domain.TaskyEmptyResult
import sp.bvantur.tasky.core.domain.TaskyError
import sp.bvantur.tasky.core.domain.TaskyResult
import sp.bvantur.tasky.core.domain.asEmptyDataResult

class AgendaLocalDataSource(
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
            // TODO keep an eye on this ticket: https://issuetracker.google.com/issues/340606803#comment2
            database.invalidationTracker.refreshAsync() // TODO remove when this is fixed for KMP
        }
        TaskyResult.Success(Unit).asEmptyDataResult()
    } catch (ignore: SQLiteException) {
        TaskyResult.Error(TaskyError.SqlError)
    }

    suspend fun getEventWithAttendeesById(
        eventId: String
    ): TaskyResult<Pair<EventEntity?, List<AttendeeEntity>>, TaskyError> {
        try {
            var event: EventEntity? = null
            var attendees: List<AttendeeEntity> = emptyList()
            database.useWriterConnection {
                it.immediateTransaction {
                    event = database.getEventDao().getEventById(eventId)
                    attendees = database.getAttendeeDao().getAttendeesByEventId(eventId)
                }
                // TODO keep an eye on this ticket: https://issuetracker.google.com/issues/340606803#comment2
                database.invalidationTracker.refreshAsync() // TODO remove when this is fixed for KMP
            }

            return TaskyResult.Success(event to attendees)
        } catch (ignore: SQLiteException) {
            return TaskyResult.Error(TaskyError.SqlError)
        }
    }

    suspend fun saveTask(taskEntity: TaskEntity): TaskyEmptyResult<TaskyError> = try {
        database.getTaskDao().insert(taskEntity)
        TaskyResult.Success(Unit).asEmptyDataResult()
    } catch (ignore: SQLiteException) {
        TaskyResult.Error(TaskyError.SqlError)
    }

    suspend fun saveReminder(reminderEntity: ReminderEntity): TaskyEmptyResult<TaskyError> = try {
        database.getReminderDao().insert(reminderEntity)
        TaskyResult.Success(Unit).asEmptyDataResult()
    } catch (ignore: SQLiteException) {
        TaskyResult.Error(TaskyError.SqlError)
    }
}
