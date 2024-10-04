package sp.bvantur.tasky.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import sp.bvantur.tasky.core.domain.DispatcherProvider

abstract class ViewStateViewModel<State : ViewState>(
    initialViewState: State,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val mutableViewStateFlow = MutableStateFlow(initialViewState)
    val viewStateFlow: StateFlow<State> = mutableViewStateFlow.onStart { initialLoadData() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(SUBSCRIPTION_TIMEOUT),
            initialViewState
        )

    suspend fun emitViewState(onUpdateViewState: (State) -> State) {
        withContext(dispatcherProvider.main.immediate) {
            mutableViewStateFlow.update {
                onUpdateViewState(viewStateFlow.value)
            }
        }
    }

    protected open fun initialLoadData() {}

    private companion object {
        const val SUBSCRIPTION_TIMEOUT = 5000L
    }
}
