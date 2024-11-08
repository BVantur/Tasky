package sp.bvantur.tasky.event.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import sp.bvantur.tasky.core.domain.extensions.getMillis
import sp.bvantur.tasky.core.ui.components.TaskyConfirmationButton
import sp.bvantur.tasky.core.ui.components.TaskyContentSurface
import sp.bvantur.tasky.core.ui.components.TaskyUserDataTextField
import sp.bvantur.tasky.core.ui.theme.eventChoreType
import sp.bvantur.tasky.core.ui.utils.CollectSingleEventsWithLifecycle
import sp.bvantur.tasky.event.presentation.CreateEventViewState
import sp.bvantur.tasky.event.presentation.EventDetailsSingleEvent
import sp.bvantur.tasky.event.presentation.EventDetailsUserAction
import sp.bvantur.tasky.event.presentation.EventDetailsViewModel
import sp.bvantur.tasky.event.presentation.models.CreateEventUpdatesModel
import sp.bvantur.tasky.event.presentation.models.SingleInputModel
import sp.bvantur.tasky.event.presentation.utils.DateTimeUtils
import sp.bvantur.tasky.event.ui.components.TaskyAddImagesSection
import sp.bvantur.tasky.event.ui.components.TaskyAgendaDescriptionRow
import sp.bvantur.tasky.event.ui.components.TaskyAgendaTitleRow
import sp.bvantur.tasky.event.ui.components.TaskyConfirmTextButton
import sp.bvantur.tasky.event.ui.components.TaskyEventDivider
import sp.bvantur.tasky.event.ui.components.TaskyEventType
import sp.bvantur.tasky.event.ui.components.TaskyReminderPicker
import sp.bvantur.tasky.event.ui.components.TaskyTimeDatePicker
import sp.bvantur.tasky.event.ui.components.TaskyTimePickerDialog
import sp.bvantur.tasky.event.ui.components.TaskyVisitorsSection
import tasky.composeapp.generated.resources.Res
import tasky.composeapp.generated.resources.add
import tasky.composeapp.generated.resources.add_visitor
import tasky.composeapp.generated.resources.close
import tasky.composeapp.generated.resources.email_address
import tasky.composeapp.generated.resources.event
import tasky.composeapp.generated.resources.from_word
import tasky.composeapp.generated.resources.save
import tasky.composeapp.generated.resources.select
import tasky.composeapp.generated.resources.to_word

