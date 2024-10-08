package sp.bvantur.tasky.event.presentation.models

import kotlinx.serialization.Serializable

@Serializable
data class SingleInputModel(val value: String? = null, val isTitle: Boolean)
