package sp.bvantur.tasky.event.presentation

import sp.bvantur.tasky.core.presentation.SingleEvent
import sp.bvantur.tasky.event.presentation.models.SingleInputModel

sealed interface EventDetailsSingleEvent : SingleEvent {
    data class OnOpenDetailsSingleInput(val data: SingleInputModel) : EventDetailsSingleEvent
    data object CloseScreen : EventDetailsSingleEvent
}
