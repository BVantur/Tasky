package sp.bvantur.tasky.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import sp.bvantur.tasky.core.domain.model.AgendaType
import sp.bvantur.tasky.core.ui.components.TaskyAgendaText
import sp.bvantur.tasky.core.ui.components.TaskyContentSurface
import sp.bvantur.tasky.core.ui.components.TaskyEventTitle
import sp.bvantur.tasky.core.ui.components.TaskyInitialsCircle
import sp.bvantur.tasky.core.ui.theme.eventChoreTitleType
import sp.bvantur.tasky.core.ui.theme.eventChoreType
import sp.bvantur.tasky.core.ui.theme.onEventType
import sp.bvantur.tasky.core.ui.utils.CollectSingleEventsWithLifecycle
import sp.bvantur.tasky.home.domain.model.AgendaItem
import sp.bvantur.tasky.home.presentation.HomeSingleEvent
import sp.bvantur.tasky.home.presentation.HomeUserAction
import sp.bvantur.tasky.home.presentation.HomeViewModel
import sp.bvantur.tasky.home.presentation.HomeViewState
import tasky.composeapp.generated.resources.Res
import tasky.composeapp.generated.resources.delete
import tasky.composeapp.generated.resources.edit
import tasky.composeapp.generated.resources.event
import tasky.composeapp.generated.resources.logout
import tasky.composeapp.generated.resources.open
import tasky.composeapp.generated.resources.password_visibility_icon
import tasky.composeapp.generated.resources.task

@Composable
fun HomeRoute(onEventDetailsAction: (String?, Boolean, AgendaType) -> Unit, onLoginAction: () -> Unit) {
    val viewModel = koinViewModel<HomeViewModel>()

    val viewState: HomeViewState by viewModel.viewStateFlow.collectAsStateWithLifecycle()

    CollectSingleEventsWithLifecycle(singleEventFlow = viewModel.singleEventFlow) { singleEvent ->
        when (singleEvent) {
            HomeSingleEvent.NavigateToLogin -> {
                onLoginAction()
            }

            is HomeSingleEvent.NavigateToEventDetails -> {
                onEventDetailsAction(singleEvent.eventId, singleEvent.isEditMode, singleEvent.agendaType)
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
                },
                onCreateTaskAction = {
                    onUserAction(HomeUserAction.CreateNewTask)
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
                    Spacer(modifier = Modifier.height(40.dp))
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        items(viewState.items.size) { index ->
                            AgendaItemCard(
                                item = viewState.items[index],
                                onUserAction = onUserAction
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FloatingActionButtonWithDropdown(onCreateEventAction: () -> Unit, onCreateTaskAction: () -> Unit) {
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
            DropdownMenuItem(
                text = { Text(stringResource(Res.string.task)) },
                onClick = {
                    showMenu = false
                    onCreateTaskAction()
                }
            )
        }
    }
}

@Composable
private fun ProfileInitials(modifier: Modifier = Modifier, name: String, onLogoutAction: () -> Unit) {
    var showMenu by rememberSaveable { mutableStateOf(false) }
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

@Composable
private fun AgendaItemCard(modifier: Modifier = Modifier, item: AgendaItem, onUserAction: (HomeUserAction) -> Unit) {
    Card(
        modifier = modifier.fillMaxWidth().padding(horizontal = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.eventChoreType,
        )
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TaskyEventTitle(
                    modifier = Modifier.padding(all = 16.dp).weight(1f),
                    text = item.title,
                    color = MaterialTheme.colorScheme.onEventType
                )
                AgendaMoreIcon(
                    item = item,
                    onOpenAction = { onUserAction(HomeUserAction.OpenAgendaItem(it)) },
                    onEditAction = { onUserAction(HomeUserAction.EditAgendaItem(it)) },
                    onDeleteAction = { onUserAction(HomeUserAction.DeleteAgendaItem(it)) }
                )
            }
            TaskyAgendaText(
                modifier = Modifier.padding(start = 50.dp, end = 16.dp).defaultMinSize(minHeight = 36.dp),
                text = item.description,
                color = MaterialTheme.colorScheme.eventChoreTitleType
            )
            // TODO change text here
            TaskyAgendaText(
                modifier = Modifier.padding(end = 16.dp, top = 8.dp, bottom = 12.dp).align(Alignment.End),
                text = item.description,
                color = MaterialTheme.colorScheme.eventChoreTitleType
            )
        }
    }
}

@Composable
fun AgendaMoreIcon(
    modifier: Modifier = Modifier,
    item: AgendaItem,
    onOpenAction: (AgendaItem) -> Unit,
    onEditAction: (AgendaItem) -> Unit,
    onDeleteAction: (AgendaItem) -> Unit,
) {
    var showMenu by rememberSaveable { mutableStateOf(false) }
    Column(modifier = modifier) {
        IconButton(
            modifier = Modifier.padding(all = 8.dp),
            onClick = {
                showMenu = !showMenu
            }
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Rounded.MoreHoriz,
                contentDescription = stringResource(Res.string.password_visibility_icon),
                tint = MaterialTheme.colorScheme.onEventType
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
                text = { Text(stringResource(Res.string.open)) },
                onClick = {
                    showMenu = false
                    onOpenAction(item)
                }
            )
            DropdownMenuItem(
                text = { Text(stringResource(Res.string.edit)) },
                onClick = {
                    showMenu = false
                    onEditAction(item)
                }
            )
            DropdownMenuItem(
                text = { Text(stringResource(Res.string.delete)) },
                onClick = {
                    showMenu = false
                    onDeleteAction(item)
                }
            )
        }
    }
}
