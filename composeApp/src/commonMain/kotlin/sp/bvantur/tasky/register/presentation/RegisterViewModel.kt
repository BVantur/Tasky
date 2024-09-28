package sp.bvantur.tasky.register.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sp.bvantur.tasky.core.DispatcherProvider
import sp.bvantur.tasky.core.domain.ValidateEmailUseCase
import sp.bvantur.tasky.core.domain.ValidatePasswordUseCase
import sp.bvantur.tasky.core.presentation.ViewModelUserActionHandler
import sp.bvantur.tasky.core.presentation.ViewModelViewStateHandler
import sp.bvantur.tasky.core.presentation.ViewModelViewStateHandlerImpl
import sp.bvantur.tasky.register.domain.RegisterUserUseCase
import sp.bvantur.tasky.register.domain.ValidateNameUseCase

class RegisterViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel(),
    ViewModelUserActionHandler<RegisterUserAction>,
    ViewModelViewStateHandler<RegisterViewState> by ViewModelViewStateHandlerImpl(
        RegisterViewState(),
        dispatcherProvider
    ) {
    override fun onUserAction(userAction: RegisterUserAction) {
        when (userAction) {
            is RegisterUserAction.NameChanged -> onNameChanged(userAction.value)
            is RegisterUserAction.EmailChanged -> onEmailChanged(userAction.value)
            is RegisterUserAction.PasswordChanged -> onPasswordChanged(userAction.value)
            RegisterUserAction.RegisterUser -> onRegisterUser()
            RegisterUserAction.NavigateBack -> onNavigateBack()
            RegisterUserAction.DismissErrorDialog -> onDismissErrorDialog()
        }
    }

    private fun onDismissErrorDialog() {
        viewModelScope.launch {
            emitViewState(
                viewStateFlow.value.copy(showErrorDialog = false)
            )
        }
    }

    private fun onNameChanged(value: String) {
        viewModelScope.launch {
            val isValid = validateNameUseCase.invoke(value)
            emitViewState(
                viewStateFlow.value.copy(
                    isNameError = !isValid,
                    name = value
                )
            )
        }
    }

    private fun onEmailChanged(value: String) {
        viewModelScope.launch {
            val isValid = validateEmailUseCase.invoke(value)
            emitViewState(
                viewStateFlow.value.copy(
                    isEmailError = !isValid,
                    email = value
                )
            )
        }
    }

    private fun onPasswordChanged(value: String) {
        viewModelScope.launch {
            val isValid = validatePasswordUseCase.invoke(value)
            emitViewState(
                viewStateFlow.value.copy(
                    isPasswordError = isValid,
                    password = value
                )
            )
        }
    }

    private fun onRegisterUser() {
        viewModelScope.launch {
            val isNameValid = validateNameUseCase.invoke(viewStateFlow.value.name)
            val isEmailValid = validateEmailUseCase.invoke(viewStateFlow.value.email)
            val isPasswordValid = validatePasswordUseCase.invoke(viewStateFlow.value.password)

            if (!isNameValid || !isEmailValid || !isPasswordValid) {
                emitViewState(
                    viewStateFlow.value.copy(
                        isNameError = !isNameValid,
                        isEmailError = !isEmailValid,
                        isPasswordError = !isPasswordValid
                    )
                )
                return@launch
            }

            val response = registerUserUseCase(
                name = viewStateFlow.value.name,
                email = viewStateFlow.value.email,
                password = viewStateFlow.value.password
            )
            if (response.isFailure) {
                emitViewState(
                    viewStateFlow.value.copy(showErrorDialog = true, errorMessage = response.exceptionOrNull()?.message)
                )
                return@launch
            }

            onNavigateBack()
        }
    }

    private fun onNavigateBack() {
        // TODO perform navigation back to the previous screen
    }
}
