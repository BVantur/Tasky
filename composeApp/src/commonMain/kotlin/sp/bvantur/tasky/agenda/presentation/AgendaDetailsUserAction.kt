package sp.bvantur.tasky.agenda.presentation

import sp.bvantur.tasky.agenda.domain.model.Attendee
import sp.bvantur.tasky.agenda.domain.model.ReminderValue
import sp.bvantur.tasky.core.presentation.UserAction

sealed interface AgendaDetailsUserAction : UserAction {
    data object TitleChange : AgendaDetailsUserAction
    data object DescriptionChange : AgendaDetailsUserAction
    data object DateFromChange : AgendaDetailsUserAction
    data object DateToChange : AgendaDetailsUserAction
    data object DismissDateDialog : AgendaDetailsUserAction
    data class SelectNewDate(val dialogDateTimeData: DialogDateTimeData?) : AgendaDetailsUserAction
    data object TimeFromChange : AgendaDetailsUserAction
    data object TimeToChange : AgendaDetailsUserAction
    data class SelectNewTime(val selectedHour: Int, val selectedMinutes: Int, val isFrom: Boolean?) :
        AgendaDetailsUserAction
    data object DismissTimeDialog : AgendaDetailsUserAction
    data class SelectNewReminder(val reminderValue: ReminderValue) : AgendaDetailsUserAction
    data object InviteNewAttendee : AgendaDetailsUserAction
    data object DismissAttendeeDialog : AgendaDetailsUserAction
    data object ConfirmAttendeeEmail : AgendaDetailsUserAction
    data class AttendeeEmailChange(val email: String) : AgendaDetailsUserAction
    data class OnRemoveAttendee(val attendee: Attendee) : AgendaDetailsUserAction
    data object SaveAgendaDetails : AgendaDetailsUserAction
    data object ToEditMode : AgendaDetailsUserAction
}
