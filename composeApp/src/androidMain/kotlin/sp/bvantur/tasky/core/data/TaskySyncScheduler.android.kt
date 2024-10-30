package sp.bvantur.tasky.core.data

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import sp.bvantur.tasky.core.data.local.EventEntity
import sp.bvantur.tasky.core.data.local.SyncStep
import java.util.concurrent.TimeUnit

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
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .setBackoffCriteria(
                backoffPolicy = BackoffPolicy.EXPONENTIAL,
                backoffDelay = 5000,
                timeUnit = TimeUnit.MILLISECONDS
            )
            .addTag("add_event")
            .setInputData(
                Data.Builder()
                    .putString(SyncCreateEventWorker.SYNC_EVENT_ID, event.id)
                    .build()
            )
            .build()

    private fun onDeleteEventWorker(event: EventEntity): OneTimeWorkRequest =
        OneTimeWorkRequestBuilder<SyncDeleteEventWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .setBackoffCriteria(
                backoffPolicy = BackoffPolicy.EXPONENTIAL,
                backoffDelay = 5000,
                timeUnit = TimeUnit.MILLISECONDS
            )
            .addTag("delete_event")
            .setInputData(
                Data.Builder()
                    .putString(SyncCreateEventWorker.SYNC_EVENT_ID, event.id)
                    .build()
            )
            .build()
}
