package sp.bvantur.tasky.core.data.local

import androidx.room.Dao
import androidx.room.Upsert

@Suppress("MaximumLineLength")
@Dao
interface TaskDao {
    @Upsert
    suspend fun insert(item: TaskEntity)
}
