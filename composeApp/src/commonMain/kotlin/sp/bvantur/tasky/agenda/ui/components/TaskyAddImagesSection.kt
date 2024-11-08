package sp.bvantur.tasky.agenda.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import tasky.composeapp.generated.resources.Res
import tasky.composeapp.generated.resources.add_photos

@Composable
fun TaskyAddImagesSection(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth().defaultMinSize(minHeight = 110.dp)
            .background(MaterialTheme.colorScheme.tertiary),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            modifier = Modifier.size(35.dp),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onTertiary
        )

        Text(
            text = stringResource(Res.string.add_photos),
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 16.dp),
        )
    }
}
