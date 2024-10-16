package sp.bvantur.tasky.event.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateEventRequest(
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
    @SerialName("attendeeIds")
    val attendeeIds: List<String>
)
