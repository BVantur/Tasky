package sp.bvantur.tasky.event.data.mappers

import io.ktor.util.date.getTimeMillis
import sp.bvantur.tasky.event.data.remote.CreateEventRequest
import sp.bvantur.tasky.event.domain.model.Event
import sp.bvantur.tasky.event.presentation.utils.extensions.getMillis

fun Event.asCreateEventRequest(): CreateEventRequest {
    return CreateEventRequest(
        id = getTimeMillis().toString(), // TODO use UUID when update to Kotlin 2.0.20
        title = title,
        description = description,
        from = fromTime.getMillis(),
        to = toTime.getMillis(),
        remindAt = reminder.inMillis,
        attendeeIds = attendees.map { it.userId }
    )
}
