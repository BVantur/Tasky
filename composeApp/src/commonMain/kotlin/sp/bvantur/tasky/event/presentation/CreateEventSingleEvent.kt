package sp.bvantur.tasky.event.presentation

import sp.bvantur.tasky.core.presentation.SingleEvent
import sp.bvantur.tasky.event.ui.model.SingleInputModel

sealed interface CreateEventSingleEvent : SingleEvent {
    data class OnOpenSingleInput(
        val data: SingleInputModel
    ): CreateEventSingleEvent
}
