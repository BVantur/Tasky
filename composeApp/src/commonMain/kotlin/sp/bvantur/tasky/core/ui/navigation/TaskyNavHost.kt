package sp.bvantur.tasky.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import sp.bvantur.tasky.core.ui.navigation.login.LOGIN_NAVIGATION_ROUTE
import sp.bvantur.tasky.core.ui.navigation.login.loginScreen
import sp.bvantur.tasky.core.ui.navigation.register.navigateToRegister
import sp.bvantur.tasky.core.ui.navigation.register.registerScreen

@Composable
internal fun TaskyNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = LOGIN_NAVIGATION_ROUTE
    ) {
        loginScreen(navigateToRegister = navController::navigateToRegister)
        registerScreen(onNavigateBack = navController::navigateUp)
    }
}
