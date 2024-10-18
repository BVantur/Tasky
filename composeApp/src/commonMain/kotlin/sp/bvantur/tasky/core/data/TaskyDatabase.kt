package sp.bvantur.tasky.core.data

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import sp.bvantur.tasky.core.data.local.AttendeeDao
import sp.bvantur.tasky.core.data.local.AttendeeEntity
import sp.bvantur.tasky.core.data.local.EventDao
import sp.bvantur.tasky.core.data.local.EventEntity

@Database(
    entities = [AttendeeEntity::class, EventEntity::class],
    version = 1
)
@ConstructedBy(TaskyDatabaseConstructor::class)
abstract class TaskyDatabase : RoomDatabase() {
    abstract fun getAttendeeDao(): AttendeeDao
    abstract fun getEventDao(): EventDao
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
    }

    object EventAttendeeCrossRef {
        const val TABLE_NAME = "EventAttendeeCrossRefEntity"
    }
}
