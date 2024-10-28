package sp.bvantur.tasky.login.data

import sp.bvantur.tasky.core.data.local.SecurePersistentStorageProvider
import sp.bvantur.tasky.core.data.local.SecurePersistentStorageProvider.Companion.ACCESS_TOKEN_KEY
import sp.bvantur.tasky.core.data.local.SecurePersistentStorageProvider.Companion.EXPIRATION_TIMESTAMP_TOKEN_KEY
import sp.bvantur.tasky.core.data.local.SecurePersistentStorageProvider.Companion.REFRESH_TOKEN_KEY
import sp.bvantur.tasky.core.data.local.SecurePersistentStorageProvider.Companion.USER_ID

class LoginLocalDataSource(private val secureStorageProvider: SecurePersistentStorageProvider) {

    fun saveLoginData(accessToken: String, refreshToken: String, expirationTimestamp: Long, userId: String) {
        secureStorageProvider.kVault.set(key = ACCESS_TOKEN_KEY, stringValue = accessToken)
        secureStorageProvider.kVault.set(key = REFRESH_TOKEN_KEY, stringValue = refreshToken)
        secureStorageProvider.kVault.set(key = EXPIRATION_TIMESTAMP_TOKEN_KEY, longValue = expirationTimestamp)
        secureStorageProvider.kVault.set(key = USER_ID, stringValue = userId)
    }
}
