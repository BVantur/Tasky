package sp.bvantur.tasky.register.data

class RegisterRepository(private val registerRemoteDataSource: RegisterRemoteDataSource) {
    suspend fun register(name: String, email: String, password: String): Result<Unit> =
        registerRemoteDataSource.register(RegisterUserDataRequest(name, email, password))
}
