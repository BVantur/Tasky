package sp.bvantur.tasky.splash.data

import sp.bvantur.tasky.splash.domain.SplashRepository

class SplashRepositoryImpl(private val localDataSource: SplashLocalDataSource) : SplashRepository {
    override fun isUserAuthorized(): Boolean = localDataSource.isUserAuthorized()

    override suspend fun clearDatabaseContent() {
        localDataSource.clearDatabaseContent()
    }
}
