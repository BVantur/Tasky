package sp.bvantur.tasky.core.ui.navigation.event

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import sp.bvantur.tasky.event.ui.CreateEventRoute

internal const val CREATE_EVENT_NAVIGATION_ROUTE = "create_event_navigation_route"

internal fun NavController.navigateToCreateEvent() {
    this.navigate(route = CREATE_EVENT_NAVIGATION_ROUTE)
}

internal fun NavGraphBuilder.createEvent(onNavigateBack: () -> Unit) {
    composable(
        route = CREATE_EVENT_NAVIGATION_ROUTE
    ) { _ ->
        CreateEventRoute(onNavigateBack)
    }
}
