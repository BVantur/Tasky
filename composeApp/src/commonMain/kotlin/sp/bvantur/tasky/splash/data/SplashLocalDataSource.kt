package sp.bvantur.tasky.splash.data

import io.ktor.util.date.getTimeMillis
import sp.bvantur.tasky.core.data.SecurePersistentStorageProvider
import sp.bvantur.tasky.core.data.SecurePersistentStorageProvider.Companion.ACCESS_TOKEN_KEY
import sp.bvantur.tasky.core.data.SecurePersistentStorageProvider.Companion.EXPIRATION_TIMESTAMP_TOKEN_KEY

class SplashLocalDataSource(private val secureStorageProvider: SecurePersistentStorageProvider) {

    fun isUserAuthorized(): Boolean {
        secureStorageProvider.kVault.string(ACCESS_TOKEN_KEY) ?: return false

        val expirationTimestamp = secureStorageProvider.kVault.long(EXPIRATION_TIMESTAMP_TOKEN_KEY) ?: return false

        val currentTimestamp = getTimeMillis()
        return expirationTimestamp > currentTimestamp
    }
}
