package sp.bvantur.tasky.event.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CheckAttendeeResponse(
    @SerialName("doesUserExist")
    val userExists: Boolean,
    @SerialName("attendee")
    val attendee: CheckAttendeeUserResponse
)

@Serializable
data class CheckAttendeeUserResponse(
    @SerialName("email")
    val email: String,
    @SerialName("fullName")
    val name: String,
    @SerialName("userId")
    val userId: String
)
