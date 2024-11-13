package sp.bvantur.tasky.agenda.domain.model

import kotlinx.datetime.LocalDateTime

data class Reminder(
    val reminderId: String,
    val title: String,
    val description: String,
    val time: LocalDateTime,
    val reminder: ReminderValue
)
