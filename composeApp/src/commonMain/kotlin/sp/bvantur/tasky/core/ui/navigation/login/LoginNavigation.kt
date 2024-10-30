package sp.bvantur.tasky.core.ui.navigation.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import sp.bvantur.tasky.core.ui.navigation.home.HOME_NAVIGATION_ROUTE
import sp.bvantur.tasky.login.ui.LoginRoute

internal const val LOGIN_NAVIGATION_ROUTE = "login_navigation_route"

internal fun NavController.navigateToLogin() {
    this.navigate(
        route = LOGIN_NAVIGATION_ROUTE,
        navOptions = NavOptions.Builder().apply {
            setPopUpTo(HOME_NAVIGATION_ROUTE, true)
        }.build()
    )
}

internal fun NavGraphBuilder.loginScreen(navigateToRegister: () -> Unit, navigateToHome: () -> Unit) {
    composable(
        route = LOGIN_NAVIGATION_ROUTE
    ) { _ ->
        LoginRoute(navigateToRegister, navigateToHome)
    }
}
