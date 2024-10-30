package sp.bvantur.tasky.core.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import sp.bvantur.tasky.core.data.TaskyDatabaseConstants.Attendee

@Suppress("MaximumLineLength")
@Dao
interface AttendeeDao {
    @Upsert
    suspend fun insert(item: AttendeeEntity)

    @Upsert
    suspend fun insertItems(items: List<AttendeeEntity>)

    @Query(
        "SELECT * FROM ${Attendee.TABLE_NAME} WHERE ${Attendee.COLUMN_EMAIL} == :email"
    )
    suspend fun getAttendeeByEmail(email: String): AttendeeEntity?

    @Query("DELETE FROM ${Attendee.TABLE_NAME}")
    suspend fun removeAllAttendeeData()

    @Query("SELECT * FROM ${Attendee.TABLE_NAME} WHERE ${Attendee.COLUMN_EVENT_ID} == :eventId")
    suspend fun getAttendeesByEventId(eventId: String): List<AttendeeEntity>

    @Query("DELETE FROM ${Attendee.TABLE_NAME} WHERE ${Attendee.COLUMN_EVENT_ID} == :id")
    suspend fun removeByEventId(id: String)
}
