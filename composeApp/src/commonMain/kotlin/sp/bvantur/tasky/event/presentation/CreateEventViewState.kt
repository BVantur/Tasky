package sp.bvantur.tasky.event.presentation

import kotlinx.datetime.LocalDateTime
import sp.bvantur.tasky.core.presentation.TextData
import sp.bvantur.tasky.core.presentation.ViewState
import sp.bvantur.tasky.event.domain.model.Attendee
import sp.bvantur.tasky.event.presentation.models.ReminderValue
import tasky.composeapp.generated.resources.Res
import tasky.composeapp.generated.resources.event_description
import tasky.composeapp.generated.resources.event_title

data class CreateEventViewState(
    val title: TextData = TextData.ResourceString(Res.string.event_title),
    val description: TextData = TextData.ResourceString(Res.string.event_description),
    val showDatePickerDialog: Boolean = false,
    val showTimePickerDialog: Boolean = false,
    val dialogDateTimeData: DialogDateTimeData? = null,
    val dialogSelectedTimeInMillis: Long = 0L,
    val currentFromDateTime: LocalDateTime? = null,
    val currentToDateTime: LocalDateTime? = null,
    val formattedFromDate: String = "",
    val formattedFromTime: String = "",
    val formattedToDate: String = "",
    val formattedToTime: String = "",
    val reminderValue: ReminderValue = ReminderValue.ONE_DAY,
    val showAttendeeDialog: Boolean = false,
    val isAttendeeEmailError: Boolean = false,
    val attendees: List<Attendee> = listOf()
) : ViewState

data class DialogDateTimeData(val localDateTime: LocalDateTime? = null, val isFrom: Boolean = false)
