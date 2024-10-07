package sp.bvantur.tasky.event.ui.model

import kotlinx.serialization.Serializable

@Serializable
data class SingleInputModel(
    val value: String? = null,
    val isTitle: Boolean
)
