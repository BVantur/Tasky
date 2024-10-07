package sp.bvantur.tasky.event.presentation

import sp.bvantur.tasky.core.presentation.TextData
import sp.bvantur.tasky.core.presentation.ViewState

data class SingleInputViewState(
    val title: TextData = TextData.DynamicString(""),
    val isTitle: Boolean = true,
    val placeholder: TextData = TextData.DynamicString(""),
    val value: String = "",
    val isSaveButtonEnabled: Boolean = false
) : ViewState
