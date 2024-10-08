package sp.bvantur.tasky

import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import sp.bvantur.tasky.core.ui.navigation.TaskyNavHost
import sp.bvantur.tasky.core.ui.theme.TaskyTheme

@Composable
@Preview
fun App() {
    TaskyTheme {
        TaskyNavHost()
    }
}
