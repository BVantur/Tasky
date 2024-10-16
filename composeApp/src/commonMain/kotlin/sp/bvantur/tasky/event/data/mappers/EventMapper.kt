package sp.bvantur.tasky.event.data.mappers

import sp.bvantur.tasky.event.data.model.CreateEventRequest
import sp.bvantur.tasky.event.domain.model.Event
import sp.bvantur.tasky.event.presentation.utils.extensions.getMillis
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun Event.asCreateEventRequest(): CreateEventRequest {
    val fromInMillis = fromTime.getMillis()
    return CreateEventRequest(
        id = Uuid.random().toString(),
        title = title,
        description = description,
        from = fromTime.getMillis(),
        to = toTime.getMillis(),
        remindAt = reminder.toMillis(fromInMillis),
        attendeeIds = attendees.map { it.userId }
    )
}
