package sp.bvantur.tasky.register.presentation

import sp.bvantur.tasky.core.presentation.SingleEvent

sealed interface RegisterSingleEvent : SingleEvent {
    data object NavigateBack : RegisterSingleEvent
}
