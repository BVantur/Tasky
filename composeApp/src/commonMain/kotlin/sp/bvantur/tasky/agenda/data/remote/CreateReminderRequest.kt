package sp.bvantur.tasky.agenda.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateReminderRequest(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("time")
    val time: Long,
    @SerialName("remindAt")
    val remindAt: Long
)
