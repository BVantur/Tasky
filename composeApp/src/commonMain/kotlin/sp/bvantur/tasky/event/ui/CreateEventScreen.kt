package sp.bvantur.tasky.event.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import sp.bvantur.tasky.core.ui.components.TaskyContentSurface
import sp.bvantur.tasky.core.ui.theme.eventChoreType
import sp.bvantur.tasky.event.ui.components.TaskyAddImagesSection
import sp.bvantur.tasky.event.ui.components.TaskyEventDescription
import sp.bvantur.tasky.event.ui.components.TaskyEventDivider
import sp.bvantur.tasky.event.ui.components.TaskyEventTitle
import sp.bvantur.tasky.event.ui.components.TaskyEventType
import sp.bvantur.tasky.event.ui.components.TaskyReminderPicker
import sp.bvantur.tasky.event.ui.components.TaskyTimeDatePicker
import sp.bvantur.tasky.event.ui.components.TaskyVisitorsSection
import tasky.composeapp.generated.resources.Res
import tasky.composeapp.generated.resources.event
import tasky.composeapp.generated.resources.event_description
import tasky.composeapp.generated.resources.event_title
import tasky.composeapp.generated.resources.password_visibility_icon

@Composable
fun CreateEventRoute(onNavigateBack: () -> Unit) {
    CreateEventScreen(onNavigateBack = onNavigateBack)
}

@Composable
fun CreateEventScreen(onNavigateBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(Res.string.password_visibility_icon),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            Text(
                modifier = Modifier.weight(1f).padding(vertical = 20.dp),
                text = "04. OCTOBER 2024".uppercase(), // TODO fix this text
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            IconButton(onClick = {
                // TODO
            }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(Res.string.password_visibility_icon),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        TaskyContentSurface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(top = 30.dp)) {
                TaskyEventType(
                    modifier = Modifier.padding(start = 16.dp),
                    text = stringResource(Res.string.event),
                    choreColor = MaterialTheme.colorScheme.eventChoreType
                )

                TaskyEventTitle(
                    modifier = Modifier.padding(start = 16.dp),
                    text = stringResource(Res.string.event_title),
                    onClick = {
                        // TODO
                    }
                )

                TaskyEventDivider()

                TaskyEventDescription(
                    modifier = Modifier.padding(start = 16.dp),
                    text = stringResource(Res.string.event_description),
                    onClick = {
                        // TODO
                    }
                )

                TaskyAddImagesSection()

                TaskyEventDivider(modifier = Modifier.padding(top = 30.dp))

                TaskyTimeDatePicker(text = "From") // TODO fix text
                TaskyEventDivider()
                TaskyTimeDatePicker(text = "To") // TODO fix text
                TaskyEventDivider()
                TaskyReminderPicker(
                    modifier = Modifier.padding(start = 16.dp),
                    onClick = {
                        // TODO
                    }
                )
                TaskyEventDivider()

                TaskyVisitorsSection(modifier = Modifier.padding(start = 16.dp), onClick = {
                    // TODO
                })
            }
        }
    }
}
