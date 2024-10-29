package sp.bvantur.tasky.home.presentation

import sp.bvantur.tasky.core.presentation.UserAction

sealed interface HomeUserAction : UserAction {
    data object CreateNewEvent : HomeUserAction
    data object LogoutUser : HomeUserAction
}
