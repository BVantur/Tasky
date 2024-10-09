package sp.bvantur.tasky.event.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import sp.bvantur.tasky.core.domain.DispatcherProvider
import sp.bvantur.tasky.core.presentation.SingleEventHandler
import sp.bvantur.tasky.core.presentation.SingleEventHandlerImpl
import sp.bvantur.tasky.core.presentation.TextData
import sp.bvantur.tasky.core.presentation.ViewStateViewModel
import sp.bvantur.tasky.event.presentation.models.SingleInputModel
import tasky.composeapp.generated.resources.Res
import tasky.composeapp.generated.resources.enter_description
import tasky.composeapp.generated.resources.enter_title
import tasky.composeapp.generated.resources.event_description
import tasky.composeapp.generated.resources.event_title

class SingleInputViewModel(
    dispatcherProvider: DispatcherProvider,
    savedStateHandle: SavedStateHandle
) :
    ViewStateViewModel<SingleInputViewState>(SingleInputViewState()),
    SingleEventHandler<SingleInputSingleEvent> by SingleEventHandlerImpl(dispatcherProvider) {
    init {
        viewModelScope.launch {
            val inputString = savedStateHandle.get<String?>(SINGLE_INPUT_NAVIGATION_EXTRA) ?: return@launch

            val inputModel: SingleInputModel = Json.decodeFromString(inputString)

            emitViewState { viewState ->
                viewState.copy(
                    title = TextData.ResourceString(
                        if (inputModel.inputType.isTitle()) Res.string.event_title else Res.string.event_description
                    ),
                    isTitle = inputModel.inputType.isTitle(),
                    placeholder = TextData.ResourceString(
                        if (inputModel.inputType.isTitle()) Res.string.enter_title else Res.string.enter_description
                    ),
                    value = inputModel.value ?: "",
                    isSaveButtonEnabled = !inputModel.value.isNullOrEmpty()
                )
            }
        }
    }

    fun onInputAction(input: String) {
        emitViewState { viewState ->
            viewState.copy(
                value = input,
                isSaveButtonEnabled = input.isNotBlank()
            )
        }
    }

    fun onSaveAction() {
        viewModelScope.launch {
            if (viewStateFlow.value.isTitle) {
                emitSingleEvent(SingleInputSingleEvent.SaveTitle(viewStateFlow.value.value))
            } else {
                emitSingleEvent(SingleInputSingleEvent.SaveDescription(viewStateFlow.value.value))
            }
        }
    }

    companion object {
        const val SINGLE_INPUT_NAVIGATION_EXTRA = "single_input_navigation_extra"
    }
}
