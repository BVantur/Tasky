package sp.bvantur.tasky.splash.data

import sp.bvantur.tasky.core.data.SecurePersistentStorageProvider

class SplashLocalDataSource(private val secureStorageProvider: SecurePersistentStorageProvider) {

    fun isUserAuthorized(): Boolean = secureStorageProvider.isUserAuthorized()
}
