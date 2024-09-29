package sp.bvantur.tasky.core.presentation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext
import sp.bvantur.tasky.core.DispatcherProvider

internal interface SingleEventHandler<Event : SingleEvent> {
    val singleEventFlow: Flow<Event>

    suspend fun emitSingleEvent(singleEvent: Event)
}

internal class SingleEventHandlerImpl<Event : SingleEvent>(
    private val dispatcherProvider: DispatcherProvider
) : SingleEventHandler<Event> {
    private val mutableSingleEventChannel = Channel<Event>(capacity = Channel.BUFFERED)
    override val singleEventFlow = mutableSingleEventChannel.receiveAsFlow()

    override suspend fun emitSingleEvent(singleEvent: Event) {
        withContext(dispatcherProvider.main.immediate) {
            mutableSingleEventChannel.send(singleEvent)
        }
    }
}
