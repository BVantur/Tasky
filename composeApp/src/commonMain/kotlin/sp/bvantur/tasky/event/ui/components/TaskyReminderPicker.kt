package sp.bvantur.tasky.event.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import org.jetbrains.compose.resources.stringResource
import sp.bvantur.tasky.event.presentation.models.ReminderValue

@Composable
fun TaskyReminderPicker(
    modifier: Modifier = Modifier,
    selectedReminderValue: ReminderValue,
    onReminderValueSelected: (ReminderValue) -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    Column {
        DropdownMenu(
            expanded = showMenu,
            offset = DpOffset(16.dp, 0.dp),
            onDismissRequest = { showMenu = false },
            modifier = Modifier
                .wrapContentSize(),
            properties = PopupProperties(focusable = true)
        ) {
            ReminderValue.entries.forEach { reminderValue ->
                DropdownMenuItem(
                    text = { Text(stringResource(reminderValue.stringRes)) },
                    onClick = {
                        onReminderValueSelected(reminderValue)
                        showMenu = false
                    }
                )
            }
        }
        Row(
            modifier = modifier.fillMaxWidth().defaultMinSize(minHeight = 70.dp).clickable {
                showMenu = true
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                modifier = Modifier.size(30.dp).clip(RoundedCornerShape(5.dp))
                    .background(MaterialTheme.colorScheme.tertiary).padding(2.dp),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onTertiary
            )

            Text(
                stringResource(selectedReminderValue.stringRes),
                modifier = Modifier.padding(start = 13.dp),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}
