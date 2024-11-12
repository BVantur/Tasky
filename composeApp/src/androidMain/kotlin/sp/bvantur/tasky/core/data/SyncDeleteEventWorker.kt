package sp.bvantur.tasky.core.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import org.koin.java.KoinJavaComponent.inject
import sp.bvantur.tasky.core.data.remote.EventRemoteDataSource
import sp.bvantur.tasky.core.domain.TaskyError
import sp.bvantur.tasky.core.domain.TaskyResult

class SyncDeleteEventWorker(context: Context, private val params: WorkerParameters) : CoroutineWorker(context, params) {

    private val database: TaskyDatabase by inject(TaskyDatabase::class.java)
    private val eventRemoteDataSource: EventRemoteDataSource by inject(EventRemoteDataSource::class.java)
    override suspend fun doWork(): Result {
        val eventId = params.inputData.getString(SYNC_EVENT_ID) ?: return Result.failure()

        return when (val response = eventRemoteDataSource.deleteEventById(eventId)) {
            is TaskyResult.Error -> response.toWorkerError()
            is TaskyResult.Success -> Result.success().also {
                database.getEventDao().removeById(eventId)
            }
        }
    }

    private fun <E : TaskyError> TaskyResult.Error<E>.toWorkerError(): Result = if (this.error.isSyncError()) {
        Result.retry()
    } else {
        Result.failure()
    }

    companion object {
        const val SYNC_EVENT_ID = "sync_event_id"
    }
}
