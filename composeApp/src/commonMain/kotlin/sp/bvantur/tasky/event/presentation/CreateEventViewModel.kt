package sp.bvantur.tasky.event.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sp.bvantur.tasky.core.domain.DispatcherProvider
import sp.bvantur.tasky.core.presentation.SingleEventHandler
import sp.bvantur.tasky.core.presentation.SingleEventHandlerImpl
import sp.bvantur.tasky.core.presentation.TextData
import sp.bvantur.tasky.core.presentation.ViewModelUserActionHandler
import sp.bvantur.tasky.core.presentation.ViewStateViewModel
import sp.bvantur.tasky.event.presentation.models.CreateEventUpdatesModel
import sp.bvantur.tasky.event.presentation.models.InputType
import sp.bvantur.tasky.event.presentation.models.SingleInputModel
import sp.bvantur.tasky.event.presentation.utils.DateTimeUtils
import sp.bvantur.tasky.event.presentation.utils.extensions.adoptFromDate
import sp.bvantur.tasky.event.presentation.utils.extensions.adoptToDate
import sp.bvantur.tasky.event.presentation.utils.extensions.changeOnlyDate
import sp.bvantur.tasky.event.presentation.utils.extensions.changeOnlyTime
import sp.bvantur.tasky.event.presentation.utils.extensions.formatDate
import sp.bvantur.tasky.event.presentation.utils.extensions.formatTime
import sp.bvantur.tasky.event.presentation.utils.extensions.getMillis

class CreateEventViewModel(dispatcherProvider: DispatcherProvider, savedStateHandle: SavedStateHandle) :
    ViewStateViewModel<CreateEventViewState>(CreateEventViewState()),
    ViewModelUserActionHandler<CreateEventUserAction>,
    SingleEventHandler<CreateEventSingleEvent> by SingleEventHandlerImpl(dispatcherProvider) {

    init {
        val toTimestamp = savedStateHandle.get<Long?>(CREATE_EVENT_TO_TIMESTAMP_EXTRA)
        val fromTimestamp = savedStateHandle.get<Long?>(CREATE_EVENT_FROM_TIMESTAMP_EXTRA)

        val fromDateTime = DateTimeUtils.toLocalDateTime(
            fromTimestamp ?: viewStateFlow.value.currentFromDateTime?.getMillis()
        )
        val toDateTime =
            DateTimeUtils.toLocalDateTime(toTimestamp ?: viewStateFlow.value.currentToDateTime?.getMillis())

        emitViewState { viewState ->
            viewState.copy(
                currentFromDateTime = fromDateTime,
                formattedFromDate = fromDateTime.formatDate(),
                formattedFromTime = fromDateTime.formatTime(),
                currentToDateTime = toDateTime,
                formattedToDate = toDateTime.formatDate(),
                formattedToTime = toDateTime.formatTime(),
            )
        }
    }

    fun onUpdateTextFields(eventData: CreateEventUpdatesModel) {
        val title = eventData.title?.let {
            TextData.DynamicString(it)
        }

        val description = eventData.description?.let {
            TextData.DynamicString(it)
        }

        if (title == null && description == null) return

        viewModelScope.launch {
            emitViewState { viewState ->
                viewState.copy(
                    title = title ?: viewStateFlow.value.title,
                    description = description ?: viewStateFlow.value.description,
                )
            }
        }
    }

    override fun onUserAction(userAction: CreateEventUserAction) {
        when (userAction) {
            CreateEventUserAction.TitleChange -> onTitleChange()
            CreateEventUserAction.DescriptionChange -> onDescriptionChange()
            CreateEventUserAction.DateFromChange -> onDateFromChange()
            CreateEventUserAction.DateToChange -> onDateToChange()
            CreateEventUserAction.DismissDateDialog -> onDismissDialog(showDatePickerDialog = false)
            CreateEventUserAction.DismissTimeDialog -> onDismissDialog(showTimePickerDialog = false)
            CreateEventUserAction.TimeFromChange -> onTimeFromChange()
            CreateEventUserAction.TimeToChange -> onTimeToChange()
            is CreateEventUserAction.SelectNewDate -> onNewDateSelected(userAction.dialogDateTimeData)
            is CreateEventUserAction.SelectNewTime -> onNewTimeSelected(
                userAction.selectedHour,
                userAction.selectedMinutes,
                userAction.isFrom
            )

            is CreateEventUserAction.SelectNewReminder -> {
                emitViewState { viewState ->
                    viewState.copy(reminderValue = userAction.reminderValue)
                }
            }
        }
    }

    private fun onTitleChange() {
        viewModelScope.launch {
            emitSingleEvent(
                CreateEventSingleEvent.OnOpenSingleInput(
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
                CreateEventSingleEvent.OnOpenSingleInput(
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
                formattedFromDate = fromDateTime?.formatDate() ?: "",
                formattedFromTime = fromDateTime?.formatTime() ?: "",
                formattedToDate = toDateTime?.formatDate() ?: "",
                formattedToTime = toDateTime?.formatTime() ?: ""
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
                formattedFromDate = fromDateTime?.formatDate() ?: "",
                formattedFromTime = fromDateTime?.formatTime() ?: "",
                formattedToDate = toDateTime?.formatDate() ?: "",
                formattedToTime = toDateTime?.formatTime() ?: ""
            )
        }
    }

    companion object {
        const val CREATE_EVENT_FROM_TIMESTAMP_EXTRA = "create_event_from_timestamp_extra"
        const val CREATE_EVENT_TO_TIMESTAMP_EXTRA = "create_event_to_timestamp_extra"
    }
}
