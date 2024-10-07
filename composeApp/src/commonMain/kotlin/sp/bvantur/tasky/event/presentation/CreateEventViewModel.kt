package sp.bvantur.tasky.event.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sp.bvantur.tasky.core.domain.DispatcherProvider
import sp.bvantur.tasky.core.presentation.SingleEventHandler
import sp.bvantur.tasky.core.presentation.SingleEventHandlerImpl
import sp.bvantur.tasky.core.presentation.TextData
import sp.bvantur.tasky.core.presentation.ViewModelUserActionHandler
import sp.bvantur.tasky.core.presentation.ViewStateViewModel
import sp.bvantur.tasky.event.ui.model.CreateEventModel
import sp.bvantur.tasky.event.ui.model.SingleInputModel

class CreateEventViewModel(
    dispatcherProvider: DispatcherProvider
) : ViewStateViewModel<CreateEventViewState>(
    initialViewState = CreateEventViewState(),
    dispatcherProvider = dispatcherProvider
), ViewModelUserActionHandler<CreateEventUserAction>,
    SingleEventHandler<CreateEventSingleEvent> by SingleEventHandlerImpl(dispatcherProvider) {

    fun onLoadInitialData(eventData: CreateEventModel) {
        val title = eventData.title?.let {
            TextData.DynamicString(it)
        } ?: viewStateFlow.value.title

        val description = eventData.description?.let {
            TextData.DynamicString(it)
        } ?: viewStateFlow.value.description

        viewModelScope.launch {
            emitViewState { viewState ->
                viewState.copy(title = title, description = description)
            }
        }
    }

    override fun onUserAction(userAction: CreateEventUserAction) {
        when (userAction) {
            CreateEventUserAction.TitleChange -> onTitleChange()
            CreateEventUserAction.DescriptionChange -> onDescriptionChange()
        }
    }

    private fun onTitleChange() {
        viewModelScope.launch {
            emitSingleEvent(
                CreateEventSingleEvent.OnOpenSingleInput(
                    SingleInputModel(
                        value = viewStateFlow.value.title.getFromDynamicStringOrNull(),
                        isTitle = true
                    )
                )
            )
        }
    }

    private fun onDescriptionChange() {
        viewModelScope.launch {
            emitSingleEvent(
                CreateEventSingleEvent.OnOpenSingleInput(
                    SingleInputModel(
                        value = viewStateFlow.value.description.getFromDynamicStringOrNull(),
                        isTitle = false
                    )
                )
            )
        }
    }
}
