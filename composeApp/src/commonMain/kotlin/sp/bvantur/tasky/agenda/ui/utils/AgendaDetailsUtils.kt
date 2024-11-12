package sp.bvantur.tasky.agenda.ui.utils

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.StringResource
import sp.bvantur.tasky.core.domain.model.AgendaType
import sp.bvantur.tasky.core.ui.theme.Green
import sp.bvantur.tasky.core.ui.theme.Light2
import sp.bvantur.tasky.core.ui.theme.LightGreen
import tasky.composeapp.generated.resources.Res
import tasky.composeapp.generated.resources.event
import tasky.composeapp.generated.resources.reminder
import tasky.composeapp.generated.resources.task

fun AgendaType.toAgendaText(): StringResource = when (this) {
    AgendaType.EVENT -> Res.string.event
    AgendaType.REMINDER -> Res.string.reminder
    AgendaType.TASK -> Res.string.task
}

fun AgendaType.toAgendaColor(): Color = when (this) {
    AgendaType.EVENT -> LightGreen
    AgendaType.REMINDER -> Green
    AgendaType.TASK -> Light2
}
