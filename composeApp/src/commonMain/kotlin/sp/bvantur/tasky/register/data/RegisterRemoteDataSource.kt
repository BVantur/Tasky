package sp.bvantur.tasky.register.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.path
import sp.bvantur.tasky.core.data.safeApiCall

class RegisterRemoteDataSource(private val httpClient: HttpClient) {
    suspend fun register(registerUserData: RegisterUserDataRequest): Result<Unit> = safeApiCall {
        httpClient.request {
            url {
                method = HttpMethod.Post
                path("register")
                contentType(ContentType.Application.Json)
                setBody(registerUserData)
            }
        }.body()
    }
}
