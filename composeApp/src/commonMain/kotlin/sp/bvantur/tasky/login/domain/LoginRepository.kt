package sp.bvantur.tasky.login.domain

interface LoginRepository {
    suspend fun login(email: String, password: String): Boolean
}
