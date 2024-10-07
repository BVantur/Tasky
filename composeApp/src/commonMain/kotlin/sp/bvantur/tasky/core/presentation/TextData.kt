package sp.bvantur.tasky.core.presentation

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

sealed interface TextData {
    data class DynamicString(val value: String) : TextData
    class ResourceString(
        val resId: StringResource,
        vararg val args: Any
    ) : TextData

    @Suppress("SpreadOperator")
    @Composable
    fun asString(): String = when (this) {
        is DynamicString -> value
        is ResourceString -> stringResource(resId, *args)
    }

    fun getFromDynamicStringOrNull(): String? {
        return (this as? DynamicString)?.value
    }
}
