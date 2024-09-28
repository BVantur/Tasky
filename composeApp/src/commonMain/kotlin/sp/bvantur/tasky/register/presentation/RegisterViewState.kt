package sp.bvantur.tasky.register.presentation

import sp.bvantur.tasky.core.presentation.ViewState

data class RegisterViewState(
    val isNameError: Boolean = false,
    val isEmailError: Boolean = false,
    val isPasswordError: Boolean = false,
    val showErrorDialog: Boolean = false,
    val errorMessage: String? = null
) : ViewState
