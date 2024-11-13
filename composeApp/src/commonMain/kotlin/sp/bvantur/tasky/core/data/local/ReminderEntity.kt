package sp.bvantur.tasky.core.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import sp.bvantur.tasky.core.data.TaskyDatabaseConstants.Reminder

@Entity(tableName = Reminder.TABLE_NAME)
data class ReminderEntity(
    @PrimaryKey
    @ColumnInfo(name = Reminder.COLUMN_ID)
    val id: String,
    @ColumnInfo(name = Reminder.COLUMN_TITLE)
    val title: String,
    @ColumnInfo(name = Reminder.COLUMN_DESCRIPTION)
    val description: String,
    @ColumnInfo(name = Reminder.COLUMN_TIME)
    val time: Long,
    @ColumnInfo(name = Reminder.COLUMN_REMINDER)
    val reminder: Long,
    @ColumnInfo(name = Reminder.COLUMN_SYNC_STEP)
    val syncStep: SyncStep = SyncStep.FULL_SYNCED
)
