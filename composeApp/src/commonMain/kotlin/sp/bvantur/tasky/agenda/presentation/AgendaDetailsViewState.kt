package sp.bvantur.tasky.agenda.presentation

import androidx.compose.ui.graphics.Color
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.StringResource
import sp.bvantur.tasky.agenda.domain.model.AgendaType
import sp.bvantur.tasky.agenda.domain.model.Attendee
import sp.bvantur.tasky.agenda.domain.model.ReminderValue
import sp.bvantur.tasky.agenda.presentation.utils.DateTimeUtils
import sp.bvantur.tasky.core.presentation.TextData
import sp.bvantur.tasky.core.presentation.ViewState
import tasky.composeapp.generated.resources.Res
import tasky.composeapp.generated.resources.event_description
import tasky.composeapp.generated.resources.event_title
import tasky.composeapp.generated.resources.from_word
import kotlin.time.Duration.Companion.minutes

data class CreateEventViewState(
    val title: TextData = TextData.ResourceString(Res.string.event_title),
    val description: TextData = TextData.ResourceString(Res.string.event_description),
    val showDatePickerDialog: Boolean = false,
    val showTimePickerDialog: Boolean = false,
    val dialogDateTimeData: DialogDateTimeData? = null,
    val dialogSelectedTimeInMillis: Long = 0L,
    val currentFromDateTime: LocalDateTime = DateTimeUtils.getCurrentLocalDateTime(),
    val formattedFromDate: String = "",
    val formattedFromTime: String = "",
    val fromDateText: TextData = TextData.ResourceString(Res.string.from_word),
    val reminderValue: ReminderValue = ReminderValue.ONE_DAY,
    val showAttendeeDialog: Boolean = false,
    val isAttendeeEmailError: Boolean = false,
    val attendeeInputValue: String = "",
    val attendees: List<Attendee> = listOf(),
    val isSaveEnabled: Boolean = false,
    val isEdit: Boolean = true,
    val agendaTypeDetails: AgendaTypeDetails = AgendaTypeDetails.Event()
) : ViewState

data class DialogDateTimeData(val localDateTime: LocalDateTime, val isFrom: Boolean)

sealed class AgendaTypeDetails(val agendaTypeText: StringResource, val agendaTypeColor: Color) {
    data class Event(
        val toDateTime: LocalDateTime = DateTimeUtils.getCurrentLocalDateTime(addDuration = 30.minutes),
        val formattedToDate: String = "",
        val formattedToTime: String = "",
        val showAttendeeDialog: Boolean = false,
        val isAttendeeEmailError: Boolean = false,
        val attendeeInputValue: String = "",
        val attendees: List<Attendee> = listOf()
    ) : AgendaTypeDetails(
        AgendaType.EVENT.text,
        AgendaType.EVENT.color
    )
    data object Task : AgendaTypeDetails(
        AgendaType.TASK.text,
        AgendaType.TASK.color
    )
    data object Reminder : AgendaTypeDetails(
        AgendaType.REMINDER.text,
        AgendaType.REMINDER.color
    )

    fun getAsEventType(): Event? = this as? Event

    companion object {
        fun fromAgendaType(agendaType: AgendaType): AgendaTypeDetails = when (agendaType) {
            AgendaType.EVENT -> Event()
            AgendaType.TASK -> Task
            AgendaType.REMINDER -> Reminder
        }
    }
}
