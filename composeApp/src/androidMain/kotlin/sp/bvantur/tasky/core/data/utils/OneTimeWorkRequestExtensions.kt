package sp.bvantur.tasky.core.data.utils

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import java.util.concurrent.TimeUnit

fun OneTimeWorkRequest.Builder.setRequireNetworkConnectivity(): OneTimeWorkRequest.Builder = this.setConstraints(
    Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()
)

fun OneTimeWorkRequest.Builder.setExponentialBackOff(duration: Long): OneTimeWorkRequest.Builder =
    this.setBackoffCriteria(
        backoffPolicy = BackoffPolicy.EXPONENTIAL,
        backoffDelay = duration,
        timeUnit = TimeUnit.MILLISECONDS
    )

fun OneTimeWorkRequest.Builder.setInputParameters(block: Data.Builder.() -> Unit): OneTimeWorkRequest.Builder =
    this.setInputData(
        Data.Builder()
            .apply(block)
            .build()
    )
