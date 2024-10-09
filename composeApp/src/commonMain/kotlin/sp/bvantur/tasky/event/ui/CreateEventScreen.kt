package sp.bvantur.tasky.event.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import sp.bvantur.tasky.core.ui.components.TaskyContentSurface
import sp.bvantur.tasky.core.ui.theme.eventChoreType
import sp.bvantur.tasky.core.ui.utils.CollectSingleEventsWithLifecycle
import sp.bvantur.tasky.event.presentation.CreateEventSingleEvent
import sp.bvantur.tasky.event.presentation.CreateEventUserAction
import sp.bvantur.tasky.event.presentation.CreateEventViewModel
import sp.bvantur.tasky.event.presentation.CreateEventViewState
import sp.bvantur.tasky.event.presentation.models.CreateEventUpdatesModel
import sp.bvantur.tasky.event.presentation.models.SingleInputModel
import sp.bvantur.tasky.event.presentation.utils.DateTimeUtils
import sp.bvantur.tasky.event.presentation.utils.extensions.getMillis
import sp.bvantur.tasky.event.ui.components.TaskyAddImagesSection
import sp.bvantur.tasky.event.ui.components.TaskyConfirmTextButton
import sp.bvantur.tasky.event.ui.components.TaskyEventDescription
import sp.bvantur.tasky.event.ui.components.TaskyEventDivider
import sp.bvantur.tasky.event.ui.components.TaskyEventTitle
import sp.bvantur.tasky.event.ui.components.TaskyEventType
import sp.bvantur.tasky.event.ui.components.TaskyReminderPicker
import sp.bvantur.tasky.event.ui.components.TaskyTimeDatePicker
import sp.bvantur.tasky.event.ui.components.TaskyTimePickerDialog
import sp.bvantur.tasky.event.ui.components.TaskyVisitorsSection
import tasky.composeapp.generated.resources.Res
import tasky.composeapp.generated.resources.event
import tasky.composeapp.generated.resources.from_word
import tasky.composeapp.generated.resources.password_visibility_icon
import tasky.composeapp.generated.resources.select
import tasky.composeapp.generated.resources.to_word

@Composable
fun CreateEventRoute(
    eventModel: CreateEventUpdatesModel,
    onNavigateBack: () -> Unit,
    onOpenSingleInputScreen: (SingleInputModel) -> Unit
) {
    val viewModel = koinViewModel<CreateEventViewModel>()

    val viewState: CreateEventViewState by viewModel.viewStateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onUpdateTextFields(eventModel)
    }

    CollectSingleEventsWithLifecycle(singleEventFlow = viewModel.singleEventFlow) { singleEvent ->
        when (singleEvent) {
            is CreateEventSingleEvent.OnOpenSingleInput -> onOpenSingleInputScreen(singleEvent.data)
        }
    }

    CreateEventScreen(
        viewState = viewState,
        onNavigateBack = onNavigateBack,
        onUserAction = viewModel::onUserAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventScreen(
    viewState: CreateEventViewState,
    onNavigateBack: () -> Unit,
    onUserAction: (CreateEventUserAction) -> Unit
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
                    contentDescription = stringResource(Res.string.password_visibility_icon),
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

            IconButton(onClick = {
                // TODO
            }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(Res.string.password_visibility_icon),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
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

                TaskyEventTitle(
                    modifier = Modifier.padding(start = 16.dp),
                    text = viewState.title.asString(),
                    onClick = {
                        onUserAction(CreateEventUserAction.TitleChange)
                    }
                )

                TaskyEventDivider()

                TaskyEventDescription(
                    modifier = Modifier.padding(start = 16.dp),
                    text = viewState.description.asString(),
                    onClick = {
                        onUserAction(CreateEventUserAction.DescriptionChange)
                    }
                )

                TaskyAddImagesSection()

                TaskyEventDivider(modifier = Modifier.padding(top = 30.dp))

                TaskyTimeDatePicker(
                    propertyName = stringResource(Res.string.from_word),
                    formattedDate = viewState.formattedFromDate,
                    formattedTime = viewState.formattedFromTime,
                    onTimeChangeAction = {
                        onUserAction(CreateEventUserAction.TimeFromChange)
                    },
                    onDateChangeAction = {
                        onUserAction(CreateEventUserAction.DateFromChange)
                    }
                )
                TaskyEventDivider()
                TaskyTimeDatePicker(
                    propertyName = stringResource(Res.string.to_word),
                    formattedDate = viewState.formattedToDate,
                    formattedTime = viewState.formattedToTime,
                    onTimeChangeAction = {
                        onUserAction(CreateEventUserAction.TimeToChange)
                    },
                    onDateChangeAction = {
                        onUserAction(CreateEventUserAction.DateToChange)
                    }
                )
                TaskyEventDivider()
                TaskyReminderPicker(
                    modifier = Modifier.padding(start = 16.dp),
                    onClick = {
                        // TODO
                    }
                )
                TaskyEventDivider()

                TaskyVisitorsSection(modifier = Modifier.padding(start = 16.dp), onClick = {
                    // TODO
                })
            }
        }
    }

    if (viewState.showDatePickerDialog) {
        val datePickerState =
            rememberDatePickerState(
                initialSelectedDateMillis = viewState.dialogDateTimeData?.localDateTime?.getMillis()
            )

        DatePickerDialog(
            onDismissRequest = {
                onUserAction(CreateEventUserAction.DismissDateDialog)
            },
            confirmButton = {
                TaskyConfirmTextButton(
                    onClick = {
                        onUserAction(
                            CreateEventUserAction.SelectNewDate(
                                viewState.dialogDateTimeData?.copy(
                                    localDateTime = DateTimeUtils.toLocalDateTime(datePickerState.selectedDateMillis)
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
                onUserAction(CreateEventUserAction.DismissTimeDialog)
            },
            confirmButton = {
                TaskyConfirmTextButton(
                    onClick = {
                        onUserAction(
                            CreateEventUserAction.SelectNewTime(
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
}
