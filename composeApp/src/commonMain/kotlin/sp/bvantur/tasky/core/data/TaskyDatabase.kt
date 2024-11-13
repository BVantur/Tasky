package sp.bvantur.tasky.core.data

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.room.immediateTransaction
import androidx.room.useWriterConnection
import androidx.sqlite.SQLiteException
import sp.bvantur.tasky.core.data.local.AttendeeDao
import sp.bvantur.tasky.core.data.local.AttendeeEntity
import sp.bvantur.tasky.core.data.local.EventDao
import sp.bvantur.tasky.core.data.local.EventEntity
import sp.bvantur.tasky.core.data.local.ReminderDao
import sp.bvantur.tasky.core.data.local.ReminderEntity
import sp.bvantur.tasky.core.data.local.TaskDao
import sp.bvantur.tasky.core.data.local.TaskEntity
import sp.bvantur.tasky.core.data.local.converter.SyncStepConverter
import sp.bvantur.tasky.core.domain.TaskyError
import sp.bvantur.tasky.core.domain.TaskyResult
import sp.bvantur.tasky.core.domain.asEmptyDataResult

@Database(
    entities = [AttendeeEntity::class, EventEntity::class, TaskEntity::class, ReminderEntity::class],
    version = 4
)
@TypeConverters(SyncStepConverter::class)
@ConstructedBy(TaskyDatabaseConstructor::class)
abstract class TaskyDatabase : RoomDatabase() {
    abstract fun getAttendeeDao(): AttendeeDao
    abstract fun getEventDao(): EventDao
    abstract fun getTaskDao(): TaskDao
    abstract fun getReminderDao(): ReminderDao

    suspend fun clearDatabase(): TaskyResult<Unit, TaskyError> = try {
        useWriterConnection {
            it.immediateTransaction {
                getAttendeeDao().removeAllAttendeeData()
                getEventDao().removeAllEventData()
            }
            // TODO keep an eye on this ticket: https://issuetracker.google.com/issues/340606803#comment2
            invalidationTracker.refreshAsync() // TODO remove when this is fixed for KMP
        }
        TaskyResult.Success(Unit).asEmptyDataResult()
    } catch (ignore: SQLiteException) {
        TaskyResult.Error(TaskyError.SqlError)
    }
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object TaskyDatabaseConstructor : RoomDatabaseConstructor<TaskyDatabase> {
    override fun initialize(): TaskyDatabase
}

object TaskyDatabaseConstants {

    internal const val DB_FILE_NAME = "tasky.db"

    object Attendee {
        const val TABLE_NAME = "AttendeeEntity"

        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_EVENT_ID = "eventId"
        const val COLUMN_IS_GOING = "isGoing"
        const val COLUMN_REMIND_AT = "remindAt"
    }

    object Event {
        const val TABLE_NAME = "EventEntity"

        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_FROM = "from"
        const val COLUMN_TO = "to"
        const val COLUMN_REMIND_AT = "remindAt"
        const val COLUMN_HOST = "host"
        const val COLUMN_IS_USER_EVENT_CREATOR = "isUserEventCreator"
        const val COLUMN_SYNC_STEP = "syncStep"
    }

    object Task {
        const val TABLE_NAME = "TaskEntity"

        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_TIME = "time"
        const val COLUMN_REMINDER = "reminder"
        const val COLUMN_IS_DONE = "isDone"
        const val COLUMN_SYNC_STEP = "syncStep"
    }

    object Reminder {
        const val TABLE_NAME = "ReminderEntity"

        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_TIME = "time"
        const val COLUMN_REMINDER = "reminder"
        const val COLUMN_SYNC_STEP = "syncStep"
    }
}
