package sp.bvantur.tasky.event.presentation

import sp.bvantur.tasky.core.presentation.UserAction
import sp.bvantur.tasky.event.domain.model.Attendee
import sp.bvantur.tasky.event.domain.model.ReminderValue

sealed interface EventDetailsUserAction : UserAction {
    data object TitleChange : EventDetailsUserAction
    data object DescriptionChange : EventDetailsUserAction
    data object DateFromChange : EventDetailsUserAction
    data object DateToChange : EventDetailsUserAction
    data object DismissDateDialog : EventDetailsUserAction
    data class SelectNewDate(val dialogDateTimeData: DialogDateTimeData?) : EventDetailsUserAction
    data object TimeFromChange : EventDetailsUserAction
    data object TimeToChange : EventDetailsUserAction
    data class SelectNewTime(val selectedHour: Int, val selectedMinutes: Int, val isFrom: Boolean?) :
        EventDetailsUserAction
    data object DismissTimeDialog : EventDetailsUserAction
    data class SelectNewReminder(val reminderValue: ReminderValue) : EventDetailsUserAction
    data object InviteNewAttendee : EventDetailsUserAction
    data object DismissAttendeeDialog : EventDetailsUserAction
    data object ConfirmAttendeeEmail : EventDetailsUserAction
    data class AttendeeEmailChange(val email: String) : EventDetailsUserAction
    data class OnRemoveAttendee(val attendee: Attendee) : EventDetailsUserAction
    data object SaveEventDetails : EventDetailsUserAction
    data object ToEditMode : EventDetailsUserAction
}