@Composable
fun EventDetailsRoute(
    eventModel: CreateEventUpdatesModel,
    eventId: String?,
    isEdit: Boolean = false,
    onNavigateBack: () -> Unit,
    onOpenSingleInputScreen: (SingleInputModel) -> Unit
) {
    val viewModel = koinViewModel<EventDetailsViewModel>(parameters = {
        parametersOf(eventId, isEdit)
    })

    val viewState: CreateEventViewState by viewModel.viewStateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onUpdateTextFields(eventModel)
    }

    CollectSingleEventsWithLifecycle(singleEventFlow = viewModel.singleEventFlow) { singleEvent ->
        when (singleEvent) {
            is EventDetailsSingleEvent.OnOpenDetailsSingleInput -> onOpenSingleInputScreen(singleEvent.data)
            EventDetailsSingleEvent.CloseScreen -> onNavigateBack()
        }
    }

    EventDetailsScreen(
        viewState = viewState,
        onNavigateBack = onNavigateBack,
        onUserAction = viewModel::onUserAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsScreen(
    viewState: CreateEventViewState,
    onNavigateBack: () -> Unit,
    onUserAction: (EventDetailsUserAction) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            Text(
                modifier = Modifier.weight(1f).padding(vertical = 20.dp),
                text = "04. OCTOBER 2024".uppercase(), // TODO fix this text
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            if (viewState.isEdit) {
                TaskyConfirmTextButton(
                    onClick = {
                        onUserAction(EventDetailsUserAction.SaveEventDetails)
                    },
                    enabled = viewState.isSaveEnabled,
                    text = stringResource(Res.string.save),
                    contentColor = MaterialTheme.colorScheme.surface,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                IconButton(onClick = {
                    onUserAction(EventDetailsUserAction.ToEditMode)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }

        TaskyContentSurface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(top = 30.dp)) {
                TaskyEventType(
                    modifier = Modifier.padding(start = 16.dp),
                    text = stringResource(Res.string.event),
                    choreColor = MaterialTheme.colorScheme.eventChoreType
                )

                TaskyAgendaTitleRow(
                    modifier = Modifier.fillMaxWidth().padding(start = 16.dp),
                    text = viewState.title.asString(),
                    isEnabled = viewState.isEdit,
                    onClick = {
                        onUserAction(EventDetailsUserAction.TitleChange)
                    }
                )

                TaskyEventDivider()

                TaskyAgendaDescriptionRow(
                    modifier = Modifier.padding(start = 16.dp),
                    text = viewState.description.asString(),
                    isEnabled = viewState.isEdit,
                    onClick = {
                        onUserAction(EventDetailsUserAction.DescriptionChange)
                    }
                )

                TaskyAddImagesSection()

                TaskyEventDivider(modifier = Modifier.padding(top = 30.dp))

                TaskyTimeDatePicker(
                    propertyName = stringResource(Res.string.from_word),
                    formattedDate = viewState.formattedFromDate,
                    formattedTime = viewState.formattedFromTime,
                    isEnabled = viewState.isEdit,
                    onTimeChangeAction = {
                        onUserAction(EventDetailsUserAction.TimeFromChange)
                    },
                    onDateChangeAction = {
                        onUserAction(EventDetailsUserAction.DateFromChange)
                    }
                )
                TaskyEventDivider()
                TaskyTimeDatePicker(
                    propertyName = stringResource(Res.string.to_word),
                    formattedDate = viewState.formattedToDate,
                    formattedTime = viewState.formattedToTime,
                    isEnabled = viewState.isEdit,
                    onTimeChangeAction = {
                        onUserAction(EventDetailsUserAction.TimeToChange)
                    },
                    onDateChangeAction = {
                        onUserAction(EventDetailsUserAction.DateToChange)
                    }
                )
                TaskyEventDivider()
                TaskyReminderPicker(
                    modifier = Modifier.padding(start = 16.dp),
                    selectedReminderValue = viewState.reminderValue,
                    isEnabled = viewState.isEdit,
                    onReminderValueSelected = {
                        onUserAction(EventDetailsUserAction.SelectNewReminder(it))
                    }
                )
                TaskyEventDivider()

                TaskyVisitorsSection(
                    attendees = viewState.attendees,
                    isEnabled = viewState.isEdit,
                    onClick = {
                        onUserAction(EventDetailsUserAction.InviteNewAttendee)
                    },
                    onDeleteAttendee = {
                        onUserAction(EventDetailsUserAction.OnRemoveAttendee(it))
                    }
                )
            }
        }
    }

    if (viewState.showDatePickerDialog || viewState.showTimePickerDialog || viewState.showAttendeeDialog) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(horizontal = 0.dp)
        )
    }

    if (viewState.showDatePickerDialog) {
        val datePickerState =
            rememberDatePickerState(
                initialSelectedDateMillis = viewState.dialogDateTimeData?.localDateTime?.getMillis()
            )

        DatePickerDialog(
            onDismissRequest = {
                onUserAction(EventDetailsUserAction.DismissDateDialog)
            },
            confirmButton = {
                TaskyConfirmTextButton(
                    onClick = {
                        onUserAction(
                            EventDetailsUserAction.SelectNewDate(
                                viewState.dialogDateTimeData?.copy(
                                    localDateTime = DateTimeUtils.toLocalDateTime(
                                        datePickerState.selectedDateMillis
                                            ?: DateTimeUtils.getCurrentLocalDateTimeInMillis()
                                    )
                                )
                            )
                        )
                    },
                    text = stringResource(Res.string.select)
                )
            }
        ) {
            DatePicker(datePickerState)
        }
    }

    if (viewState.showTimePickerDialog) {
        val timePickerState =
            rememberTimePickerState(
                initialHour = viewState.dialogDateTimeData?.localDateTime?.hour ?: 0,
                initialMinute = viewState.dialogDateTimeData?.localDateTime?.minute ?: 0,
                is24Hour = true
            )
        TaskyTimePickerDialog(
            onDismissRequest = {
                onUserAction(EventDetailsUserAction.DismissTimeDialog)
            },
            confirmButton = {
                TaskyConfirmTextButton(
                    onClick = {
                        onUserAction(
                            EventDetailsUserAction.SelectNewTime(
                                timePickerState.hour,
                                timePickerState.minute,
                                viewState.dialogDateTimeData?.isFrom
                            )
                        )
                    },
                    text = stringResource(Res.string.select)
                )
            }
        ) {
            TimePicker(state = timePickerState)
        }
    }

    if (viewState.showAttendeeDialog) {
        TaskyAttendeeDialog(
            onDismiss = {
                onUserAction(EventDetailsUserAction.DismissAttendeeDialog)
            },
            onConfirm = {
                onUserAction(EventDetailsUserAction.ConfirmAttendeeEmail)
            },
            onTextChanged = {
                onUserAction(EventDetailsUserAction.AttendeeEmailChange(it))
            },
            isError = viewState.isAttendeeEmailError,
            inputValue = viewState.attendeeInputValue
        )
    }
}

@Composable
private fun TaskyAttendeeDialog(
    onDismiss: () -> Unit,
    onTextChanged: (String) -> Unit,
    onConfirm: () -> Unit,
    isError: Boolean,
    inputValue: String
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = true)
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(Res.string.close)
                    )
                }

                Text(
                    text = stringResource(Res.string.add_visitor),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                TaskyUserDataTextField(
                    value = inputValue,
                    onValueChange = { value ->
                        onTextChanged(value)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp)
                        .defaultMinSize(minWidth = 300.dp, minHeight = 60.dp),
                    placeholder = stringResource(Res.string.email_address),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    onKeyboardImeAction = {
                    },
                    isError = isError
                )

                TaskyConfirmationButton(
                    text = stringResource(Res.string.add),
                    onClick = {
                        onConfirm()
                    },
                    modifier = Modifier.fillMaxWidth()
                        .padding(bottom = 20.dp, top = 30.dp)
                        .defaultMinSize(minHeight = 56.dp)
                )
            }
        }
    }
}