package sp.bvantur.tasky.core.ui.navigation.register

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import sp.bvantur.tasky.register.ui.RegisterRoute

const val REGISTER_NAVIGATION_ROUTE = "register_navigation_route"

internal fun NavController.navigateToRegister() {
    this.navigate(REGISTER_NAVIGATION_ROUTE)
}

internal fun NavGraphBuilder.registerScreen(onNavigateBack: () -> Unit) {
    composable(
        route = REGISTER_NAVIGATION_ROUTE
    ) { _ ->
        RegisterRoute(onNavigateBack)
    }
}
