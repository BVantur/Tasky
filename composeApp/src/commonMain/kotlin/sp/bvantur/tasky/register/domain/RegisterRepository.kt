package sp.bvantur.tasky.register.domain

import sp.bvantur.tasky.core.domain.CommunicationError
import sp.bvantur.tasky.core.domain.TaskyEmptyResult

interface RegisterRepository {
    suspend fun register(name: String, email: String, password: String): TaskyEmptyResult<CommunicationError>
}
