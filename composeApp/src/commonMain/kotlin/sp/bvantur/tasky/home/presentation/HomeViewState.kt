package sp.bvantur.tasky.home.presentation

import sp.bvantur.tasky.core.presentation.ViewState
import sp.bvantur.tasky.home.domain.model.AgendaItem

data class HomeViewState(val items: List<AgendaItem> = listOf()) : ViewState
