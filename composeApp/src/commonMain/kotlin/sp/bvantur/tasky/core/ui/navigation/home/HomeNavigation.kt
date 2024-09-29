package sp.bvantur.tasky.core.ui.navigation.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import sp.bvantur.tasky.home.ui.HomeScreen

const val HOME_NAVIGATION_ROUTE = "home_navigation_route"

internal fun NavGraphBuilder.homeScreen() {
    composable(
        route = HOME_NAVIGATION_ROUTE
    ) { _ ->
        HomeScreen()
    }
}
