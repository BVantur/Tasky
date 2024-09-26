package sp.bvantur.tasky.core.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import org.jetbrains.compose.resources.stringResource
import tasky.composeapp.generated.resources.Res
import tasky.composeapp.generated.resources.password_visibility_icon

@Composable
fun PasswordTextField(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val showPassword = rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.labelMedium,
        modifier = modifier,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedBorderColor = MaterialTheme.colorScheme.background,
            focusedBorderColor = MaterialTheme.colorScheme.secondary,
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        placeholder = {
            TextFieldPlaceholder(text = placeholder)
        },
        visualTransformation = if (!showPassword.value) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        trailingIcon = {
            val icon = if (showPassword.value) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }
            IconButton(onClick = { showPassword.value = !showPassword.value }) {
                Icon(
                    imageVector = icon,
                    contentDescription = stringResource(Res.string.password_visibility_icon),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        shape = MaterialTheme.shapes.medium
    )
}
