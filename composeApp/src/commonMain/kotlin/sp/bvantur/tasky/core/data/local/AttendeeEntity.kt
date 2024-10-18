package sp.bvantur.tasky.core.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import sp.bvantur.tasky.core.data.TaskyDatabaseConstants

@Entity(tableName = TaskyDatabaseConstants.Attendee.TABLE_NAME)
data class AttendeeEntity(
    @PrimaryKey
    @ColumnInfo(name = TaskyDatabaseConstants.Attendee.COLUMN_ID)
    val userId: String,
    @ColumnInfo(name = TaskyDatabaseConstants.Attendee.COLUMN_NAME)
    val name: String,
    @ColumnInfo(name = TaskyDatabaseConstants.Attendee.COLUMN_EMAIL)
    val email: String,
    @ColumnInfo(name = TaskyDatabaseConstants.Attendee.COLUMN_EVENT_ID)
    val eventId: String? = null,
    @ColumnInfo(name = TaskyDatabaseConstants.Attendee.COLUMN_IS_GOING)
    val isGoing: Boolean? = null,
    @ColumnInfo(name = TaskyDatabaseConstants.Attendee.COLUMN_REMIND_AT)
    val remindAt: Long? = null
)
