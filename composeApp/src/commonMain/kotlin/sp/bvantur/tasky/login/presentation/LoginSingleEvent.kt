package sp.bvantur.tasky.login.presentation

import sp.bvantur.tasky.core.presentation.SingleEvent

sealed interface LoginSingleEvent : SingleEvent {
    data object OpenHome : LoginSingleEvent
}
