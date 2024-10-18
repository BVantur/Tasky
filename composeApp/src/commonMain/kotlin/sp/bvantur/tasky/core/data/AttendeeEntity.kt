package sp.bvantur.tasky.core.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AttendeeEntity(
    @PrimaryKey
    @ColumnInfo(name = TaskyDatabaseConstants.Attendee.COLUMN_STORY_ID)
    val userId: String,
    val name: String,
    @ColumnInfo(name = TaskyDatabaseConstants.Attendee.COLUMN_STORY_EMAIL)
    val email: String
)
