package sp.bvantur.tasky.core.data

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

actual class TaskySyncScheduler(
    private val workManager: WorkManager,
    private val applicationCoroutineScope: CoroutineScope
) {
    actual suspend fun scheduleAgendaSync(eventIds: List<String>) {
        repeat(eventIds.size) { index ->
            val agendaWorker = OneTimeWorkRequestBuilder<SyncEventsWorker>()
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
                        .putString(SyncEventsWorker.SYNC_EVENT_ID, eventIds[index])
                        .build()
                )
                .build()

            applicationCoroutineScope.launch {
                workManager.enqueue(agendaWorker)
            }
        }
    }
}
