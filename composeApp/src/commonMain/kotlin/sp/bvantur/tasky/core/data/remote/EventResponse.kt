package sp.bvantur.tasky.core.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventResponse(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("from")
    val from: Long,
    @SerialName("to")
    val to: Long,
    @SerialName("remindAt")
    val remindAt: Long,
    @SerialName("host")
    val host: String,
    @SerialName("isUserEventCreator")
    val isUserEventCreator: Boolean,
    @SerialName("attendees")
    val attendees: List<AttendeeResponse>
    // TODO add photos
)
