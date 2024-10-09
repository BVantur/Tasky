package sp.bvantur.tasky.event.presentation

import sp.bvantur.tasky.core.presentation.UserAction

sealed interface CreateEventUserAction : UserAction {
    data object TitleChange : CreateEventUserAction
    data object DescriptionChange : CreateEventUserAction
}
