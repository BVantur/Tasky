package sp.bvantur.tasky.home.presentation

import sp.bvantur.tasky.core.presentation.UserAction
import sp.bvantur.tasky.home.domain.model.AgendaItem

sealed interface HomeUserAction : UserAction {
    data object CreateNewEvent : HomeUserAction
    data object LogoutUser : HomeUserAction
    data class OpenAgendaItem(val agendaItem: AgendaItem) : HomeUserAction
    data class EditAgendaItem(val agendaItem: AgendaItem) : HomeUserAction
    data class DeleteAgendaItem(val agendaItem: AgendaItem) : HomeUserAction
}
