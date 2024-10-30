package sp.bvantur.tasky.home.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import sp.bvantur.tasky.core.domain.DispatcherProvider
import sp.bvantur.tasky.core.domain.onSuccess
import sp.bvantur.tasky.core.presentation.SingleEventHandler
import sp.bvantur.tasky.core.presentation.SingleEventHandlerImpl
import sp.bvantur.tasky.core.presentation.ViewModelUserActionHandler
import sp.bvantur.tasky.core.presentation.ViewStateViewModel
import sp.bvantur.tasky.home.domain.HomeRepository
import kotlin.time.Duration.Companion.minutes

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
                    // TODO refactor this part
                    val fromTime: Instant = Clock.System.now()
                    val toTime = fromTime.plus(30.minutes).toEpochMilliseconds()
                    emitSingleEvent(
                        HomeSingleEvent.NavigateToCreateEvent(
                            fromTime = fromTime.toEpochMilliseconds(),
                            toTime = toTime
                        )
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
                // TODO
            }
            is HomeUserAction.OpenAgendaItem -> {
                // TODO
            }
        }
    }
}
