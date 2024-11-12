package sp.bvantur.tasky.agenda.presentation

import sp.bvantur.tasky.agenda.presentation.models.SingleInputModel
import sp.bvantur.tasky.core.presentation.SingleEvent

sealed interface AgendaDetailsSingleEvent : SingleEvent {
    data class OnOpenDetailsSingleInput(val data: SingleInputModel) : AgendaDetailsSingleEvent
    data object CloseScreen : AgendaDetailsSingleEvent
}
