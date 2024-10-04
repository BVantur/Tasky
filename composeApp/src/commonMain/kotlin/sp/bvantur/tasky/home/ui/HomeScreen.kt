package sp.bvantur.tasky.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.window.PopupProperties
import org.jetbrains.compose.resources.stringResource
import tasky.composeapp.generated.resources.Res
import tasky.composeapp.generated.resources.event

@Composable
fun HomeRoute(onCreateEventAction: () -> Unit) {
    HomeScreen(
        onCreateEventAction = onCreateEventAction
    )
}

@Composable
private fun HomeScreen(onCreateEventAction: () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface),
        floatingActionButton = {
            FloatingActionButtonWithDropdown(
                onCreateEventAction = onCreateEventAction
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)) {
            Text(
                "Home screen",
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.onSurface
            )
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
