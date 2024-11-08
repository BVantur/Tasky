package sp.bvantur.tasky.agenda.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import sp.bvantur.tasky.core.ui.components.TaskyAgendaText

@Composable
fun TaskyAgendaDescriptionRow(modifier: Modifier = Modifier, text: String, isEnabled: Boolean, onClick: () -> Unit) {
    Row(
        modifier = modifier.fillMaxWidth().defaultMinSize(minHeight = 80.dp).clickable(isEnabled) {
            onClick()
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        TaskyAgendaText(
            modifier = Modifier.padding(horizontal = 10.dp).weight(1f),
            text = text
        )

        if (isEnabled) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowForwardIos,
                modifier = Modifier.padding(end = 25.dp).size(12.dp),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
