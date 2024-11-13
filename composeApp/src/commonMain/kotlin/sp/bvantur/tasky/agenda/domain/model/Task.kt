package sp.bvantur.tasky.agenda.domain.model

import kotlinx.datetime.LocalDateTime

data class Task(
    val taskId: String,
    val title: String,
    val description: String,
    val time: LocalDateTime,
    val reminder: ReminderValue,
    val isDone: Boolean
)
