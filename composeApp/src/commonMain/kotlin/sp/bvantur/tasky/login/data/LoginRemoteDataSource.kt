package sp.bvantur.tasky.login.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.path
import sp.bvantur.tasky.core.data.safeApiCall

class LoginRemoteDataSource(private val httpClient: HttpClient) {

    suspend fun login(data: LoginUserDataRequest): Result<LoginUserDataResponse> = safeApiCall {
        httpClient.request {
            url {
                method = HttpMethod.Post
                path("login")
                contentType(ContentType.Application.Json)
                setBody(data)
            }
        }.body()
    }
}