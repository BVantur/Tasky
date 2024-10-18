package sp.bvantur.tasky.core.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Suppress("MaximumLineLength")
@Dao
interface AttendeeDao {
    @Upsert
    suspend fun insert(item: AttendeeEntity)

    @Query(
        "SELECT * FROM ${TaskyDatabaseConstants.Attendee.TABLE_NAME} WHERE ${TaskyDatabaseConstants.Attendee.COLUMN_STORY_EMAIL} == :email"
    )
    suspend fun getAttendeeByEmail(email: String): AttendeeEntity?

    @Query("DELETE FROM ${TaskyDatabaseConstants.Attendee.TABLE_NAME}")
    suspend fun removeAttendeeData()
}
