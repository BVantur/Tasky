package sp.bvantur.tasky.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import sp.bvantur.tasky.core.ui.components.TaskyContentSurface
import sp.bvantur.tasky.core.ui.components.TaskyInitialsCircle
import sp.bvantur.tasky.core.ui.utils.CollectSingleEventsWithLifecycle
import sp.bvantur.tasky.home.presentation.HomeSingleEvent
import sp.bvantur.tasky.home.presentation.HomeUserAction
import sp.bvantur.tasky.home.presentation.HomeViewModel
import sp.bvantur.tasky.home.presentation.HomeViewState
import tasky.composeapp.generated.resources.Res
import tasky.composeapp.generated.resources.event
import tasky.composeapp.generated.resources.logout

@Composable
fun HomeRoute(onCreateEventAction: (Long, Long) -> Unit, onLoginAction: () -> Unit) {
    val viewModel = koinViewModel<HomeViewModel>()

    val viewState: HomeViewState by viewModel.viewStateFlow.collectAsStateWithLifecycle()

    CollectSingleEventsWithLifecycle(singleEventFlow = viewModel.singleEventFlow) { singleEvent ->
        when (singleEvent) {
            is HomeSingleEvent.NavigateToCreateEvent -> {
                onCreateEventAction(singleEvent.fromTime, singleEvent.toTime)
            }

            HomeSingleEvent.NavigateToLogin -> {
                onLoginAction()
            }
        }
    }

    HomeScreen(
        viewState = viewState,
        onUserAction = viewModel::onUserAction
    )
}

@Composable
private fun HomeScreen(viewState: HomeViewState, onUserAction: (HomeUserAction) -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface),
        floatingActionButton = {
            FloatingActionButtonWithDropdown(
                onCreateEventAction = {
                    onUserAction(HomeUserAction.CreateNewEvent)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                ProfileInitials(modifier = Modifier.align(Alignment.CenterEnd), name = viewState.userName) {
                    onUserAction(HomeUserAction.LogoutUser)
                }
            }

            TaskyContentSurface(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)) {
                    // TODO add UI
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(viewState.items.size) { index ->
                            Text(viewState.items[index].title)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FloatingActionButtonWithDropdown(onCreateEventAction: () -> Unit) {
    var showMenu by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = {
                showMenu = !showMenu
            },
            shape = MaterialTheme.shapes.small,
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }

        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false },
            modifier = Modifier
                .wrapContentSize(),
            properties = PopupProperties(focusable = true)
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(Res.string.event)) },
                onClick = {
                    showMenu = false
                    onCreateEventAction()
                }
            )
        }
    }
}

@Composable
private fun ProfileInitials(modifier: Modifier = Modifier, name: String, onLogoutAction: () -> Unit) {
    var showMenu by remember { mutableStateOf(false) }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End
    ) {
        TaskyInitialsCircle(
            modifier = Modifier.padding(16.dp).clickable {
                showMenu = !showMenu
            },
            value = name
        )

        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false },
            modifier = Modifier
                .wrapContentSize(),
            properties = PopupProperties(focusable = true)
        ) {
            DropdownMenuItem(
                text = { Text(stringResource(Res.string.logout)) },
                onClick = {
                    showMenu = false
                    onLogoutAction()
                }
            )
        }
    }
}
