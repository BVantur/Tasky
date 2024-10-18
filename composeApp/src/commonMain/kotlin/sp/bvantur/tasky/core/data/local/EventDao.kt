package sp.bvantur.tasky.core.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import sp.bvantur.tasky.core.data.TaskyDatabaseConstants

@Suppress("MaximumLineLength")
@Dao
interface EventDao {
    @Upsert
    suspend fun insert(item: EventEntity)

    @Upsert
    suspend fun insertItems(items: List<EventEntity>)

    @Query(
        "SELECT * FROM ${TaskyDatabaseConstants.Event.TABLE_NAME} WHERE ${TaskyDatabaseConstants.Event.COLUMN_ID} == :id"
    )
    suspend fun getEventById(id: String): EventEntity?

    @Query("DELETE FROM ${TaskyDatabaseConstants.Event.TABLE_NAME}")
    suspend fun removeEventData()

    @Query("SELECT * FROM ${TaskyDatabaseConstants.Event.TABLE_NAME}")
    fun getAllEvents(): Flow<List<EventEntity>>
}
