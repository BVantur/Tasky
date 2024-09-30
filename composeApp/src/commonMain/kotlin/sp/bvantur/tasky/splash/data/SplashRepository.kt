package sp.bvantur.tasky.splash.data

class SplashRepository(private val localDataSource: SplashLocalDataSource) {
    fun isUserAuthorized(): Boolean = localDataSource.isUserAuthorized()
}
