package sp.bvantur.tasky.home.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import sp.bvantur.tasky.core.presentation.ViewStateViewModel
import sp.bvantur.tasky.home.domain.HomeRepository

class HomeViewModel(private val repository: HomeRepository) : ViewStateViewModel<HomeViewState>(HomeViewState()) {

    override suspend fun initialLoadData() {
        repository.getAgendaForTheDay(Clock.System.now().toEpochMilliseconds())

        viewModelScope.launch {
            async { repository.syncPendingAgendaItems() }
            repository.observeAgendaItems().collect { agendaItems ->
                emitViewState { viewState ->
                    viewState.copy(items = agendaItems)
                }
            }
        }
    }
}
