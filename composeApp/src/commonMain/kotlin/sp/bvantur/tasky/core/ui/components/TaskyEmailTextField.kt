package sp.bvantur.tasky.core.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TaskyEmailTextField(value: String, placeholder: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier) {
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
            TaskyTextFieldPlaceholder(text = placeholder)
        },
        shape = MaterialTheme.shapes.medium
    )
}
