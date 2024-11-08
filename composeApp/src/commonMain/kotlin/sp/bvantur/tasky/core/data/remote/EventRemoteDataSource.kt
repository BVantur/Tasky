package sp.bvantur.tasky.core.data.remote

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
import sp.bvantur.tasky.agenda.data.remote.CheckAttendeeResponse
import sp.bvantur.tasky.agenda.data.remote.CreateEventRequest
import sp.bvantur.tasky.core.data.safeApiCall
import sp.bvantur.tasky.core.domain.TaskyError
import sp.bvantur.tasky.core.domain.TaskyResult

class EventRemoteDataSource(private val httpClient: HttpClient, private val json: Json) {

    suspend fun getAttendee(email: String): TaskyResult<CheckAttendeeResponse, TaskyError> = safeApiCall {
        httpClient.request {
            url {
                method = HttpMethod.Get
                path("attendee")
                contentType(ContentType.Application.Json)
                parameters.append("email", email)
            }
        }
    }

    suspend fun createEvent(createEvent: CreateEventRequest): TaskyResult<EventResponse, TaskyError> = safeApiCall {
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

    suspend fun deleteEventById(id: String): TaskyResult<Unit, TaskyError> = safeApiCall {
        httpClient.request {
            url {
                method = HttpMethod.Delete
                path("event")
                parameters.append("eventId", id)
            }
        }
    }
}
