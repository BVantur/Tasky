package sp.bvantur.tasky.core.data.local

import androidx.room.Dao
import androidx.room.Upsert

@Dao
interface ReminderDao {
    @Upsert
    suspend fun insert(item: ReminderEntity)
}
