package sp.bvantur.tasky.core.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import sp.bvantur.tasky.core.data.TaskyDatabaseConstants
import sp.bvantur.tasky.event.domain.model.ReminderValue

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
    val isGoing: Boolean = false,
    @ColumnInfo(name = TaskyDatabaseConstants.Attendee.COLUMN_REMIND_AT)
    val remindAt: Long = ReminderValue.THIRTY_MINUTES.inMillis,
    @ColumnInfo(name = TaskyDatabaseConstants.Event.COLUMN_IS_SYNCED)
    val isSynced: Boolean? = null
)
