package sp.bvantur.tasky.agenda.presentation

import sp.bvantur.tasky.core.presentation.SingleEvent

sealed interface SingleInputSingleEvent : SingleEvent {
    data class SaveTitle(val value: String) : SingleInputSingleEvent
    data class SaveDescription(val value: String) : SingleInputSingleEvent
}
