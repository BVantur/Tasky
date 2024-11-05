package sp.bvantur.tasky.event.presentation

import kotlinx.datetime.LocalDateTime
import sp.bvantur.tasky.core.presentation.TextData
import sp.bvantur.tasky.core.presentation.ViewState
import sp.bvantur.tasky.event.domain.model.Attendee
import sp.bvantur.tasky.event.domain.model.ReminderValue
import sp.bvantur.tasky.event.presentation.utils.DateTimeUtils
import tasky.composeapp.generated.resources.Res
import tasky.composeapp.generated.resources.event_description
import tasky.composeapp.generated.resources.event_title
import kotlin.time.Duration.Companion.minutes

data class CreateEventViewState(
    val title: TextData = TextData.ResourceString(Res.string.event_title),
    val description: TextData = TextData.ResourceString(Res.string.event_description),
    val showDatePickerDialog: Boolean = false,
    val showTimePickerDialog: Boolean = false,
    val dialogDateTimeData: DialogDateTimeData? = null,
    val dialogSelectedTimeInMillis: Long = 0L,
    val currentFromDateTime: LocalDateTime = DateTimeUtils.getCurrentLocalDateTime(),
    val currentToDateTime: LocalDateTime = DateTimeUtils.getCurrentLocalDateTime(addDuration = 30.minutes),
    val formattedFromDate: String = "",
    val formattedFromTime: String = "",
    val formattedToDate: String = "",
    val formattedToTime: String = "",
    val reminderValue: ReminderValue = ReminderValue.ONE_DAY,
    val showAttendeeDialog: Boolean = false,
    val isAttendeeEmailError: Boolean = false,
    val attendeeInputValue: String = "",
    val attendees: List<Attendee> = listOf(),
    val isSaveEnabled: Boolean = false,
    val isEdit: Boolean = true
) : ViewState

data class DialogDateTimeData(val localDateTime: LocalDateTime, val isFrom: Boolean)
