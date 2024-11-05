package sp.bvantur.tasky.home.presentation

import sp.bvantur.tasky.core.presentation.SingleEvent

sealed interface HomeSingleEvent : SingleEvent {
    data class NavigateToEventDetails(val eventId: String?, val isEditMode: Boolean) : HomeSingleEvent
    data object NavigateToLogin : HomeSingleEvent
}
