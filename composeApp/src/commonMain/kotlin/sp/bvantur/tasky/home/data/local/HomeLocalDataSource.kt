package sp.bvantur.tasky.home.data.local

import kotlinx.coroutines.flow.Flow
import sp.bvantur.tasky.core.data.TaskyDatabase
import sp.bvantur.tasky.core.data.local.AttendeeEntity
import sp.bvantur.tasky.core.data.local.EventEntity

class HomeLocalDataSource(private val database: TaskyDatabase) {

    suspend fun storeEvents(event: List<EventEntity>) {
        database.getEventDao().insertItems(event)
    }

    suspend fun storeAttendees(attendees: List<AttendeeEntity>) {
        database.getAttendeeDao().insertItems(attendees)
    }

    suspend fun getEvents(): Flow<List<EventEntity>> = database.getEventDao().getAllEvents()
}
