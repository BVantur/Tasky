package sp.bvantur.tasky.login.data

import sp.bvantur.tasky.core.data.SecurePersistentStorageProvider

class LoginLocalDataSource(private val secureStorageProvider: SecurePersistentStorageProvider) {

    fun saveLoginData(accessToken: String, expirationTimestamp: Long) {
        secureStorageProvider.saveLoginData(accessToken, expirationTimestamp)
    }
}
