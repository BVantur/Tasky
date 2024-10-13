package sp.bvantur.tasky.core.data

import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.io.IOException
import sp.bvantur.tasky.core.domain.CommunicationError
import sp.bvantur.tasky.core.domain.TaskyResult
import kotlin.coroutines.cancellation.CancellationException

suspend inline fun <reified T> safeApiCall(execute: () -> HttpResponse): TaskyResult<T, CommunicationError> {
    // TODO revisit and handle more cases here
    return try {
        val response = execute()
        if (response.status.isSuccess()) {
            val responseBody = response.body<T>()
            TaskyResult.Success(responseBody)
        } else {
            TaskyResult.Error(CommunicationError.UnknownError())
        }
    } catch (e: ClientRequestException) {
        return TaskyResult.Error(CommunicationError.UnknownError(e.message))
    } catch (e: ServerResponseException) {
        return TaskyResult.Error(CommunicationError.UnknownError(e.message))
    } catch (e: IOException) {
        return TaskyResult.Error(CommunicationError.UnknownError(e.message))
    } catch (e: TimeoutCancellationException) {
        return TaskyResult.Error(CommunicationError.UnknownError(e.message))
    } catch (ignore: Exception) {
        if (ignore is CancellationException) throw ignore

        return TaskyResult.Error(CommunicationError.UnknownError(ignore.message))
    }
}
