package sp.bvantur.tasky.agenda.domain.model

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.StringResource
import sp.bvantur.tasky.core.ui.theme.Green
import sp.bvantur.tasky.core.ui.theme.Light2
import sp.bvantur.tasky.core.ui.theme.LightGreen
import tasky.composeapp.generated.resources.Res
import tasky.composeapp.generated.resources.event
import tasky.composeapp.generated.resources.reminder
import tasky.composeapp.generated.resources.task

enum class AgendaType(val text: StringResource, val color: Color) {
    EVENT(Res.string.event, LightGreen),
    TASK(Res.string.task, Green),
    REMINDER(Res.string.reminder, Light2)
}
