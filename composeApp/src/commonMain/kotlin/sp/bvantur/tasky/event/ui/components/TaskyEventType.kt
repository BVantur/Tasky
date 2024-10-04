package sp.bvantur.tasky.event.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import sp.bvantur.tasky.core.ui.theme.eventChoreTitleType

@Composable
fun TaskyEventType(modifier: Modifier = Modifier, choreColor: Color, text: String) {
    Row(modifier = modifier) {
        Box(modifier = Modifier.size(20.dp).background(choreColor))

        Text(
            text = text,
            modifier = Modifier.padding(start = 10.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.eventChoreTitleType
        )
    }
}
