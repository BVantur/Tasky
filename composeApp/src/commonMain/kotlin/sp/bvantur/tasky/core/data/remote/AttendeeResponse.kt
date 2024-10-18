package sp.bvantur.tasky.core.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttendeeResponse(
    @SerialName("email")
    val email: String,
    @SerialName("fullName")
    val name: String,
    @SerialName("userId")
    val userId: String,
    @SerialName("eventId")
    val eventId: String,
    @SerialName("isGoing")
    val isGoing: Boolean,
    @SerialName("remindAt")
    val remindAt: Long
)
