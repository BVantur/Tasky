package sp.bvantur.tasky.event.data.mappers

import sp.bvantur.tasky.core.data.local.EventEntity
import sp.bvantur.tasky.core.domain.extensions.getMillis
import sp.bvantur.tasky.event.data.remote.CreateEventRequest
import sp.bvantur.tasky.event.data.utils.EventUtils
import sp.bvantur.tasky.event.domain.model.Event

fun Event.asCreateEventRequest(eventId: String): CreateEventRequest = CreateEventRequest(
    id = eventId,
    title = title,
    description = description,
    from = fromTime.getMillis(),
    to = toTime.getMillis(),
    remindAt = reminder.inMillis,
    attendeeIds = attendees.map { it.userId }
)

fun Event.asEventEntity(hostId: String, isUserEventCreator: Boolean = true, isSynced: Boolean? = null): EventEntity =
    EventEntity(
        id = EventUtils.generateEventId(),
        title = title,
        description = description,
        from = fromTime.getMillis(),
        to = toTime.getMillis(),
        remindAt = reminder.inMillis,
        host = hostId,
        isUserEventCreator = isUserEventCreator,
        isSynced = isSynced
    )
