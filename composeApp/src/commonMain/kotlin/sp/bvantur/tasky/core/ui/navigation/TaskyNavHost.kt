package sp.bvantur.tasky.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.koin.compose.viewmodel.koinViewModel
import sp.bvantur.tasky.core.ui.navigation.event.SINGLE_INPUT_DESCRIPTION_RESULT_EXTRA
import sp.bvantur.tasky.core.ui.navigation.event.SINGLE_INPUT_TITLE_RESULT_EXTRA
import sp.bvantur.tasky.core.ui.navigation.event.createEventScreen
import sp.bvantur.tasky.core.ui.navigation.event.navigateToCreateEventScreen
import sp.bvantur.tasky.core.ui.navigation.event.navigateToSingleInputScreen
import sp.bvantur.tasky.core.ui.navigation.event.singleInputScreen
import sp.bvantur.tasky.core.ui.navigation.home.HOME_NAVIGATION_ROUTE
import sp.bvantur.tasky.core.ui.navigation.home.homeScreen
import sp.bvantur.tasky.core.ui.navigation.home.navigateToHome
import sp.bvantur.tasky.core.ui.navigation.login.LOGIN_NAVIGATION_ROUTE
import sp.bvantur.tasky.core.ui.navigation.login.loginScreen
import sp.bvantur.tasky.core.ui.navigation.register.navigateToRegister
import sp.bvantur.tasky.core.ui.navigation.register.registerScreen
import sp.bvantur.tasky.splash.presentation.SplashViewModel

@Composable
internal fun TaskyNavHost() {
    val navController = rememberNavController()
    val viewModel = koinViewModel<SplashViewModel>()
    val startDestination = if (viewModel.isUserAuthorized()) {
        HOME_NAVIGATION_ROUTE
    } else {
        LOGIN_NAVIGATION_ROUTE
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        loginScreen(
            navigateToRegister = navController::navigateToRegister,
            navigateToHome = navController::navigateToHome
        )
        registerScreen(onNavigateBack = navController::navigateUp)
        homeScreen(onCreateEventAction = navController::navigateToCreateEventScreen)
        createEventScreen(
            onNavigateBack = navController::navigateUp,
            onOpenSingleInputScreen = navController::navigateToSingleInputScreen
        )
        singleInputScreen(onNavigateBack = navController::navigateUp, onSaveAction = { value, isTitle ->
            if (isTitle) {
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    SINGLE_INPUT_TITLE_RESULT_EXTRA,
                    value
                )
            } else {
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    SINGLE_INPUT_DESCRIPTION_RESULT_EXTRA,
                    value
                )
            }
            navController.popBackStack()
        })
    }
}
