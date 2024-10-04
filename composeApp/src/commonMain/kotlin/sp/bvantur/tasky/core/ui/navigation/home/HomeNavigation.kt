package sp.bvantur.tasky.core.ui.navigation.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import sp.bvantur.tasky.core.ui.navigation.login.LOGIN_NAVIGATION_ROUTE
import sp.bvantur.tasky.home.ui.HomeRoute

const val HOME_NAVIGATION_ROUTE = "home_navigation_route"

internal fun NavController.navigateToHome() {
    this.navigate(
        route = HOME_NAVIGATION_ROUTE,
        navOptions = NavOptions.Builder().apply {
            setPopUpTo(LOGIN_NAVIGATION_ROUTE, true)
        }.build()
    )
}

internal fun NavGraphBuilder.homeScreen(onCreateEventAction: () -> Unit) {
    composable(
        route = HOME_NAVIGATION_ROUTE
    ) { _ ->
        HomeRoute(
            onCreateEventAction = onCreateEventAction
        )
    }
}
