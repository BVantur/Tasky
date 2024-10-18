package sp.bvantur.tasky.home.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.path
import sp.bvantur.tasky.core.data.safeApiCall
import sp.bvantur.tasky.core.domain.CommunicationError
import sp.bvantur.tasky.core.domain.TaskyResult

class HomeRemoteDataSource(private val httpClient: HttpClient) {
    suspend fun getTodayAgenda(time: Long): TaskyResult<AgendaResponse, CommunicationError> = safeApiCall {
        httpClient.request {
            url {
                method = HttpMethod.Get
                path("agenda")
                contentType(ContentType.Application.Json)
                parameters.append("time", time.toString())
            }
        }
    }
}
