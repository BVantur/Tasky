package sp.bvantur.tasky.login.presentation

import sp.bvantur.tasky.core.presentation.UserAction

sealed class LoginUserAction : UserAction {
    data class EmailChanged(val value: String) : LoginUserAction()
    data class PasswordChanged(val value: String) : LoginUserAction()
    data object OnLogin : LoginUserAction()
}
