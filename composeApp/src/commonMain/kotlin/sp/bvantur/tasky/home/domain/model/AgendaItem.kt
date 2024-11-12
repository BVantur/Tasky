package sp.bvantur.tasky.home.domain.model

import sp.bvantur.tasky.core.domain.model.AgendaType

data class AgendaItem(
    val id: String,
    val title: String,
    val description: String,
    val duration: String,
    val type: AgendaType
)
