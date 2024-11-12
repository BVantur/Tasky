package sp.bvantur.tasky.agenda.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sp.bvantur.tasky.agenda.data.utils.AgendaUtils
import sp.bvantur.tasky.agenda.domain.AgendaRepository
import sp.bvantur.tasky.agenda.domain.model.Attendee
import sp.bvantur.tasky.agenda.domain.model.Event
import sp.bvantur.tasky.agenda.presentation.models.CreateEventUpdatesModel
import sp.bvantur.tasky.agenda.presentation.models.InputType
import sp.bvantur.tasky.agenda.presentation.models.SingleInputModel
import sp.bvantur.tasky.agenda.presentation.utils.DateTimeUtils
import sp.bvantur.tasky.core.domain.DispatcherProvider
import sp.bvantur.tasky.core.domain.ValidateEmailUseCase
import sp.bvantur.tasky.core.domain.extensions.adoptFromDate
import sp.bvantur.tasky.core.domain.extensions.adoptToDate
import sp.bvantur.tasky.core.domain.extensions.changeOnlyDate
import sp.bvantur.tasky.core.domain.extensions.changeOnlyTime
import sp.bvantur.tasky.core.domain.extensions.formatDate
import sp.bvantur.tasky.core.domain.extensions.formatTime
import sp.bvantur.tasky.core.domain.extensions.getMillis
import sp.bvantur.tasky.core.domain.onError
import sp.bvantur.tasky.core.domain.onSuccess
import sp.bvantur.tasky.core.presentation.SingleEventHandler
import sp.bvantur.tasky.core.presentation.SingleEventHandlerImpl
import sp.bvantur.tasky.core.presentation.TextData
import sp.bvantur.tasky.core.presentation.ViewModelUserActionHandler
import sp.bvantur.tasky.core.presentation.ViewStateViewModel

