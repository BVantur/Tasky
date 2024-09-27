package sp.bvantur.tasky.register.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import sp.bvantur.tasky.core.DispatcherProvider
import sp.bvantur.tasky.core.domain.ValidateEmailUseCase
import sp.bvantur.tasky.core.domain.ValidateNameUseCase
import sp.bvantur.tasky.core.domain.ValidatePasswordUseCase
import sp.bvantur.tasky.core.presentation.ViewModelUserActionHandler
import sp.bvantur.tasky.core.presentation.ViewModelViewStateHandler
import sp.bvantur.tasky.core.presentation.ViewModelViewStateHandlerImpl
import kotlin.time.Duration.Companion.milliseconds

typealias OnRegisterUserAction = (RegisterUserAction) -> Unit

@OptIn(FlowPreview::class)
class RegisterViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel(),
    ViewModelUserActionHandler<RegisterUserAction>,
    ViewModelViewStateHandler<RegisterViewState> by ViewModelViewStateHandlerImpl(
        RegisterViewState(),
        dispatcherProvider
    ) {

    private val textDebounceFlow = MutableSharedFlow<Pair<String, suspend (String) -> Unit>>()

    init {
        textDebounceFlow
            .debounce(500.milliseconds)
            .onEach { (text, action) ->
                action(text)
            }
            .launchIn(CoroutineScope(dispatcherProvider.default))
    }

    override fun onUserAction(userAction: RegisterUserAction) {
        when (userAction) {
            is RegisterUserAction.NameChanged -> onNameChanged(userAction.value)
            is RegisterUserAction.EmailChanged -> onEmailChanged(userAction.value)
            is RegisterUserAction.PasswordChanged -> onPasswordChanged(userAction.value)
            is RegisterUserAction.RegisterUser -> onRegisterUser(userAction.name, userAction.email, userAction.password)
            RegisterUserAction.NavigateBack -> onNavigateBack()
        }
    }

    private fun onNameChanged(value: String) {
        viewModelScope.launch {
            textDebounceFlow.emit(
                value to {
                    val isValid = validateNameUseCase.invoke(it)
                    emitViewState(
                        viewStateFlow.value.copy(isNameError = !isValid)
                    )
                }
            )
        }
    }

    private fun onEmailChanged(value: String) {
        viewModelScope.launch {
            textDebounceFlow.emit(
                value to {
                    val isValid = validateEmailUseCase.invoke(it)
                    emitViewState(
                        viewStateFlow.value.copy(isEmailError = !isValid)
                    )
                }
            )
        }
    }

    private fun onPasswordChanged(value: String) {
        viewModelScope.launch {
            textDebounceFlow.emit(
                value to {
                    val isValid = validatePasswordUseCase.invoke(it)
                    emitViewState(
                        viewStateFlow.value.copy(isPasswordError = !isValid)
                    )
                }
            )
        }
    }

    private fun onRegisterUser(name: String, email: String, password: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            val isNameValid = validateNameUseCase.invoke(name)
            val isEmailValid = validateEmailUseCase.invoke(email)
            val isPasswordValid = validatePasswordUseCase.invoke(password)

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

            // TODO perform register logic and react depending on the backend result
        }
    }

    private fun onNavigateBack() {
        // TODO perform navigation back to the previous screen
    }
}
