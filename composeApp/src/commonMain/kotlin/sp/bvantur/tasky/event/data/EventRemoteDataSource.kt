package sp.bvantur.tasky.event.data

import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.path
import sp.bvantur.tasky.core.data.safeApiCall
import sp.bvantur.tasky.core.domain.CommunicationError
import sp.bvantur.tasky.core.domain.TaskyResult
import sp.bvantur.tasky.event.data.model.AttendeeResponse

class EventRemoteDataSource(private val httpClient: HttpClient) {

    suspend fun getAttendee(email: String): TaskyResult<AttendeeResponse, CommunicationError> = safeApiCall {
        httpClient.request {
            url {
                method = HttpMethod.Get
                path("attendee")
                contentType(ContentType.Application.Json)
                parameters.append("email", email)
            }
        }
    }
}
