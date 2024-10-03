package sp.bvantur.tasky.core.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import tasky.composeapp.generated.resources.Res
import tasky.composeapp.generated.resources.close

@Composable
fun TaskyErrorDialog(
    modifier: Modifier = Modifier,
    title: String,
    message: String,
    buttonText: String = stringResource(Res.string.close),
    onDismissAction: () -> Unit,
    onConfirmAction: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismissAction,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        confirmButton = {
            TaskyConfirmationButton(
                text = buttonText,
                onClick = onConfirmAction
            )
        },
        shape = MaterialTheme.shapes.medium
    )
}
