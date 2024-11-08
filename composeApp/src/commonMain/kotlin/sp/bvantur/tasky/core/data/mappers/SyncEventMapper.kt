package sp.bvantur.tasky.core.data.mappers

import sp.bvantur.tasky.agenda.data.remote.CreateEventRequest
import sp.bvantur.tasky.core.data.local.EventEntity

fun EventEntity.asCreateEventRequest(attendeeId: List<String>): CreateEventRequest = CreateEventRequest(
    id = id,
    title = title,
    description = description,
    from = from,
    to = to,
    remindAt = remindAt,
    attendeeIds = attendeeId
)
