package sp.bvantur.tasky.register.data

import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.path
import sp.bvantur.tasky.core.data.safeApiCall
import sp.bvantur.tasky.core.domain.TaskyError
import sp.bvantur.tasky.core.domain.TaskyResult

class RegisterRemoteDataSource(private val httpClient: HttpClient) {
    suspend fun register(registerUserData: RegisterUserDataRequest): TaskyResult<Unit, TaskyError> = safeApiCall {
        httpClient.request {
            url {
                method = HttpMethod.Post
                path("register")
                contentType(ContentType.Application.Json)
                setBody(registerUserData)
            }
        }
    }
}
