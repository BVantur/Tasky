package sp.bvantur.tasky.core.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun TaskyTitleText(modifier: Modifier = Modifier, text: String, color: Color = MaterialTheme.colorScheme.onPrimary) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.displayLarge,
        color = color
    )
}
