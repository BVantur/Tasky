package sp.bvantur.tasky.event.data.mappers

import sp.bvantur.tasky.core.data.AttendeeEntity
import sp.bvantur.tasky.event.data.model.AttendeeUserResponse

fun AttendeeUserResponse.toAttendeeEntity(): AttendeeEntity = AttendeeEntity(
    userId = userId,
    name = name,
    email = email
)
