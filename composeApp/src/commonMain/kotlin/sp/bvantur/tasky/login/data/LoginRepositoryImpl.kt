package sp.bvantur.tasky.login.data

import sp.bvantur.tasky.core.domain.TaskyResult
import sp.bvantur.tasky.core.domain.onSuccess
import sp.bvantur.tasky.login.domain.LoginRepository

open class LoginRepositoryImpl(
    private val remoteDataSource: LoginRemoteDataSource,
    private val localDataSource: LoginLocalDataSource
) : LoginRepository {
    override suspend fun login(email: String, password: String): Boolean =
        remoteDataSource.login(LoginUserDataRequest(email, password)).onSuccess { data ->
            localDataSource.saveLoginData(
                accessToken = data.accessToken,
                refreshToken = data.refreshToken,
                expirationTimestamp = data.accessTokenExpirationTimestamp
            )
        } is TaskyResult.Success
}
