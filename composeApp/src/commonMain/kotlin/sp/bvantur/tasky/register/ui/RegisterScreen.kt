package sp.bvantur.tasky.register.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import sp.bvantur.tasky.core.ui.components.TaskyBackButton
import sp.bvantur.tasky.core.ui.components.TaskyConfirmationButton
import sp.bvantur.tasky.core.ui.components.TaskyPasswordTextField
import sp.bvantur.tasky.core.ui.components.TaskyTitleText
import sp.bvantur.tasky.core.ui.components.TaskyUserDataTextField
import sp.bvantur.tasky.core.ui.components.TaskyUserOnboardingSurface
import sp.bvantur.tasky.register.presentation.OnRegisterUserAction
import sp.bvantur.tasky.register.presentation.RegisterUserAction
import sp.bvantur.tasky.register.presentation.RegisterViewModel
import sp.bvantur.tasky.register.presentation.RegisterViewState
import tasky.composeapp.generated.resources.Res
import tasky.composeapp.generated.resources.close
import tasky.composeapp.generated.resources.create_your_account
import tasky.composeapp.generated.resources.email_address
import tasky.composeapp.generated.resources.error_with_registration
import tasky.composeapp.generated.resources.error_with_registration_message
import tasky.composeapp.generated.resources.get_started
import tasky.composeapp.generated.resources.name
import tasky.composeapp.generated.resources.password

@Composable
fun RegisterRoute() {
    val viewModel = koinViewModel<RegisterViewModel>()
    val viewState: RegisterViewState by viewModel.viewStateFlow.collectAsStateWithLifecycle()

    RegisterScreen(
        viewState = viewState,
        onUserAction = viewModel::onUserAction
    )
}

@Composable
fun RegisterScreen(viewState: RegisterViewState, onUserAction: OnRegisterUserAction) {
    val name = rememberSaveable { mutableStateOf("Blaž") }
    val email = rememberSaveable { mutableStateOf("blaz.vantur@gmail.com") }
    val password = rememberSaveable { mutableStateOf("Test1234!") }

    val (emailRequester, passwordRequester) = remember { FocusRequester.createRefs() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TaskyTitleText(
            text = stringResource(Res.string.create_your_account),
            modifier = Modifier.padding(top = 50.dp)
                .padding(horizontal = 16.dp)
        )

        TaskyUserOnboardingSurface(
            modifier = Modifier.fillMaxSize()
                .padding(top = 42.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TaskyUserDataTextField(
                    value = name.value,
                    onValueChange = { value ->
                        name.value = value
                        onUserAction(RegisterUserAction.NameChanged(value))
                    },
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 50.dp),
                    placeholder = stringResource(Res.string.name),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    onKeyboardImeAction = {
                        emailRequester.requestFocus()
                    },
                    isError = viewState.isNameError
                )
                TaskyUserDataTextField(
                    value = email.value,
                    onValueChange = { value ->
                        email.value = value
                        onUserAction(RegisterUserAction.EmailChanged(value))
                    },
                    modifier = Modifier.focusRequester(emailRequester)
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    placeholder = stringResource(Res.string.email_address),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    onKeyboardImeAction = {
                        passwordRequester.requestFocus()
                    },
                    isError = viewState.isEmailError
                )

                TaskyPasswordTextField(
                    value = password.value,
                    onValueChange = { value ->
                        password.value = value
                        onUserAction(RegisterUserAction.PasswordChanged(value))
                    },
                    modifier = Modifier.focusRequester(passwordRequester)
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    placeholder = stringResource(Res.string.password),
                    onKeyboardImeAction = {
                        keyboardController?.hide()
                        onUserAction(
                            RegisterUserAction.RegisterUser(
                                name = name.value,
                                email = email.value,
                                password = password.value
                            )
                        )
                    },
                    isError = viewState.isPasswordError
                )

                TaskyConfirmationButton(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 25.dp)
                        .defaultMinSize(minHeight = 56.dp),
                    text = stringResource(Res.string.get_started),
                    onClick = {
                        keyboardController?.hide()
                        onUserAction(
                            RegisterUserAction.RegisterUser(
                                name = name.value,
                                email = email.value,
                                password = password.value
                            )
                        )
                    }
                )

                Spacer(modifier = Modifier.weight(1f))

                TaskyBackButton(
                    modifier = Modifier.padding(bottom = 40.dp).align(Alignment.Start),
                    onClick = {
                        keyboardController?.hide()
                        onUserAction(RegisterUserAction.NavigateBack)
                    }
                )
            }
        }
    }

    if (viewState.showErrorDialog) {
        AlertDialog(
            onDismissRequest = {
                onUserAction(RegisterUserAction.DismissErrorDialog)
            },
            title = {
                Text(
                    text = stringResource(Res.string.error_with_registration),
                    style = MaterialTheme.typography.titleMedium
                )
            },
            text = {
                Text(
                    text = stringResource(Res.string.error_with_registration_message),
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            confirmButton = {
                TaskyConfirmationButton(
                    text = stringResource(Res.string.close),
                    onClick = {
                        onUserAction(RegisterUserAction.DismissErrorDialog)
                    }
                )
            },
            shape = MaterialTheme.shapes.medium
        )
    }
}
