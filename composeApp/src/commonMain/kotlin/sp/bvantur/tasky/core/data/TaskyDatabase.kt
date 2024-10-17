package sp.bvantur.tasky.core.data

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor

@Database(entities = [AttendeeEntity::class], version = 1)
@ConstructedBy(TaskyDatabaseConstructor::class)
abstract class TaskyDatabase : RoomDatabase() {
    abstract fun getAttendeeDao(): AttendeeDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object TaskyDatabaseConstructor : RoomDatabaseConstructor<TaskyDatabase> {
    override fun initialize(): TaskyDatabase
}

object TaskyDatabaseConstants {

    internal const val DB_FILE_NAME = "tasky.db"

    object Attendee {
        const val TABLE_NAME = "AttendeeEntity"

        const val COLUMN_STORY_ID = "id"
        const val COLUMN_STORY_EMAIL = "email"
    }
}
