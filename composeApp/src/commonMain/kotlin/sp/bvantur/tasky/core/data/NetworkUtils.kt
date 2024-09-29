package sp.bvantur.tasky.core.data

import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.io.IOException
import kotlin.coroutines.cancellation.CancellationException

@Suppress("TooGenericExceptionCaught")
suspend inline fun <reified T> safeApiCall(apiCall: () -> HttpResponse): Result<T> = try {
    val response = apiCall()
    if (response.status.isSuccess()) {
        val responseBody = response.body<T>()
        Result.success(responseBody)
    } else {
        Result.failure(Exception("Error: ${response.status}"))
    }
} catch (e: ClientRequestException) {
    Result.failure(Exception("Client request error: ${e.response.status}", e))
} catch (e: ServerResponseException) {
    Result.failure(Exception("Server error: ${e.response.status}", e))
} catch (e: IOException) {
    Result.failure(Exception("Network error", e))
} catch (e: TimeoutCancellationException) {
    Result.failure(Exception("Request timeout", e))
} catch (e: CancellationException) {
    throw e
} catch (e: Exception) {
    Result.failure(e)
}
