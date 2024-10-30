package sp.bvantur.tasky.core.data

import sp.bvantur.tasky.core.data.local.EventEntity

expect class TaskySyncScheduler {

    suspend fun scheduleAgendaSync(events: List<EventEntity>)
}
