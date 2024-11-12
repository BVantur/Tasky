package sp.bvantur.tasky.core.data.mappers

import sp.bvantur.tasky.agenda.data.remote.CheckAttendeeUserResponse
import sp.bvantur.tasky.agenda.domain.model.Attendee
import sp.bvantur.tasky.agenda.domain.model.ReminderValue
import sp.bvantur.tasky.core.data.local.AttendeeEntity
import sp.bvantur.tasky.core.data.remote.AttendeeResponse

fun CheckAttendeeUserResponse.asAttendeeEntity(): AttendeeEntity = AttendeeEntity(
    userId = userId,
    name = name,
    email = email
)

fun AttendeeResponse.asAttendeeEntity(): AttendeeEntity = AttendeeEntity(
    userId = userId,
    name = name,
    email = email,
    eventId = eventId,
    isGoing = isGoing,
    remindAt = remindAt
)

fun Attendee.asAttendeeEntity(
    eventId: String,
    isGoing: Boolean = false,
    remindAt: Long = ReminderValue.THIRTY_MINUTES.inMillis
): AttendeeEntity = AttendeeEntity(
    userId = userId,
    name = name,
    email = email,
    eventId = eventId,
    isGoing = isGoing,
    remindAt = remindAt
)
