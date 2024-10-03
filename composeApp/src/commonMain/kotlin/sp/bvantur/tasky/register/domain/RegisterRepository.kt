package sp.bvantur.tasky.register.domain

interface RegisterRepository {
    suspend fun register(name: String, email: String, password: String): Result<Unit>
}
