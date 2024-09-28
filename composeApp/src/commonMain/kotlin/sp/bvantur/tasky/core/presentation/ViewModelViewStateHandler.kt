package sp.bvantur.tasky.core.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import sp.bvantur.tasky.core.DispatcherProvider

interface ViewModelViewStateHandler<State : ViewState> {
    val viewStateFlow: StateFlow<State>

    suspend fun emitViewState(viewState: State)
}

class ViewModelViewStateHandlerImpl<State : ViewState>(
    initialViewState: State,
    private val dispatcherProvider: DispatcherProvider
) : ViewModelViewStateHandler<State> {
    private val mutableViewStateFlow = MutableStateFlow(initialViewState)
    override val viewStateFlow: StateFlow<State> = mutableViewStateFlow.asStateFlow()

    override suspend fun emitViewState(viewState: State) {
        withContext(dispatcherProvider.main) {
            mutableViewStateFlow.update {
                viewState
            }
        }
    }
}