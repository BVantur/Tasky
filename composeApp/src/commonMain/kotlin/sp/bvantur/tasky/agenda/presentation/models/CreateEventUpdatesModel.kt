package sp.bvantur.tasky.agenda.presentation.models

import kotlinx.serialization.Serializable

@Serializable
data class CreateEventUpdatesModel(val title: String? = null, val description: String? = null)
