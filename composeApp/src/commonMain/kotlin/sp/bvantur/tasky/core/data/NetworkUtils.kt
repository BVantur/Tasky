package sp.bvantur.tasky.core.data

import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.TimeoutCancellationException
import sp.bvantur.tasky.core.domain.TaskyError
import sp.bvantur.tasky.core.domain.TaskyResult
import kotlin.coroutines.cancellation.CancellationException

suspend inline fun <reified T> safeApiCall(execute: () -> HttpResponse): TaskyResult<T, TaskyError> = try {
    val response = execute()

    if (response.status.isSuccess()) {
        val responseBody = response.body<T>()
        TaskyResult.Success(responseBody)
    } else {
        response.toResult()
    }
} catch (ignore: RedirectResponseException) {
    TaskyResult.Error(TaskyError.RedirectError)
} catch (ignore: ClientRequestException) {
    TaskyResult.Error(TaskyError.BadRequest)
} catch (ignore: ServerResponseException) {
    TaskyResult.Error(TaskyError.ServerError)
} catch (ignore: UnresolvedAddressException) {
    TaskyResult.Error(TaskyError.NoInternet)
} catch (ignore: TimeoutCancellationException) {
    TaskyResult.Error(TaskyError.RequestTimeout)
} catch (ignore: kotlinx.io.IOException) {
    TaskyResult.Error(TaskyError.NoInternet)
} catch (ignore: Exception) {
    if (ignore is CancellationException) throw ignore

    TaskyResult.Error(TaskyError.UnknownError(ignore.message))
}

inline fun <reified T> HttpResponse.toResult(): TaskyResult<T, TaskyError> = when (status.value) {
    HttpStatusCode.Unauthorized.value -> TaskyResult.Error(TaskyError.Unauthorized)
    HttpStatusCode.RequestTimeout.value -> TaskyResult.Error(TaskyError.RequestTimeout)
    HttpStatusCode.Conflict.value -> TaskyResult.Error(TaskyError.Conflict)
    in HttpStatusCode.InternalServerError.value..HttpStatusCode.InsufficientStorage.value -> TaskyResult.Error(
        TaskyError.ServerError
    )
    else -> TaskyResult.Error(TaskyError.UnknownError())
}
