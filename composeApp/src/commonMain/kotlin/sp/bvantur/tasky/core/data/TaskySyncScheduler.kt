package sp.bvantur.tasky.core.data

expect class TaskySyncScheduler {

    suspend fun scheduleAgendaSync(eventIds: List<String>)
}
