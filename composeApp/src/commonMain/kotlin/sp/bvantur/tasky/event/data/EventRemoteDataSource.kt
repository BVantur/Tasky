package sp.bvantur.tasky.event.data

import io.ktor.client.HttpClient
import io.ktor.client.request.forms.FormPart
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.path
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import sp.bvantur.tasky.core.data.safeApiCall
import sp.bvantur.tasky.core.domain.CommunicationError
import sp.bvantur.tasky.core.domain.TaskyResult
import sp.bvantur.tasky.event.data.model.AttendeeResponse
import sp.bvantur.tasky.event.data.model.CreateEventRequest

class EventRemoteDataSource(private val httpClient: HttpClient, private val json: Json) {

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

    suspend fun createEvent(createEvent: CreateEventRequest): TaskyResult<Unit, CommunicationError> = safeApiCall {
        httpClient.request {
            url {
                method = HttpMethod.Post
                path("event")
                contentType(ContentType.Application.Json)
                setBody(
                    MultiPartFormDataContent(
                        formData(
                            FormPart(key = "create_event_request", value = json.encodeToString(createEvent))
                        )
                    )
                )
            }
        }
    }
}
