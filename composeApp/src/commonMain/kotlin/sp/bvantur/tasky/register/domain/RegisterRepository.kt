package sp.bvantur.tasky.register.domain

import sp.bvantur.tasky.core.domain.TaskyEmptyResult
import sp.bvantur.tasky.core.domain.TaskyError

interface RegisterRepository {
    suspend fun register(name: String, email: String, password: String): TaskyEmptyResult<TaskyError>
}
