package sp.bvantur.tasky.event.presentation

import sp.bvantur.tasky.core.presentation.TextData
import sp.bvantur.tasky.core.presentation.ViewState
import tasky.composeapp.generated.resources.Res
import tasky.composeapp.generated.resources.event_description
import tasky.composeapp.generated.resources.event_title

data class CreateEventViewState(
    val title: TextData = TextData.ResourceString(Res.string.event_title),
    val description: TextData = TextData.ResourceString(Res.string.event_description)
) : ViewState
