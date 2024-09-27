package sp.bvantur.tasky.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun TaskyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = TaskyColorPalette,
        typography = TaskyTypography(),
        shapes = TaskyShapes,
        content = content
    )
}
