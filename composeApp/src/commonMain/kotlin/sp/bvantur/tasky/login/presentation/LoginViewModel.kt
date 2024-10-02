package sp.bvantur.tasky.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sp.bvantur.tasky.core.domain.DispatcherProvider
import sp.bvantur.tasky.core.domain.ValidateEmailUseCase
import sp.bvantur.tasky.core.domain.ValidatePasswordUseCase
import sp.bvantur.tasky.core.presentation.ViewModelUserActionHandler
import sp.bvantur.tasky.core.presentation.ViewModelViewStateHandler
import sp.bvantur.tasky.core.presentation.ViewModelViewStateHandlerImpl

class LoginViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel(),
    ViewModelUserActionHandler<LoginUserAction>,
    ViewModelViewStateHandler<LoginViewState> by ViewModelViewStateHandlerImpl(
        LoginViewState(),
        dispatcherProvider
    ) {
    override fun onUserAction(userAction: LoginUserAction) {
        when (userAction) {
            is LoginUserAction.EmailChanged -> onEmailChanged(userAction.value)
            is LoginUserAction.PasswordChanged -> onPasswordChanged(userAction.value)
            LoginUserAction.OnLogin -> onLogin()
        }
    }

    private fun onLogin() {
        viewModelScope.launch {
            val isEmailValid = validateEmailUseCase.invoke(viewStateFlow.value.email)
            val isPasswordValid = validateEmailUseCase.invoke(viewStateFlow.value.email)

            if (!isEmailValid || !isPasswordValid) {
                emitViewState { viewState ->
                    viewState.copy(
                        isEmailError = !isEmailValid,
                        isPasswordError = !isPasswordValid
                    )
                }
                return@launch
            }

            // TODO implement backend communication
        }
    }

    private fun onEmailChanged(value: String) {
        viewModelScope.launch {
            val isValid = validateEmailUseCase.invoke(value)
            emitViewState { viewState ->
                viewState.copy(
                    isEmailError = !isValid,
                    email = value
                )
            }
        }
    }

    private fun onPasswordChanged(value: String) {
        viewModelScope.launch {
            val isValid = validatePasswordUseCase.invoke(value)
            emitViewState { viewState ->
                viewState.copy(
                    isPasswordError = !isValid,
                    password = value
                )
            }
        }
    }
}
