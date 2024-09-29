package sp.bvantur.tasky.register.presentation

import sp.bvantur.tasky.core.presentation.UserAction

sealed class RegisterUserAction : UserAction {
    data class NameChanged(val value: String) : RegisterUserAction()
    data class EmailChanged(val value: String) : RegisterUserAction()
    data class PasswordChanged(val value: String) : RegisterUserAction()
    data object RegisterUser : RegisterUserAction()
    data object NavigateBack : RegisterUserAction()
}
