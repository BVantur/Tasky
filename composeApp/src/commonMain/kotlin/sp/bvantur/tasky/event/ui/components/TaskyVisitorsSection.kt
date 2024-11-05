package sp.bvantur.tasky.event.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.sharp.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import sp.bvantur.tasky.core.ui.components.TaskyInitialsCircle
import sp.bvantur.tasky.core.ui.theme.eventChoreTitleType
import sp.bvantur.tasky.event.domain.model.Attendee
import tasky.composeapp.generated.resources.Res
import tasky.composeapp.generated.resources.add_visitors
import tasky.composeapp.generated.resources.password_visibility_icon
import tasky.composeapp.generated.resources.visitors

@Composable
fun TaskyVisitorsSection(
    attendees: List<Attendee>,
    modifier: Modifier = Modifier,
    isEnabled: Boolean,
    onClick: () -> Unit,
    onDeleteAttendee: (Attendee) -> Unit
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = stringResource(Res.string.visitors),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary
            )

            if (isEnabled) {
                IconButton(
                    modifier = Modifier.padding(start = 16.dp),
                    onClick = onClick
                ) {
                    Icon(
                        modifier = Modifier.size(35.dp).clip(RoundedCornerShape(5.dp))
                            .background(MaterialTheme.colorScheme.tertiary).padding(5.dp),
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(Res.string.add_visitors),
                        tint = MaterialTheme.colorScheme.onTertiary
                    )
                }
            }
        }

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(attendees.size) { index ->
                AttendeeItem(
                    attendee = attendees[index],
                    isEnabled = isEnabled,
                    onDeleteAttendee = onDeleteAttendee
                )
            }
        }
    }
}

@Composable
private fun AttendeeItem(
    modifier: Modifier = Modifier,
    attendee: Attendee,
    isEnabled: Boolean,
    onDeleteAttendee: (Attendee) -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth().padding(all = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 16.dp)
        ) {
            TaskyInitialsCircle(
                attendee.name,
                modifier = Modifier.padding(vertical = 6.dp)
            )

            Text(
                modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
                text = attendee.name,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.eventChoreTitleType
            )

            if (isEnabled) {
                IconButton(onClick = { onDeleteAttendee(attendee) }) {
                    Icon(
                        imageVector = Icons.Sharp.Delete,
                        contentDescription = stringResource(Res.string.password_visibility_icon),
                        tint = MaterialTheme.colorScheme.eventChoreTitleType
                    )
                }
            }
        }
    }
}
