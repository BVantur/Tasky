package sp.bvantur.tasky.core.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import sp.bvantur.tasky.core.data.TaskyDatabaseConstants

@Entity(tableName = TaskyDatabaseConstants.Event.TABLE_NAME)
data class EventEntity(
    @PrimaryKey
    @ColumnInfo(name = TaskyDatabaseConstants.Event.COLUMN_ID)
    val id: String,
    @ColumnInfo(name = TaskyDatabaseConstants.Event.COLUMN_TITLE)
    val title: String,
    @ColumnInfo(name = TaskyDatabaseConstants.Event.COLUMN_DESCRIPTION)
    val description: String,
    @ColumnInfo(name = TaskyDatabaseConstants.Event.COLUMN_FROM)
    val from: Long,
    @ColumnInfo(name = TaskyDatabaseConstants.Event.COLUMN_TO)
    val to: Long,
    @ColumnInfo(name = TaskyDatabaseConstants.Event.COLUMN_REMIND_AT)
    val remindAt: Long,
    @ColumnInfo(name = TaskyDatabaseConstants.Event.COLUMN_HOST)
    val host: String,
    @ColumnInfo(name = TaskyDatabaseConstants.Event.COLUMN_IS_USER_EVENT_CREATOR)
    val isUserEventCreator: Boolean,
    @ColumnInfo(name = TaskyDatabaseConstants.Event.COLUMN_IS_SYNCED)
    val isSynced: Boolean? = null
)
