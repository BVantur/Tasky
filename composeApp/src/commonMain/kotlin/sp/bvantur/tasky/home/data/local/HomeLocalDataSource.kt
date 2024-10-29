package sp.bvantur.tasky.home.data.local

import kotlinx.coroutines.flow.Flow
import sp.bvantur.tasky.core.data.TaskyDatabase
import sp.bvantur.tasky.core.data.local.AttendeeEntity
import sp.bvantur.tasky.core.data.local.EventEntity
import sp.bvantur.tasky.core.data.local.SecurePersistentStorageProvider
import sp.bvantur.tasky.core.domain.TaskyError
import sp.bvantur.tasky.core.domain.TaskyResult
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

    fun getPendingAgendaItems(): Flow<List<EventEntity>> = database.getEventDao().getPendingEvents()

    fun getProfileName(): String? = securePersistentStorageProvider.kVault.string(SecurePersistentStorageProvider.NAME)

    suspend fun clearLocalData(): TaskyResult<Unit, TaskyError> = database.clearDatabase().onSuccess {
        securePersistentStorageProvider.kVault.clear()
    }
}
