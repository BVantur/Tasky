package sp.bvantur.tasky.agenda.data.mappers

import sp.bvantur.tasky.agenda.data.remote.CreateEventRequest
import sp.bvantur.tasky.agenda.data.utils.AgendaUtils
import sp.bvantur.tasky.agenda.domain.model.Attendee
import sp.bvantur.tasky.agenda.domain.model.Event
import sp.bvantur.tasky.agenda.domain.model.ReminderValue
import sp.bvantur.tasky.core.data.local.AttendeeEntity
import sp.bvantur.tasky.core.data.local.EventEntity
import sp.bvantur.tasky.core.data.local.SyncStep
import sp.bvantur.tasky.core.domain.extensions.getMillis

fun Event.asCreateEventRequest(eventId: String): CreateEventRequest = CreateEventRequest(
    id = eventId,
    title = title,
    description = description,
    from = fromTime.getMillis(),
    to = toTime.getMillis(),
    remindAt = reminder.inMillis,
    attendeeIds = attendees.map { it.userId }
)

fun Event.asEventEntity(
    hostId: String,
    isUserEventCreator: Boolean = true,
    syncStep: SyncStep = SyncStep.NONE
): EventEntity = EventEntity(
    id = eventId,
    title = title,
    description = description,
    from = fromTime.getMillis(),
    to = toTime.getMillis(),
    remindAt = reminder.inMillis,
    host = hostId,
    isUserEventCreator = isUserEventCreator,
    syncStep = syncStep
)

fun EventEntity.asEvent(attendees: List<Attendee>): Event = Event(
    eventId = id,
    title = title,
    description = description,
    fromTime = AgendaUtils.toLocalDateTime(from),
    toTime = AgendaUtils.toLocalDateTime(to),
    reminder = ReminderValue.fromMillisToReminderValue(remindAt),
    attendees = attendees
)

fun AttendeeEntity.asAttendee(): Attendee = Attendee(
    userId = userId,
    name = name,
    email = email
)