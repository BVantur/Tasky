package sp.bvantur.tasky.event.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import sp.bvantur.tasky.core.ui.theme.appBarAction
import sp.bvantur.tasky.core.ui.utils.CollectSingleEventsWithLifecycle
import sp.bvantur.tasky.event.presentation.SingleInputSingleEvent
import sp.bvantur.tasky.event.presentation.SingleInputViewModel
import sp.bvantur.tasky.event.presentation.SingleInputViewState
import sp.bvantur.tasky.event.presentation.models.SingleInputModel
import tasky.composeapp.generated.resources.Res
import tasky.composeapp.generated.resources.save

@Composable
fun SingleInputRoute(
    singleInputModel: SingleInputModel?,
    onNavigateBack: () -> Unit,
    onSaveAction: (String, Boolean) -> Unit
) {
    val viewModel = koinViewModel<SingleInputViewModel>(
        parameters = { parametersOf(singleInputModel) }
    )

    val viewState: SingleInputViewState by viewModel.viewStateFlow.collectAsStateWithLifecycle()

    CollectSingleEventsWithLifecycle(singleEventFlow = viewModel.singleEventFlow) { singleEvent ->
        when (singleEvent) {
            is SingleInputSingleEvent.SaveTitle -> onSaveAction(singleEvent.value, true)
            is SingleInputSingleEvent.SaveDescription -> onSaveAction(singleEvent.value, false)
        }
    }

    SingleInputScreen(
        viewState = viewState,
        onNavigateBack = onNavigateBack,
        onInput = viewModel::onInputAction,
        onSaveAction = viewModel::onSaveAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleInputScreen(
    viewState: SingleInputViewState,
    onNavigateBack: () -> Unit,
    onInput: (String) -> Unit,
    onSaveAction: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = viewState.title.asString(),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    Icon(
                        modifier = Modifier.padding(start = 16.dp).clickable {
                            onNavigateBack()
                        },
                        imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                actions = {
                    Button(
                        enabled = viewState.isSaveButtonEnabled,
                        onClick = onSaveAction,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.appBarAction,
                            disabledContainerColor = Color.Transparent,
                            disabledContentColor = MaterialTheme.colorScheme.onTertiary
                        )
                    ) {
                        Text(stringResource(Res.string.save), style = MaterialTheme.typography.labelMedium)
                    }
                }
            )
        }
    ) { paddingValues ->
        TextField(
            value = viewState.value,
            onValueChange = { onInput(it) },
            placeholder = {
                Text(viewState.placeholder.asString())
            },
            singleLine = true,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth(),
            textStyle = if (viewState.isTitle) {
                MaterialTheme.typography.displayMedium
            } else {
                MaterialTheme.typography.labelMedium
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedPlaceholderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
            ),
        )
    }
}
