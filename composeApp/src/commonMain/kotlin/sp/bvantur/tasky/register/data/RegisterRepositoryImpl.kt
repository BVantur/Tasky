package sp.bvantur.tasky.register.data

import sp.bvantur.tasky.register.domain.RegisterRepository

class RegisterRepositoryImpl(private val registerRemoteDataSource: RegisterRemoteDataSource) : RegisterRepository {
    override suspend fun register(name: String, email: String, password: String): Result<Unit> =
        registerRemoteDataSource.register(RegisterUserDataRequest(name, email, password))
}
