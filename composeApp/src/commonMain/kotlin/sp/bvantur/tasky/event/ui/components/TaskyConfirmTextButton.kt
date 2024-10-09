package sp.bvantur.tasky.event.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import sp.bvantur.tasky.core.ui.theme.appBarAction

@Composable
fun TaskyConfirmTextButton(onClick: () -> Unit, text: String) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.appBarAction,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = MaterialTheme.colorScheme.onTertiary
        )
    ) {
        Text(text, style = MaterialTheme.typography.labelMedium)
    }
}
