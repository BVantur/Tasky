package sp.bvantur.tasky.event.presentation

import sp.bvantur.tasky.core.presentation.UserAction

sealed interface CreateEventUserAction : UserAction {
    data object TitleChange : CreateEventUserAction
    data object DescriptionChange : CreateEventUserAction
    data object DateFromChange : CreateEventUserAction
    data object DateToChange : CreateEventUserAction
    data object DismissDateDialog : CreateEventUserAction
    data class SelectNewDate(val dialogDateTimeData: DialogDateTimeData?) : CreateEventUserAction
    data object TimeFromChange : CreateEventUserAction
    data object TimeToChange : CreateEventUserAction
    data class SelectNewTime(val selectedHour: Int, val selectedMinutes: Int, val isFrom: Boolean?) :
        CreateEventUserAction
    data object DismissTimeDialog : CreateEventUserAction
}
