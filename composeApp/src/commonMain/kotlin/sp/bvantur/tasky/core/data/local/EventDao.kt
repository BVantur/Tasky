package sp.bvantur.tasky.core.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import sp.bvantur.tasky.core.data.TaskyDatabaseConstants.Event

@Suppress("MaximumLineLength")
@Dao
interface EventDao {
    @Upsert
    suspend fun insert(item: EventEntity)

    @Upsert
    suspend fun insertItems(items: List<EventEntity>)

    @Query(
        "SELECT * FROM ${Event.TABLE_NAME} WHERE ${Event.COLUMN_ID} == :id"
    )
    suspend fun getEventById(id: String): EventEntity?

    @Query("DELETE FROM ${Event.TABLE_NAME}")
    suspend fun removeAllEventData()

    @Query("DELETE FROM ${Event.TABLE_NAME} WHERE ${Event.COLUMN_ID} == :id")
    suspend fun removeById(id: String)

    @Query("SELECT * FROM ${Event.TABLE_NAME} WHERE ${Event.COLUMN_SYNC_STEP} != :syncStepDelete")
    fun getAllEvents(syncStepDelete: Int = SyncStep.DELETE.ordinal): Flow<List<EventEntity>>

    @Query("SELECT * FROM ${Event.TABLE_NAME} WHERE ${Event.COLUMN_SYNC_STEP} != :syncStepNone")
    fun getPendingEvents(syncStepNone: Int = SyncStep.FULL_SYNCED.ordinal): Flow<List<EventEntity>>
}
