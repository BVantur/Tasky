package sp.bvantur.tasky.core.data.mappers

import sp.bvantur.tasky.core.data.local.AttendeeEntity
import sp.bvantur.tasky.core.data.remote.AttendeeResponse
import sp.bvantur.tasky.event.data.remote.CheckAttendeeUserResponse

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
