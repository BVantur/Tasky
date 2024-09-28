package sp.bvantur.tasky.register.presentation

import sp.bvantur.tasky.core.presentation.UserAction

sealed class RegisterUserAction : UserAction {
    data class NameChanged(val value: String) : RegisterUserAction()
    data class EmailChanged(val value: String) : RegisterUserAction()
    data class PasswordChanged(val value: String) : RegisterUserAction()
    data class RegisterUser(val name: String, val email: String, val password: String) : RegisterUserAction()
    data object NavigateBack : RegisterUserAction()
    data object DismissErrorDialog : RegisterUserAction()
}
