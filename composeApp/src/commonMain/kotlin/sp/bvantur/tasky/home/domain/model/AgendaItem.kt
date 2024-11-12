package sp.bvantur.tasky.home.domain.model

import sp.bvantur.tasky.agenda.domain.model.AgendaType

data class AgendaItem(
    val id: String,
    val title: String,
    val description: String,
    val duration: String,
    val type: AgendaType
)
