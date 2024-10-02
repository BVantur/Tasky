package sp.bvantur.tasky.login.presentation

import sp.bvantur.tasky.core.presentation.ViewState

data class LoginViewState(
    val email: String = "",
    val isEmailError: Boolean = false,
    val password: String = "",
    val isPasswordError: Boolean = false
) : ViewState
