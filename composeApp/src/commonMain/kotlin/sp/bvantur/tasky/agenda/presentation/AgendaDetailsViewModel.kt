package sp.bvantur.tasky.agenda.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sp.bvantur.tasky.agenda.data.utils.AgendaUtils
import sp.bvantur.tasky.agenda.domain.AgendaRepository
import sp.bvantur.tasky.agenda.domain.model.AgendaType
import sp.bvantur.tasky.agenda.domain.model.Attendee
import sp.bvantur.tasky.agenda.domain.model.Event
import sp.bvantur.tasky.agenda.domain.model.Task
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
import tasky.composeapp.generated.resources.Res
import tasky.composeapp.generated.resources.at_word
import tasky.composeapp.generated.resources.task_description
import tasky.composeapp.generated.resources.task_title

class AgendaDetailsViewModel(
    private val agendaId: String,
    private val isEditMode: Boolean,
    private val agendaType: AgendaType,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val agendaRepository: AgendaRepository,
    dispatcherProvider: DispatcherProvider
) : ViewStateViewModel<CreateEventViewState>(
    CreateEventViewState(
        agendaTypeDetails = AgendaTypeDetails.fromAgendaType(
            agendaType
        )
    )
),
    ViewModelUserActionHandler<AgendaDetailsUserAction>,
    SingleEventHandler<AgendaDetailsSingleEvent> by SingleEventHandlerImpl(dispatcherProvider) {

    override suspend fun initialLoadData() {
        super.initialLoadData()
        setGeneralDefaultAgendaValues()

        when (agendaType) {
            AgendaType.EVENT -> {
                onLoadEventDetails(agendaId)
            }

            AgendaType.TASK -> {
                onLoadTaskDetails(agendaId)
            }

            AgendaType.REMINDER -> TODO()
        }
    }

    private fun onLoadTaskDetails(agendaId: String?) {
        if (agendaId.isNullOrBlank()) {
            mutableViewStateFlow.update { viewState ->
                viewState.copy(
                    title = TextData.ResourceString(Res.string.task_title),
                    description = TextData.ResourceString(Res.string.task_description),
                    fromDateText = TextData.ResourceString(Res.string.at_word),
                    isEdit = isEditMode
                )
            }
            return
        }
    }

    private suspend fun onLoadEventDetails(agendaId: String?) {
        if (agendaId.isNullOrBlank()) {
            mutableViewStateFlow.update { viewState ->
                val eventTypeDetails =
                    viewState.agendaTypeDetails.getAsEventType() ?: return@update viewState

                val toDateTime =
                    DateTimeUtils.toLocalDateTime(eventTypeDetails.toDateTime.getMillis())

                viewState.copy(
                    agendaTypeDetails = eventTypeDetails.copy(
                        toDateTime = toDateTime,
                        formattedToDate = toDateTime.formatDate(),
                        formattedToTime = toDateTime.formatTime()
                    ),
                    isEdit = isEditMode
                )
            }
            return
        }
        agendaRepository.getEventById(agendaId).onError {
            mutableViewStateFlow.update { viewState ->
                val eventTypeDetails =
                    viewState.agendaTypeDetails.getAsEventType() ?: return@update viewState

                val toDateTime =
                    DateTimeUtils.toLocalDateTime(eventTypeDetails.toDateTime.getMillis())

                viewState.copy(
                    agendaTypeDetails = eventTypeDetails.copy(
                        toDateTime = toDateTime,
                        formattedToDate = toDateTime.formatDate(),
                        formattedToTime = toDateTime.formatTime()
                    ),
                    isEdit = isEditMode
                )
            }
        }.onSuccess { event ->
            event ?: return

            mutableViewStateFlow.update { viewState ->
                val eventTypeDetails =
                    viewState.agendaTypeDetails.getAsEventType() ?: return@update viewState

                viewState.copy(
                    title = TextData.DynamicString(event.title),
                    description = TextData.DynamicString(event.description),
                    currentFromDateTime = event.fromTime,
                    formattedFromDate = event.fromTime.formatDate(),
                    formattedFromTime = event.fromTime.formatTime(),
                    agendaTypeDetails = eventTypeDetails.copy(
                        toDateTime = event.toTime,
                        formattedToDate = event.toTime.formatDate(),
                        attendees = event.attendees,
                        formattedToTime = event.toTime.formatTime()
                    ),
                    reminderValue = event.reminder,
                    attendees = event.attendees,
                    isEdit = isEditMode,
                    isSaveEnabled = isEditMode
                )
            }
        }
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

        mutableViewStateFlow.update { viewState ->
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

            is AgendaDetailsUserAction.SelectNewReminder -> {
                mutableViewStateFlow.update { viewState ->
                    viewState.copy(reminderValue = userAction.reminderValue)
                }
            }

            AgendaDetailsUserAction.InviteNewAttendee -> {
                mutableViewStateFlow.update { viewState ->
                    viewState.copy(showAttendeeDialog = true)
                }
            }

            is AgendaDetailsUserAction.ConfirmAttendeeEmail -> onInviteAttendee()

            AgendaDetailsUserAction.DismissAttendeeDialog -> {
                mutableViewStateFlow.update { viewState ->
                    viewState.copy(showAttendeeDialog = false)
                }
            }

            is AgendaDetailsUserAction.AttendeeEmailChange -> onAttendeeEmailChange(userAction.email)
            is AgendaDetailsUserAction.OnRemoveAttendee -> onRemoveAttendee(userAction.attendee)
            AgendaDetailsUserAction.SaveAgendaDetails -> onSaveAgendaItem()
            AgendaDetailsUserAction.ToEditMode -> {
                mutableViewStateFlow.update { viewState ->
                    viewState.copy(isEdit = true)
                }
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
        mutableViewStateFlow.update { viewState ->
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
        mutableViewStateFlow.update { viewState ->
            val eventTypeDetails = viewState.agendaTypeDetails.getAsEventType() ?: return@update viewState

            viewState.copy(
                showDatePickerDialog = true,
                dialogDateTimeData = DialogDateTimeData(
                    localDateTime = eventTypeDetails.toDateTime,
                    isFrom = false
                )
            )
        }
    }

    private fun onTimeFromChange() {
        mutableViewStateFlow.update { viewState ->
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
        mutableViewStateFlow.update { viewState ->
            val eventTypeDetails = viewState.agendaTypeDetails.getAsEventType() ?: return@update viewState

            viewState.copy(
                showTimePickerDialog = true,
                dialogDateTimeData = DialogDateTimeData(
                    localDateTime = eventTypeDetails.toDateTime,
                    isFrom = false
                )
            )
        }
    }

    private fun onDismissDialog(
        showDatePickerDialog: Boolean = viewStateFlow.value.showDatePickerDialog,
        showTimePickerDialog: Boolean = viewStateFlow.value.showTimePickerDialog
    ) {
        mutableViewStateFlow.update { viewState ->
            viewState.copy(
                showTimePickerDialog = showTimePickerDialog,
                showDatePickerDialog = showDatePickerDialog
            )
        }
    }

    private fun onNewDateSelected(dialogDateTimeData: DialogDateTimeData?) {
        dialogDateTimeData ?: return

        mutableViewStateFlow.update { viewState ->
            val eventTypeDetails = viewState.agendaTypeDetails.getAsEventType() ?: return@update viewState

            val (fromDateTime, toDateTime) = if (dialogDateTimeData.isFrom) {
                val newFromDate = viewState.currentFromDateTime.changeOnlyDate(dialogDateTimeData.localDateTime)
                newFromDate to newFromDate.adoptToDate(eventTypeDetails.toDateTime)
            } else {
                val newToDateTime = eventTypeDetails.toDateTime.changeOnlyDate(dialogDateTimeData.localDateTime)
                newToDateTime.adoptFromDate(viewState.currentFromDateTime) to newToDateTime
            }

            viewState.copy(
                showDatePickerDialog = false,
                currentFromDateTime = fromDateTime,
                agendaTypeDetails = eventTypeDetails.copy(
                    toDateTime = toDateTime,
                    formattedToDate = toDateTime.formatDate(),
                    formattedToTime = toDateTime.formatTime()
                ),
                formattedFromDate = fromDateTime.formatDate(),
                formattedFromTime = fromDateTime.formatTime()
            )
        }
    }

    private fun onNewTimeSelected(selectedHour: Int, selectedMinutes: Int, isFrom: Boolean?) {
        mutableViewStateFlow.update { viewState ->
            val eventTypeDetails = viewState.agendaTypeDetails.getAsEventType() ?: return@update viewState

            val (fromDateTime, toDateTime) = if (isFrom == true) {
                val newFromDate = viewState.currentFromDateTime.changeOnlyTime(selectedHour, selectedMinutes)
                newFromDate to newFromDate.adoptToDate(eventTypeDetails.toDateTime)
            } else {
                val newToDateTime = eventTypeDetails.toDateTime.changeOnlyTime(selectedHour, selectedMinutes)
                newToDateTime.adoptFromDate(viewState.currentFromDateTime) to newToDateTime
            }

            viewState.copy(
                showTimePickerDialog = false,
                currentFromDateTime = fromDateTime,
                agendaTypeDetails = eventTypeDetails.copy(
                    toDateTime = toDateTime,
                    formattedToDate = toDateTime.formatDate(),
                    formattedToTime = toDateTime.formatTime()
                ),
                formattedFromDate = fromDateTime.formatDate(),
                formattedFromTime = fromDateTime.formatTime()
            )
        }
    }

    private fun onAttendeeEmailChange(text: String) {
        mutableViewStateFlow.update { viewState ->
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
            mutableViewStateFlow.update { viewState ->
                viewState.copy(isAttendeeEmailError = true)
            }
            return
        }

        viewModelScope.launch {
            agendaRepository.getAttendee(email).onError {
                mutableViewStateFlow.update { viewState ->
                    viewState.copy(
                        isAttendeeEmailError = true
                    )
                }
            }.onSuccess { data ->
                mutableViewStateFlow.update { viewState ->
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
        mutableViewStateFlow.update { viewState ->
            viewState.copy(
                attendees = viewState.attendees.filter { attendee.userId != it.userId }
            )
        }
    }

    private fun onSaveAgendaItem() {
        if (agendaId.isNotEmpty()) {
            // TODO patch
            return
        }
        when (agendaType) {
            AgendaType.EVENT -> onSaveEvent()
            AgendaType.TASK -> onSaveTask()
            AgendaType.REMINDER -> TODO()
        }
    }

    private fun onSaveEvent() {
        viewModelScope.launch {
            val currentViewState = viewStateFlow.value
            val eventTypeDetails = currentViewState.agendaTypeDetails.getAsEventType() ?: return@launch

            agendaRepository.createEvent(
                Event(
                    eventId = AgendaUtils.generateAgendaId(),
                    title = currentViewState.title.getFromDynamicStringOrNull() ?: "",
                    description = currentViewState.description.getFromDynamicStringOrNull() ?: "",
                    fromTime = currentViewState.currentFromDateTime,
                    toTime = eventTypeDetails.toDateTime,
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

    private fun onSaveTask() {
        viewModelScope.launch {
            val currentViewState = viewStateFlow.value

            agendaRepository.createTask(
                Task(
                    taskId = AgendaUtils.generateAgendaId(),
                    title = currentViewState.title.getFromDynamicStringOrNull() ?: "",
                    description = currentViewState.description.getFromDynamicStringOrNull() ?: "",
                    time = currentViewState.currentFromDateTime,
                    reminder = currentViewState.reminderValue,
                    isDone = false
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

    private fun setGeneralDefaultAgendaValues() {
        val fromDateTime = DateTimeUtils.toLocalDateTime(viewStateFlow.value.currentFromDateTime.getMillis())
        mutableViewStateFlow.update { viewState ->
            viewState.copy(
                currentFromDateTime = fromDateTime,
                formattedFromDate = fromDateTime.formatDate(),
                formattedFromTime = fromDateTime.formatTime(),
                isEdit = isEditMode
            )
        }
    }

    companion object {
        const val CREATE_AGENDA_ID = "create_agenda_id"
        const val CREATE_AGENDA_IS_EDIT = "create_agenda_is_edit"
        const val CREATE_AGENDA_TYPE = "create_agenda_type"
    }
}
