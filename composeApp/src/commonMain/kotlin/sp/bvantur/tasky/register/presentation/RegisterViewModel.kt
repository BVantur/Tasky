package sp.bvantur.tasky.register.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sp.bvantur.tasky.core.domain.DispatcherProvider
import sp.bvantur.tasky.core.domain.ValidateEmailUseCase
import sp.bvantur.tasky.core.domain.ValidatePasswordUseCase
import sp.bvantur.tasky.core.presentation.SingleEventHandler
import sp.bvantur.tasky.core.presentation.SingleEventHandlerImpl
import sp.bvantur.tasky.core.presentation.ViewModelUserActionHandler
import sp.bvantur.tasky.core.presentation.ViewStateViewModel
import sp.bvantur.tasky.register.domain.RegisterRepository
import sp.bvantur.tasky.register.domain.ValidateNameUseCase

class RegisterViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val registerRepository: RegisterRepository
) : ViewStateViewModel<RegisterViewState>(RegisterViewState()),
    ViewModelUserActionHandler<RegisterUserAction>,
    SingleEventHandler<RegisterSingleEvent> by SingleEventHandlerImpl(dispatcherProvider) {
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
        emitViewState { viewState ->
            viewState.copy(showErrorDialog = false)
        }
    }

    private fun onNameChanged(value: String) {
        val isValid = validateNameUseCase.invoke(value)
        emitViewState { viewState ->
            viewState.copy(
                isNameError = !isValid,
                name = value
            )
        }
    }

    private fun onEmailChanged(value: String) {
        val isValid = validateEmailUseCase.invoke(value)
        emitViewState { viewState ->
            viewState.copy(
                isEmailError = !isValid,
                email = value
            )
        }
    }

    private fun onPasswordChanged(value: String) {
        val isValid = validatePasswordUseCase.invoke(value)
        emitViewState { viewState ->
            viewState.copy(
                isPasswordError = !isValid,
                password = value
            )
        }
    }

    private fun onRegisterUser() {
        val isNameValid = validateNameUseCase.invoke(viewStateFlow.value.name)
        val isEmailValid = validateEmailUseCase.invoke(viewStateFlow.value.email)
        val isPasswordValid = validatePasswordUseCase.invoke(viewStateFlow.value.password)

        if (!isNameValid || !isEmailValid || !isPasswordValid) {
            emitViewState { viewState ->
                viewState.copy(
                    isNameError = !isNameValid,
                    isEmailError = !isEmailValid,
                    isPasswordError = !isPasswordValid
                )
            }
            return
        }

        viewModelScope.launch {

            val response = registerRepository.register(
                name = viewStateFlow.value.name,
                email = viewStateFlow.value.email,
                password = viewStateFlow.value.password
            )
            if (response.isFailure) {
                emitViewState { viewState ->
                    viewState.copy(showErrorDialog = true)
                }
                return@launch
            }

            onNavigateBack()
        }
    }

    private fun onNavigateBack() {
        viewModelScope.launch {
            emitSingleEvent(RegisterSingleEvent.NavigateBack)
        }
    }
}
