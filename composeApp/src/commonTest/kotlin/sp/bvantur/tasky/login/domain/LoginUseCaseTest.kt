@file:OptIn(ExperimentalCoroutinesApi::class)

package sp.bvantur.tasky.login.domain

import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import sp.bvantur.tasky.TestThrowable
import sp.bvantur.tasky.login.data.LoginUserDataResponse
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LoginUseCaseTest {

    private val repository = mock<LoginRepository>()

    @Test
    fun `GIVEN login fails WHEN use case is invoked THEN false is returned`() {
        runTest {
            Dispatchers.setMain(StandardTestDispatcher(testScheduler))
            everySuspend { repository.login(any(), any()) } returns Result.failure(TestThrowable)
            val useCase = LoginUseCase(repository).invoke("", "")

            assertFalse { useCase }
        }
    }

    @Test
    fun `GIVEN login succeeds WHEN use case is invoked THEN true is returned`() {
        runTest {
            Dispatchers.setMain(StandardTestDispatcher(testScheduler))
            everySuspend { repository.login(any(), any()) } returns Result.success(
                LoginUserDataResponse(
                    accessToken = "accessToken",
                    refreshToken = "refreshToken",
                    name = "name",
                    userId = "userId",
                    accessTokenExpirationTimestamp = 0L
                )
            )
            val useCase = LoginUseCase(repository).invoke("", "")

            assertTrue { useCase }
        }
    }
}
