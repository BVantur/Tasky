package sp.bvantur.tasky.core.data.mappers

import sp.bvantur.tasky.core.data.local.EventEntity
import sp.bvantur.tasky.event.data.remote.CreateEventRequest

fun EventEntity.asCreateEventRequest(attendeeId: List<String>): CreateEventRequest = CreateEventRequest(
    id = id,
    title = title,
    description = description,
    from = from,
    to = to,
    remindAt = remindAt,
    attendeeIds = attendeeId
)
