package sp.bvantur.tasky.core.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import sp.bvantur.tasky.core.data.TaskyDatabaseConstants

@Entity(tableName = TaskyDatabaseConstants.Task.TABLE_NAME)
data class TaskEntity(
    @PrimaryKey
    @ColumnInfo(name = TaskyDatabaseConstants.Task.COLUMN_ID)
    val id: String,
    @ColumnInfo(name = TaskyDatabaseConstants.Task.COLUMN_TITLE)
    val title: String,
    @ColumnInfo(name = TaskyDatabaseConstants.Task.COLUMN_DESCRIPTION)
    val description: String,
    @ColumnInfo(name = TaskyDatabaseConstants.Task.COLUMN_TIME)
    val time: Long,
    @ColumnInfo(name = TaskyDatabaseConstants.Task.COLUMN_REMINDER)
    val reminder: Long,
    @ColumnInfo(name = TaskyDatabaseConstants.Task.COLUMN_SYNC_STEP)
    val syncStep: SyncStep = SyncStep.FULL_SYNCED
)
