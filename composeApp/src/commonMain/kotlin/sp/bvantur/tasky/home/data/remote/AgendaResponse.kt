package sp.bvantur.tasky.home.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import sp.bvantur.tasky.core.data.remote.EventResponse

@Serializable
data class AgendaResponse(
    @SerialName("events")
    val events: List<EventResponse>
)
// TODO add other types here
