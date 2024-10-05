package sp.bvantur.tasky.event.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TaskyTimeDatePicker(text: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth().defaultMinSize(minHeight = 70.dp).padding(horizontal = 25.dp)
    ) {
        Text(
            text,
            modifier = Modifier.width(50.dp),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            "08:00",
            modifier = Modifier.weight(1f).padding(start = 17.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        ) // TODO text
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.ArrowForwardIos,
            modifier = Modifier.padding(end = 25.dp).size(12.dp),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            "Jul 21 2022",
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        ) // TODO text
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.ArrowForwardIos,
            modifier = Modifier.size(12.dp),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}
