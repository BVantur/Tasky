package sp.bvantur.tasky.core.ui.navigation.event

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import sp.bvantur.tasky.event.ui.CreateEventRoute
import sp.bvantur.tasky.event.ui.SingleInputRoute
import sp.bvantur.tasky.event.ui.model.CreateEventModel
import sp.bvantur.tasky.event.ui.model.SingleInputModel
import sp.bvantur.tasky.event.ui.model.SingleInputModelNavType

internal const val CREATE_EVENT_NAVIGATION_ROUTE = "create_event_navigation_route"
internal const val SINGLE_INPUT_NAVIGATION_ROUTE = "single_input_navigation_route"

internal const val SINGLE_INPUT_NAVIGATION_EXTRA = "single_input_navigation_extra"
internal const val SINGLE_INPUT_TITLE_RESULT_EXTRA = "single_input_title_result_extra"
internal const val SINGLE_INPUT_DESCRIPTION_RESULT_EXTRA = "single_input_description_result_extra"

internal fun NavController.navigateToCreateEventScreen() {
    this.navigate(route = CREATE_EVENT_NAVIGATION_ROUTE)
}

internal fun NavController.navigateToSingleInputScreen(singleInputModel: SingleInputModel) {
    val jsonString = Json.encodeToString(singleInputModel)
    this.navigate(route = "$SINGLE_INPUT_NAVIGATION_ROUTE/$jsonString")
}

internal fun NavGraphBuilder.createEventScreen(
    onNavigateBack: () -> Unit,
    onOpenSingleInputScreen: (SingleInputModel) -> Unit
) {
    composable(
        route = CREATE_EVENT_NAVIGATION_ROUTE
    ) { backStackEntry ->
        val title = backStackEntry.savedStateHandle.get<String>(SINGLE_INPUT_TITLE_RESULT_EXTRA)
        val description = backStackEntry.savedStateHandle.get<String>(SINGLE_INPUT_DESCRIPTION_RESULT_EXTRA)
        backStackEntry.savedStateHandle.remove<Boolean>(SINGLE_INPUT_TITLE_RESULT_EXTRA)
        backStackEntry.savedStateHandle.remove<Boolean>(SINGLE_INPUT_DESCRIPTION_RESULT_EXTRA)

        CreateEventRoute(
            CreateEventModel(title = title, description = description),
            onNavigateBack,
            onOpenSingleInputScreen
        )
    }
}

internal fun NavGraphBuilder.singleInputScreen(onNavigateBack: () -> Unit, onSaveAction: (String, Boolean) -> Unit) {
    composable(
        route = "$SINGLE_INPUT_NAVIGATION_ROUTE/{$SINGLE_INPUT_NAVIGATION_EXTRA}",
        arguments = listOf(
            navArgument(SINGLE_INPUT_NAVIGATION_EXTRA) {
                type = SingleInputModelNavType()
            }
        )
    ) { backStackEntry ->
        val singleInputModel: SingleInputModel? =
            backStackEntry.arguments?.getString(SINGLE_INPUT_NAVIGATION_EXTRA)?.let {
                Json.decodeFromString<SingleInputModel>(it)
            }
        SingleInputRoute(
            singleInputModel = singleInputModel,
            onNavigateBack = onNavigateBack,
            onSaveAction = onSaveAction
        )
    }
}
