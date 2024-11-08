package sp.bvantur.tasky.event.domain.model

import kotlinx.datetime.LocalDateTime

data class Event(
    val eventId: String,
    val title: String,
    val description: String,
    val fromTime: LocalDateTime,
    val toTime: LocalDateTime,
    val reminder: ReminderValue,
    val attendees: List<Attendee>
)
