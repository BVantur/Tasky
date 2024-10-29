package sp.bvantur.tasky.home.presentation

import sp.bvantur.tasky.core.presentation.SingleEvent

sealed interface HomeSingleEvent : SingleEvent {
    data class NavigateToCreateEvent(val fromTime: Long, val toTime: Long) : HomeSingleEvent
    data object NavigateToLogin : HomeSingleEvent
}
