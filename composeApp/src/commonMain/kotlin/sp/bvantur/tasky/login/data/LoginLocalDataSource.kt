package sp.bvantur.tasky.login.data

import sp.bvantur.tasky.core.data.SecurePersistentStorageProvider
import sp.bvantur.tasky.core.data.SecurePersistentStorageProvider.Companion.ACCESS_TOKEN_KEY
import sp.bvantur.tasky.core.data.SecurePersistentStorageProvider.Companion.EXPIRATION_TIMESTAMP_TOKEN_KEY

class LoginLocalDataSource(private val secureStorageProvider: SecurePersistentStorageProvider) {

    fun saveLoginData(accessToken: String, expirationTimestamp: Long) {
        secureStorageProvider.kVault.set(key = ACCESS_TOKEN_KEY, stringValue = accessToken)
        secureStorageProvider.kVault.set(key = EXPIRATION_TIMESTAMP_TOKEN_KEY, longValue = expirationTimestamp)
    }
}
