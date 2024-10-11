package sp.bvantur.tasky.event.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttendeeResponse(
    @SerialName("doesUserExist")
    val userExists: Boolean,
    @SerialName("attendee")
    val attendee: AttendeeUserResponse
)

@Serializable
data class AttendeeUserResponse(
    @SerialName("email")
    val email: String,
    @SerialName("fullName")
    val name: String,
    @SerialName("userId")
    val userId: String
)
