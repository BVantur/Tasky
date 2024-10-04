package sp.bvantur.tasky.login.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import sp.bvantur.tasky.core.ui.components.TaskyConfirmationButton
import sp.bvantur.tasky.core.ui.components.TaskyContentSurface
import sp.bvantur.tasky.core.ui.components.TaskyErrorDialog
import sp.bvantur.tasky.core.ui.components.TaskyHyperlinkText
import sp.bvantur.tasky.core.ui.components.TaskyPasswordTextField
import sp.bvantur.tasky.core.ui.components.TaskyTitleText
import sp.bvantur.tasky.core.ui.components.TaskyUserDataTextField
import sp.bvantur.tasky.core.ui.utils.CollectSingleEventsWithLifecycle
import sp.bvantur.tasky.login.presentation.LoginSingleEvent
import sp.bvantur.tasky.login.presentation.LoginUserAction
import sp.bvantur.tasky.login.presentation.LoginViewModel
import sp.bvantur.tasky.login.presentation.LoginViewState
import tasky.composeapp.generated.resources.Res
import tasky.composeapp.generated.resources.email_address
import tasky.composeapp.generated.resources.error_general_message
import tasky.composeapp.generated.resources.error_with_login
import tasky.composeapp.generated.resources.login
import tasky.composeapp.generated.resources.no_account_sign_up
import tasky.composeapp.generated.resources.password
import tasky.composeapp.generated.resources.sign_up
import tasky.composeapp.generated.resources.welcome_back

@Composable
fun LoginRoute(onNavigateToRegister: () -> Unit, onOpenHome: () -> Unit) {
    val viewModel = koinViewModel<LoginViewModel>()

    val viewState: LoginViewState by viewModel.viewStateFlow.collectAsStateWithLifecycle()

    CollectSingleEventsWithLifecycle(singleEventFlow = viewModel.singleEventFlow) { singleEvent ->
        when (singleEvent) {
            LoginSingleEvent.OpenHome -> onOpenHome()
        }
    }

    LoginScreen(
        viewState = viewState,
        onNavigateToRegister = onNavigateToRegister,
        onUserAction = viewModel::onUserAction
    )
}

@Composable
private fun LoginScreen(
    viewState: LoginViewState,
    onNavigateToRegister: () -> Unit,
    onUserAction: (LoginUserAction) -> Unit
) {
    val passwordRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TaskyTitleText(
            text = stringResource(Res.string.welcome_back),
            modifier = Modifier.padding(top = 50.dp)
                .padding(horizontal = 16.dp)
        )

        TaskyContentSurface(
            modifier = Modifier.fillMaxSize()
                .padding(top = 42.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TaskyUserDataTextField(
                    value = viewState.email,
                    isError = viewState.isEmailError,
                    onValueChange = {
                        onUserAction(LoginUserAction.EmailChanged(it))
                    },
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 50.dp),
                    placeholder = stringResource(Res.string.email_address),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    onKeyboardImeAction = {
                        passwordRequester.requestFocus()
                    }
                )

                TaskyPasswordTextField(
                    value = viewState.password,
                    isError = viewState.isPasswordError,
                    onValueChange = {
                        onUserAction(LoginUserAction.PasswordChanged(it))
                    },
                    modifier = Modifier.focusRequester(passwordRequester)
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    placeholder = stringResource(Res.string.password),
                    onKeyboardImeAction = {
                        keyboardController?.hide()
                        onUserAction(LoginUserAction.OnLogin)
                    }
                )

                TaskyConfirmationButton(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 25.dp)
                        .defaultMinSize(minHeight = 56.dp),
                    text = stringResource(Res.string.login),
                    onClick = {
                        keyboardController?.hide()
                        onUserAction(LoginUserAction.OnLogin)
                    }
                )

                Spacer(modifier = Modifier.weight(1f))

                TaskyHyperlinkText(
                    allText = stringResource(Res.string.no_account_sign_up),
                    hyperlinkText = stringResource(Res.string.sign_up),
                    modifier = Modifier.padding(bottom = 40.dp),
                    onClick = {
                        keyboardController?.hide()
                        onNavigateToRegister()
                    }
                )
            }
        }
    }

    if (viewState.showErrorDialog) {
        TaskyErrorDialog(
            title = stringResource(Res.string.error_with_login),
            message = stringResource(Res.string.error_general_message),
            onDismissAction = {
                onUserAction(LoginUserAction.DismissErrorDialog)
            },
            onConfirmAction = {
                onUserAction(LoginUserAction.DismissErrorDialog)
            }

        )
    }
}
