package sp.bvantur.tasky.home.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import sp.bvantur.tasky.agenda.domain.model.AgendaType
import sp.bvantur.tasky.core.domain.DispatcherProvider
import sp.bvantur.tasky.core.domain.onSuccess
import sp.bvantur.tasky.core.presentation.SingleEventHandler
import sp.bvantur.tasky.core.presentation.SingleEventHandlerImpl
import sp.bvantur.tasky.core.presentation.ViewModelUserActionHandler
import sp.bvantur.tasky.core.presentation.ViewStateViewModel
import sp.bvantur.tasky.home.domain.HomeRepository

class HomeViewModel(private val repository: HomeRepository, private val dispatcherProvider: DispatcherProvider) :
    ViewStateViewModel<HomeViewState>(HomeViewState()),
    ViewModelUserActionHandler<HomeUserAction>,
    SingleEventHandler<HomeSingleEvent> by SingleEventHandlerImpl(dispatcherProvider) {

    override suspend fun initialLoadData() {
        repository.getAgendaForTheDay(Clock.System.now().toEpochMilliseconds())

        viewModelScope.launch {
            val userName = repository.getProfileName() ?: ""
            emitViewState { viewState ->
                viewState.copy(
                    userName = userName
                )
            }

            async { repository.syncPendingAgendaItems() }
            repository.observeAgendaItems().collect { agendaItems ->
                emitViewState { viewState ->
                    viewState.copy(items = agendaItems)
                }
            }
        }
    }

    override fun onUserAction(userAction: HomeUserAction) {
        when (userAction) {
            HomeUserAction.CreateNewEvent -> {
                viewModelScope.launch {
                    emitSingleEvent(
                        HomeSingleEvent.NavigateToEventDetails(null, true, AgendaType.EVENT)
                    )
                }
            }

            HomeUserAction.LogoutUser -> {
                viewModelScope.launch {
                    repository.logoutUser().onSuccess {
                        emitSingleEvent(HomeSingleEvent.NavigateToLogin)
                    }
                }
            }

            is HomeUserAction.DeleteAgendaItem -> {
                viewModelScope.launch {
                    repository.deleteEventById(id = userAction.agendaItem.id)
                }
            }
            is HomeUserAction.EditAgendaItem -> {
                viewModelScope.launch {
                    emitSingleEvent(
                        HomeSingleEvent.NavigateToEventDetails(
                            userAction.agendaItem.id,
                            true,
                            userAction.agendaItem.type
                        )
                    )
                }
            }
            is HomeUserAction.OpenAgendaItem -> {
                viewModelScope.launch {
                    emitSingleEvent(
                        HomeSingleEvent.NavigateToEventDetails(
                            userAction.agendaItem.id,
                            false,
                            userAction.agendaItem.type
                        )
                    )
                }
            }

            HomeUserAction.CreateNewTask -> {
                viewModelScope.launch {
                    emitSingleEvent(
                        HomeSingleEvent.NavigateToEventDetails(null, true, AgendaType.TASK)
                    )
                }
            }
        }
    }
}
