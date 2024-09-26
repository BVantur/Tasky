package sp.bvantur.tasky

import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import sp.bvantur.tasky.core.ui.theme.TaskyTheme
import sp.bvantur.tasky.login.ui.LoginRoute

@Composable
@Preview
fun App() {
    TaskyTheme {
        LoginRoute()
    }
}
