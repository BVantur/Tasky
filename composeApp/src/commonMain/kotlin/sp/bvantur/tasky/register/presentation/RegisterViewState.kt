package sp.bvantur.tasky.register.presentation

import sp.bvantur.tasky.core.presentation.ViewState

data class RegisterViewState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isNameError: Boolean = false,
    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false,
) : ViewState
