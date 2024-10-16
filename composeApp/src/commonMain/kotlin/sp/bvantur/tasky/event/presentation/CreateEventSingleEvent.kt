package sp.bvantur.tasky.event.presentation

import sp.bvantur.tasky.core.presentation.SingleEvent
import sp.bvantur.tasky.event.presentation.models.SingleInputModel

sealed interface CreateEventSingleEvent : SingleEvent {
    data class OnOpenSingleInput(val data: SingleInputModel) : CreateEventSingleEvent
    data object CloseScreen : CreateEventSingleEvent
}
