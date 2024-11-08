package sp.bvantur.tasky.core.ui.navigation.event

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import sp.bvantur.tasky.agenda.presentation.AgendaDetailsViewModel.Companion.CREATE_EVENT_AGENDA_ID
import sp.bvantur.tasky.agenda.presentation.AgendaDetailsViewModel.Companion.CREATE_EVENT_IS_EDIT
import sp.bvantur.tasky.agenda.presentation.SingleInputViewModel.Companion.SINGLE_INPUT_NAVIGATION_EXTRA
import sp.bvantur.tasky.agenda.presentation.models.CreateEventUpdatesModel
import sp.bvantur.tasky.agenda.presentation.models.SingleInputModel
import sp.bvantur.tasky.agenda.ui.EventDetailsRoute
import sp.bvantur.tasky.agenda.ui.SingleInputRoute
import sp.bvantur.tasky.agenda.ui.model.SingleInputModelNavType

internal const val CREATE_EVENT_NAVIGATION_ROUTE = "create_event_navigation_route"
internal const val SINGLE_INPUT_NAVIGATION_ROUTE = "single_input_navigation_route"

internal const val SINGLE_INPUT_TITLE_RESULT_EXTRA = "single_input_title_result_extra"
internal const val SINGLE_INPUT_DESCRIPTION_RESULT_EXTRA = "single_input_description_result_extra"

internal fun NavController.navigateToEventDetailsScreen(eventId: String?, isEdit: Boolean) {
    this.navigate(
        route = "$CREATE_EVENT_NAVIGATION_ROUTE?${CREATE_EVENT_AGENDA_ID}=${eventId ?: ""}" +
            "&${CREATE_EVENT_IS_EDIT}=$isEdit"
    )
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
        route = "$CREATE_EVENT_NAVIGATION_ROUTE?${CREATE_EVENT_AGENDA_ID}=" +
            "{${CREATE_EVENT_AGENDA_ID}}&${CREATE_EVENT_IS_EDIT}={${CREATE_EVENT_IS_EDIT}}",
        arguments = listOf(
            navArgument(CREATE_EVENT_AGENDA_ID) {
                type = NavType.StringType
                defaultValue = ""
            },
            navArgument(CREATE_EVENT_IS_EDIT) {
                type = NavType.BoolType
                defaultValue = false
            }
        )
    ) { backStackEntry ->
        val title = backStackEntry.savedStateHandle.get<String>(SINGLE_INPUT_TITLE_RESULT_EXTRA)
        val description = backStackEntry.savedStateHandle.get<String>(SINGLE_INPUT_DESCRIPTION_RESULT_EXTRA)
        backStackEntry.savedStateHandle.remove<String>(SINGLE_INPUT_TITLE_RESULT_EXTRA)
        backStackEntry.savedStateHandle.remove<String>(SINGLE_INPUT_DESCRIPTION_RESULT_EXTRA)

        val eventId = backStackEntry.arguments?.getString(CREATE_EVENT_AGENDA_ID)
        val isEdit = backStackEntry.arguments?.getBoolean(CREATE_EVENT_IS_EDIT) ?: false

        EventDetailsRoute(
            CreateEventUpdatesModel(
                title = title,
                description = description
            ),
            eventId = eventId,
            isEdit = isEdit,
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
    ) { _ ->
        SingleInputRoute(
            onNavigateBack = onNavigateBack,
            onSaveAction = onSaveAction
        )
    }
}
