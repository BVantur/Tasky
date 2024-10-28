package sp.bvantur.tasky.register.data

import sp.bvantur.tasky.core.domain.TaskyEmptyResult
import sp.bvantur.tasky.core.domain.TaskyError
import sp.bvantur.tasky.core.domain.asEmptyDataResult
import sp.bvantur.tasky.register.domain.RegisterRepository

class RegisterRepositoryImpl(private val registerRemoteDataSource: RegisterRemoteDataSource) : RegisterRepository {
    override suspend fun register(name: String, email: String, password: String): TaskyEmptyResult<TaskyError> =
        registerRemoteDataSource.register(
            RegisterUserDataRequest(name, email, password)
        ).asEmptyDataResult() // TODO better error handling
}
