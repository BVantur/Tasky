package sp.bvantur.tasky.event.ui.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateEventModel(val title: String? = null, val description: String? = null)
