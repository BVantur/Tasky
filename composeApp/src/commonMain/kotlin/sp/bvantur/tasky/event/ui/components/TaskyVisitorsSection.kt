package sp.bvantur.tasky.event.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import tasky.composeapp.generated.resources.Res
import tasky.composeapp.generated.resources.add_visitors
import tasky.composeapp.generated.resources.visitors

@Composable
fun TaskyVisitorsSection(modifier: Modifier, onClick: () -> Unit) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            stringResource(Res.string.visitors),
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary
        )

        IconButton(
            modifier = Modifier.padding(start = 18.dp),
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