class AgendaDetailsViewModel(
    private val eventId: String,
    private val isEditMode: Boolean,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val agendaRepository: AgendaRepository,
    dispatcherProvider: DispatcherProvider
) : ViewStateViewModel<CreateEventViewState>(CreateEventViewState()),
    ViewModelUserActionHandler<AgendaDetailsUserAction>,
    SingleEventHandler<AgendaDetailsSingleEvent> by SingleEventHandlerImpl(dispatcherProvider) {

    override suspend fun initialLoadData() {
        super.initialLoadData()

        if (eventId.isNotEmpty()) {
            viewModelScope.launch {
                agendaRepository.getEventById(eventId).onError {
                    // TODO handle error
                }.onSuccess { event ->
                    event ?: return@launch

                    emitViewState { viewState ->
                        viewState.copy(
                            title = TextData.DynamicString(event.title),
                            description = TextData.DynamicString(event.description),
                            currentFromDateTime = event.fromTime,
                            formattedFromDate = event.fromTime.formatDate(),
                            formattedFromTime = event.fromTime.formatTime(),
                            currentToDateTime = event.toTime,
                            formattedToDate = event.toTime.formatDate(),
                            formattedToTime = event.toTime.formatTime(),
                            reminderValue = event.reminder,
                            attendees = event.attendees,
                            isEdit = isEditMode,
                            isSaveEnabled = isEditMode
                        )
                    }
                }
            }
            return
        }

        onDefaultEventDetailsLoad()
    }

    fun onUpdateTextFields(eventData: CreateEventUpdatesModel) {
        val (title, description) = eventData

        if (title == null && description == null) return

        val newTitle = title?.let {
            TextData.DynamicString(it)
        } ?: viewStateFlow.value.title

        val newDescription = description?.let {
            TextData.DynamicString(it)
        } ?: viewStateFlow.value.description

        emitViewState { viewState ->
            viewState.copy(
                title = newTitle,
                description = newDescription,
                isSaveEnabled = canEventBeSaved(
                    title = newTitle,
                    description = newDescription
                )
            )
        }
    }

    override fun onUserAction(userAction: AgendaDetailsUserAction) {
        when (userAction) {
            AgendaDetailsUserAction.TitleChange -> onTitleChange()
            AgendaDetailsUserAction.DescriptionChange -> onDescriptionChange()
            AgendaDetailsUserAction.DateFromChange -> onDateFromChange()
            AgendaDetailsUserAction.DateToChange -> onDateToChange()
            AgendaDetailsUserAction.DismissDateDialog -> onDismissDialog(showDatePickerDialog = false)
            AgendaDetailsUserAction.DismissTimeDialog -> onDismissDialog(showTimePickerDialog = false)
            AgendaDetailsUserAction.TimeFromChange -> onTimeFromChange()
            AgendaDetailsUserAction.TimeToChange -> onTimeToChange()
            is AgendaDetailsUserAction.SelectNewDate -> onNewDateSelected(userAction.dialogDateTimeData)
            is AgendaDetailsUserAction.SelectNewTime -> onNewTimeSelected(
                userAction.selectedHour,
                userAction.selectedMinutes,
                userAction.isFrom
            )

            is AgendaDetailsUserAction.SelectNewReminder -> emitViewState { viewState ->
                viewState.copy(reminderValue = userAction.reminderValue)
            }

            AgendaDetailsUserAction.InviteNewAttendee -> emitViewState { viewState ->
                viewState.copy(showAttendeeDialog = true)
            }

            is AgendaDetailsUserAction.ConfirmAttendeeEmail -> onInviteAttendee()

            AgendaDetailsUserAction.DismissAttendeeDialog -> emitViewState { viewState ->
                viewState.copy(showAttendeeDialog = false)
            }

            is AgendaDetailsUserAction.AttendeeEmailChange -> onAttendeeEmailChange(userAction.email)
            is AgendaDetailsUserAction.OnRemoveAttendee -> onRemoveAttendee(userAction.attendee)
            AgendaDetailsUserAction.SaveAgendaDetails -> onSaveEvent()
            AgendaDetailsUserAction.ToEditMode -> emitViewState { viewState ->
                viewState.copy(isEdit = true)
            }
        }
    }

    private fun onTitleChange() {
        viewModelScope.launch {
            emitSingleEvent(
                AgendaDetailsSingleEvent.OnOpenDetailsSingleInput(
                    SingleInputModel(
                        value = viewStateFlow.value.title.getFromDynamicStringOrNull(),
                        inputType = InputType.TITLE
                    )
                )
            )
        }
    }

    private fun onDescriptionChange() {
        viewModelScope.launch {
            emitSingleEvent(
                AgendaDetailsSingleEvent.OnOpenDetailsSingleInput(
                    SingleInputModel(
                        value = viewStateFlow.value.description.getFromDynamicStringOrNull(),
                        inputType = InputType.DESCRIPTION
                    )
                )
            )
        }
    }

    private fun onDateFromChange() {
        emitViewState { viewState ->
            viewState.copy(
                showDatePickerDialog = true,
                dialogDateTimeData = DialogDateTimeData(
                    localDateTime = viewState.currentFromDateTime,
                    isFrom = true
                )
            )
        }
    }

    private fun onDateToChange() {
        emitViewState { viewState ->
            viewState.copy(
                showDatePickerDialog = true,
                dialogDateTimeData = DialogDateTimeData(
                    localDateTime = viewState.currentToDateTime,
                    isFrom = false
                )
            )
        }
    }

    private fun onTimeFromChange() {
        emitViewState { viewState ->
            viewState.copy(
                showTimePickerDialog = true,
                dialogDateTimeData = DialogDateTimeData(
                    localDateTime = viewState.currentFromDateTime,
                    isFrom = true
                )
            )
        }
    }

    private fun onTimeToChange() {
        emitViewState { viewState ->
            viewState.copy(
                showTimePickerDialog = true,
                dialogDateTimeData = DialogDateTimeData(
                    localDateTime = viewState.currentToDateTime,
                    isFrom = false
                )
            )
        }
    }

    private fun onDismissDialog(
        showDatePickerDialog: Boolean = viewStateFlow.value.showDatePickerDialog,
        showTimePickerDialog: Boolean = viewStateFlow.value.showTimePickerDialog
    ) {
        emitViewState { viewState ->
            viewState.copy(
                showTimePickerDialog = showTimePickerDialog,
                showDatePickerDialog = showDatePickerDialog
            )
        }
    }

    private fun onNewDateSelected(dialogDateTimeData: DialogDateTimeData?) {
        dialogDateTimeData ?: return

        emitViewState { viewState ->
            val (fromDateTime, toDateTime) = if (dialogDateTimeData.isFrom) {
                val newFromDate = viewState.currentFromDateTime.changeOnlyDate(dialogDateTimeData.localDateTime)
                newFromDate to newFromDate.adoptToDate(viewState.currentToDateTime)
            } else {
                val newToDateTime = viewState.currentToDateTime.changeOnlyDate(dialogDateTimeData.localDateTime)
                newToDateTime.adoptFromDate(viewState.currentFromDateTime) to newToDateTime
            }

            viewState.copy(
                showDatePickerDialog = false,
                currentFromDateTime = fromDateTime,
                currentToDateTime = toDateTime,
                formattedFromDate = fromDateTime.formatDate(),
                formattedFromTime = fromDateTime.formatTime(),
                formattedToDate = toDateTime.formatDate(),
                formattedToTime = toDateTime.formatTime()
            )
        }
    }

    private fun onNewTimeSelected(selectedHour: Int, selectedMinutes: Int, isFrom: Boolean?) {
        emitViewState { viewState ->
            val (fromDateTime, toDateTime) = if (isFrom == true) {
                val newFromDate = viewState.currentFromDateTime.changeOnlyTime(selectedHour, selectedMinutes)
                newFromDate to newFromDate.adoptToDate(viewState.currentToDateTime)
            } else {
                val newToDateTime = viewState.currentToDateTime.changeOnlyTime(selectedHour, selectedMinutes)
                newToDateTime.adoptFromDate(viewState.currentFromDateTime) to newToDateTime
            }

            viewState.copy(
                showTimePickerDialog = false,
                currentFromDateTime = fromDateTime,
                currentToDateTime = toDateTime,
                formattedFromDate = fromDateTime.formatDate(),
                formattedFromTime = fromDateTime.formatTime(),
                formattedToDate = toDateTime.formatDate(),
                formattedToTime = toDateTime.formatTime()
            )
        }
    }

    private fun onAttendeeEmailChange(text: String) {
        emitViewState { viewState ->
            viewState.copy(
                isAttendeeEmailError = if (viewStateFlow.value.isAttendeeEmailError) {
                    !validateEmailUseCase(
                        text
                    )
                } else {
                    false
                },
                attendeeInputValue = text
            )
        }
    }

    private fun onInviteAttendee() {
        val email = viewStateFlow.value.attendeeInputValue
        val isEmailValid = validateEmailUseCase(email)
        if (!isEmailValid) {
            emitViewState { viewState ->
                viewState.copy(isAttendeeEmailError = true)
            }
            return
        }

        viewModelScope.launch {
            agendaRepository.getAttendee(email).onError {
                emitViewState { viewState ->
                    viewState.copy(
                        isAttendeeEmailError = true
                    )
                }
            }.onSuccess { data ->
                emitViewState { viewState ->
                    if (viewStateFlow.value.attendees.any {
                            data.userId == it.userId
                        }
                    ) {
                        // TODO handle with a better UI
                        viewState.copy(
                            isAttendeeEmailError = true
                        )
                    } else {
                        viewState.copy(
                            attendees = listOf(data) + viewState.attendees,
                            showAttendeeDialog = false,
                            attendeeInputValue = ""
                        )
                    }
                }
            }
        }
    }

    private fun onRemoveAttendee(attendee: Attendee) {
        emitViewState { viewState ->
            viewState.copy(
                attendees = viewState.attendees.filter { attendee.userId != it.userId }
            )
        }
    }

    private fun onSaveEvent() {
        if (eventId.isNotEmpty()) {
            // TODO patch
            return
        }
        viewModelScope.launch {
            val currentViewState = viewStateFlow.value
            agendaRepository.createEvent(
                Event(
                    eventId = AgendaUtils.generateAgendaId(),
                    title = currentViewState.title.getFromDynamicStringOrNull() ?: "",
                    description = currentViewState.description.getFromDynamicStringOrNull() ?: "",
                    fromTime = currentViewState.currentFromDateTime,
                    toTime = currentViewState.currentToDateTime,
                    reminder = currentViewState.reminderValue,
                    attendees = currentViewState.attendees,
                )
            ).onError {
                if (it.isSyncError()) {
                    // TODO send a message that event will be saved after phone gets back internet connection
                    emitSingleEvent(AgendaDetailsSingleEvent.CloseScreen)
                    return@onError
                }
                println("ERROR: $it")
                // TODO handle error
            }.onSuccess {
                emitSingleEvent(AgendaDetailsSingleEvent.CloseScreen)
            }
        }
    }

    private fun canEventBeSaved(title: TextData, description: TextData): Boolean =
        title.getFromDynamicStringOrNull() != null && description.getFromDynamicStringOrNull() != null

    private fun onDefaultEventDetailsLoad() {
        val fromDateTime = DateTimeUtils.toLocalDateTime(viewStateFlow.value.currentFromDateTime.getMillis())
        val toDateTime =
            DateTimeUtils.toLocalDateTime(viewStateFlow.value.currentToDateTime.getMillis())

        emitViewState { viewState ->
            viewState.copy(
                currentFromDateTime = fromDateTime,
                formattedFromDate = fromDateTime.formatDate(),
                formattedFromTime = fromDateTime.formatTime(),
                currentToDateTime = toDateTime,
                formattedToDate = toDateTime.formatDate(),
                formattedToTime = toDateTime.formatTime(),
                isEdit = isEditMode
            )
        }
    }

    companion object {
        const val CREATE_EVENT_AGENDA_ID = "create_event_agenda_id"
        const val CREATE_EVENT_IS_EDIT = "create_event_is_edit"
    }
}
