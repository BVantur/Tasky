package sp.bvantur.tasky.login.data

import sp.bvantur.tasky.login.domain.LoginRepository

open class LoginRepositoryImpl(
    private val remoteDataSource: LoginRemoteDataSource,
    private val localDataSource: LoginLocalDataSource
) : LoginRepository {
    override suspend fun login(email: String, password: String): Boolean {
        val response = remoteDataSource.login(LoginUserDataRequest(email, password)).also { response ->
            if (response.isSuccess) {
                val userInformation = response.getOrNull() ?: return@also

                localDataSource.saveLoginData(
                    userInformation.accessToken,
                    userInformation.accessTokenExpirationTimestamp
                )
            }
        }
        return response.isSuccess
    }
}
