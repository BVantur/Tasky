package sp.bvantur.tasky.home.data.local

import androidx.room.immediateTransaction
import androidx.room.useWriterConnection
import androidx.sqlite.SQLiteException
import kotlinx.coroutines.flow.Flow
import sp.bvantur.tasky.core.data.TaskyDatabase
import sp.bvantur.tasky.core.data.local.AttendeeEntity
import sp.bvantur.tasky.core.data.local.EventEntity
import sp.bvantur.tasky.core.data.local.SecurePersistentStorageProvider
import sp.bvantur.tasky.core.data.local.SyncStep
import sp.bvantur.tasky.core.domain.TaskyEmptyResult
import sp.bvantur.tasky.core.domain.TaskyError
import sp.bvantur.tasky.core.domain.TaskyResult
import sp.bvantur.tasky.core.domain.asEmptyDataResult
import sp.bvantur.tasky.core.domain.onSuccess

class HomeLocalDataSource(
    private val database: TaskyDatabase,
    private val securePersistentStorageProvider: SecurePersistentStorageProvider
) {

    suspend fun storeEvents(event: List<EventEntity>) {
        database.getEventDao().insertItems(event)
    }

    suspend fun storeAttendees(attendees: List<AttendeeEntity>) {
        database.getAttendeeDao().insertItems(attendees)
    }

    fun getEvents(): Flow<List<EventEntity>> = database.getEventDao().getAllEvents()

    fun getPendingEventItems(): Flow<List<EventEntity>> = database.getEventDao().getPendingEvents()

    fun getProfileName(): String? = securePersistentStorageProvider.kVault.string(SecurePersistentStorageProvider.NAME)

    suspend fun clearLocalData(): TaskyResult<Unit, TaskyError> = database.clearDatabase().onSuccess {
        securePersistentStorageProvider.kVault.clear()
    }

    suspend fun deleteEventById(id: String): TaskyResult<EventEntity?, TaskyError> = try {
        var eventEntity: EventEntity? = null

        database.useWriterConnection {
            it.immediateTransaction {
                eventEntity = database.getEventDao().getEventById(id)
                database.getEventDao().removeById(id)
                database.getAttendeeDao().removeByEventId(id)
            }
            // TODO keep an eye on this ticket: https://issuetracker.google.com/issues/340606803#comment2
            database.invalidationTracker.refreshAsync() // TODO remove when this is fixed for KMP
        }
        TaskyResult.Success(eventEntity)
    } catch (ignore: SQLiteException) {
        TaskyResult.Error(TaskyError.SqlError)
    }

    suspend fun changeSyncStepForEvent(eventEntity: EventEntity, syncStep: SyncStep): TaskyEmptyResult<TaskyError> =
        try {
            database.getEventDao().insert(eventEntity.copy(syncStep = syncStep))
            TaskyResult.Success(Unit).asEmptyDataResult()
        } catch (ignore: SQLiteException) {
            TaskyResult.Error(TaskyError.SqlError)
        }
}
