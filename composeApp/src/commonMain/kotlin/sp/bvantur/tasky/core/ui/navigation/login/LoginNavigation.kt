package sp.bvantur.tasky.core.ui.navigation.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import sp.bvantur.tasky.login.ui.LoginRoute

internal const val LOGIN_NAVIGATION_ROUTE = "login_navigation_route"

internal fun NavGraphBuilder.loginScreen(navigateToRegister: () -> Unit, navigateToHome: () -> Unit) {
    composable(
        route = LOGIN_NAVIGATION_ROUTE
    ) { _ ->
        LoginRoute(navigateToRegister, navigateToHome)
    }
}
