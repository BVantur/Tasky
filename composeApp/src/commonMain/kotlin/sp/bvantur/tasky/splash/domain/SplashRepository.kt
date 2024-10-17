package sp.bvantur.tasky.splash.domain

interface SplashRepository {
    fun isUserAuthorized(): Boolean
    suspend fun clearDatabaseContent()
}
