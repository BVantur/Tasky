package sp.bvantur.tasky.register.ui

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import sp.bvantur.tasky.core.ui.components.BackButton
import sp.bvantur.tasky.core.ui.components.ConfirmationButton
import sp.bvantur.tasky.core.ui.components.PasswordTextField
import sp.bvantur.tasky.core.ui.components.TitleText
import sp.bvantur.tasky.core.ui.components.UserDataTextField
import sp.bvantur.tasky.core.ui.components.UserOnboardingSurface
import tasky.composeapp.generated.resources.Res
import tasky.composeapp.generated.resources.create_your_account
import tasky.composeapp.generated.resources.email_address
import tasky.composeapp.generated.resources.get_started
import tasky.composeapp.generated.resources.name
import tasky.composeapp.generated.resources.password

@Composable
fun RegisterRoute() {
    RegisterScreen()
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterScreen() {
    val (emailRequester, passwordRequester) = FocusRequester.createRefs()
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleText(
            text = stringResource(Res.string.create_your_account),
            modifier = Modifier.padding(top = 50.dp)
                .padding(horizontal = 16.dp)
        )

        UserOnboardingSurface(
            modifier = Modifier.fillMaxSize()
                .padding(top = 42.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                UserDataTextField(
                    value = "", // TODO
                    onValueChange = {
                        // TODO
                    },
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 50.dp),
                    placeholder = stringResource(Res.string.email_address),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    onKeyboardImeAction = {
                        emailRequester.requestFocus()
                    }
                )
                UserDataTextField(
                    value = "", // TODO
                    onValueChange = {
                        // TODO
                    },
                    modifier = Modifier.focusRequester(emailRequester)
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    placeholder = stringResource(Res.string.name),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    onKeyboardImeAction = {
                        passwordRequester.requestFocus()
                    }
                )

                PasswordTextField(
                    value = "", // TODO
                    onValueChange = {
                        // TODO
                    },
                    modifier = Modifier.focusRequester(passwordRequester)
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    placeholder = stringResource(Res.string.password),
                    onKeyboardImeAction = {
                        keyboardController?.hide()
                        // TODO
                    }
                )

                ConfirmationButton(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 25.dp)
                        .defaultMinSize(minHeight = 56.dp),
                    text = stringResource(Res.string.get_started),
                    onClick = {
                        keyboardController?.hide()
                        // TODO
                    }
                )

                Spacer(modifier = Modifier.weight(1f))

                BackButton(modifier = Modifier.padding(bottom = 40.dp).align(Alignment.Start), onClick = {
                    keyboardController?.hide()
                    // TODO
                })
            }
        }
    }
}
