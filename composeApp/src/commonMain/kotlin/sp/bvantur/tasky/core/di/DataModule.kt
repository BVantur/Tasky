package sp.bvantur.tasky.core.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import sp.bvantur.inspektify.ktor.InspektifyKtor
import sp.bvantur.inspektify.ktor.LogLevel
import sp.bvantur.tasky.TaskyBuildKonfig
import sp.bvantur.tasky.core.data.local.UserAuthDataProvider

private const val BASE_URL = "https://tasky.pl-coding.com/"

val dataModule = module {

    singleOf(::UserAuthDataProvider)

    single {
        Json {
            ignoreUnknownKeys = true
            explicitNulls = false
        }
    }

    single<HttpClient> {
        HttpClient(engine = get()) {
            install(ContentNegotiation) {
                json(get())
            }
            defaultRequest {
                url(BASE_URL)
            }
            install(InspektifyKtor) {
                logLevel = LogLevel.All
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        val authProvider: UserAuthDataProvider = get()
                        val (accessToken, refreshToken) = authProvider.getAuthData()
                        BearerTokens(
                            accessToken = accessToken ?: "",
                            refreshToken = refreshToken ?: ""
                        )
                    }
                    // TODO add refreshing of the tokens
                }
            }

            install(DefaultRequest) {
                header("x-api-key", TaskyBuildKonfig.API_KEY)
            }

            install(Logging)

            install(HttpTimeout) {
                requestTimeoutMillis = 15_000
                connectTimeoutMillis = 15_000
            }
        }
    }
}
