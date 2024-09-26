package sp.bvantur.tasky.core.ui.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun TaskyHyperlinkText(modifier: Modifier = Modifier, allText: String, hyperlinkText: String, onClick: () -> Unit) {
    val annotation = "clickable"

    val annotatedString = buildAnnotatedString {
        val hyperLinkStartIndex = allText.indexOf(hyperlinkText)

        append(allText.substring(0, hyperLinkStartIndex).uppercase())

        pushStringAnnotation(tag = annotation, annotation = annotation)
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.secondary)) {
            append(hyperlinkText.uppercase())
        }
        pop()

        append(allText.substring(hyperLinkStartIndex + hyperlinkText.length, allText.length).uppercase())
    }

    ClickableText(
        modifier = modifier,
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(
                tag = annotation,
                start = offset,
                end = offset
            ).firstOrNull()?.let {
                onClick()
            }
        },
        style = MaterialTheme.typography.bodyMedium
    )
}
