package sp.bvantur.tasky.login.data

class LoginRepository(
    private val remoteDataSource: LoginRemoteDataSource,
    private val localDataSource: LoginLocalDataSource
) {
    suspend fun login(email: String, password: String): Result<LoginUserDataResponse> =
        remoteDataSource.login(LoginUserDataRequest(email, password))

    fun storeTokenInformation(accessToken: String, expirationTimestamp: Long) {
        localDataSource.saveLoginData(accessToken, expirationTimestamp)
    }
}
