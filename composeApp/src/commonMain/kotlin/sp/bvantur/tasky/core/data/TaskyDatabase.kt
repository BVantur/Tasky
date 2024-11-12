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
import sp.bvantur.tasky.core.data.local.converter.SyncStepConverter
import sp.bvantur.tasky.core.domain.TaskyError
import sp.bvantur.tasky.core.domain.TaskyResult
import sp.bvantur.tasky.core.domain.asEmptyDataResult

@Database(
    entities = [AttendeeEntity::class, EventEntity::class],
    version = 2
)
@TypeConverters(SyncStepConverter::class)
@ConstructedBy(TaskyDatabaseConstructor::class)
abstract class TaskyDatabase : RoomDatabase() {
    abstract fun getAttendeeDao(): AttendeeDao
    abstract fun getEventDao(): EventDao

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

        const val COLUMN_ID = "attendeeId"
        const val COLUMN_NAME = "attendeeName"
        const val COLUMN_EMAIL = "attendeeEmail"
        const val COLUMN_EVENT_ID = "attendeeEventId"
        const val COLUMN_IS_GOING = "attendeeIsGoing"
        const val COLUMN_REMIND_AT = "attendeeRemindAt"
    }

    object Event {
        const val TABLE_NAME = "EventEntity"

        const val COLUMN_ID = "eventId"
        const val COLUMN_TITLE = "eventTitle"
        const val COLUMN_DESCRIPTION = "eventDescription"
        const val COLUMN_FROM = "eventFrom"
        const val COLUMN_TO = "eventTo"
        const val COLUMN_REMIND_AT = "eventRemindAt"
        const val COLUMN_HOST = "eventHost"
        const val COLUMN_IS_USER_EVENT_CREATOR = "eventIsUserEventCreator"
        const val COLUMN_SYNC_STEP = "sync_step"
    }
}
