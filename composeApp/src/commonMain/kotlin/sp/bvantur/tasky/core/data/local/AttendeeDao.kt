package sp.bvantur.tasky.core.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import sp.bvantur.tasky.core.data.TaskyDatabaseConstants

@Suppress("MaximumLineLength")
@Dao
interface AttendeeDao {
    @Upsert
    suspend fun insert(item: AttendeeEntity)

    @Upsert
    suspend fun insertItems(items: List<AttendeeEntity>)

    @Query(
        "SELECT * FROM ${TaskyDatabaseConstants.Attendee.TABLE_NAME} WHERE ${TaskyDatabaseConstants.Attendee.COLUMN_EMAIL} == :email"
    )
    suspend fun getAttendeeByEmail(email: String): AttendeeEntity?

    @Query("DELETE FROM ${TaskyDatabaseConstants.Attendee.TABLE_NAME}")
    suspend fun removeAttendeeData()
}
