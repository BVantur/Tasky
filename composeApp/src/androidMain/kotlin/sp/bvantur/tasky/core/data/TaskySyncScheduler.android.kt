package sp.bvantur.tasky.core.data

import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import sp.bvantur.tasky.core.data.local.EventEntity
import sp.bvantur.tasky.core.data.local.SyncStep
import sp.bvantur.tasky.core.data.utils.setExponentialBackOff
import sp.bvantur.tasky.core.data.utils.setInputParameters
import sp.bvantur.tasky.core.data.utils.setRequireNetworkConnectivity

@Suppress("MagicNumber")
actual class TaskySyncScheduler(
    private val workManager: WorkManager,
    private val applicationCoroutineScope: CoroutineScope
) {
    actual suspend fun scheduleAgendaSync(events: List<EventEntity>) {
        events.forEach { event ->
            val agendaWorker: OneTimeWorkRequest = when (event.syncStep) {
                SyncStep.CREATE -> {
                    onCreateEventWorker(event)
                }

                // TODO handle also create and then automatic edit when device is in
                // offline state for both of those actions
                SyncStep.EDIT -> {
                    return@forEach // TODO
                }

                SyncStep.DELETE -> {
                    onDeleteEventWorker(event)
                }

                else -> return@forEach
            }

            applicationCoroutineScope.launch {
                workManager.beginUniqueWork(
                    event.id,
                    ExistingWorkPolicy.REPLACE,
                    agendaWorker
                ).enqueue()
            }
        }
    }

    private fun onCreateEventWorker(event: EventEntity): OneTimeWorkRequest =
        OneTimeWorkRequestBuilder<SyncCreateEventWorker>()
            .setRequireNetworkConnectivity()
            .setExponentialBackOff(5000)
            .setInputParameters {
                putString(SyncCreateEventWorker.SYNC_EVENT_ID, event.id)
            }
            .addTag("add_event")
            .build()

    private fun onDeleteEventWorker(event: EventEntity): OneTimeWorkRequest =
        OneTimeWorkRequestBuilder<SyncDeleteEventWorker>()
            .setRequireNetworkConnectivity()
            .setExponentialBackOff(5000)
            .setInputParameters {
                putString(SyncDeleteEventWorker.SYNC_EVENT_ID, event.id)
            }
            .addTag("delete_event")
            .build()
}
