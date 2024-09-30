package sp.bvantur.tasky.core.data

import com.liftric.kvault.KVault
import io.ktor.util.date.getTimeMillis

abstract class SecurePersistentStorageProvider {
    companion object {
        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val EXPIRATION_TIMESTAMP_TOKEN_KEY = "expiration_timestamp_token_key"
    }

    protected open lateinit var kVault: KVault

    fun saveLoginData(accessToken: String, expirationTimestamp: Long) {
        kVault.set(key = ACCESS_TOKEN_KEY, stringValue = accessToken)
        kVault.set(key = EXPIRATION_TIMESTAMP_TOKEN_KEY, longValue = expirationTimestamp)
    }

    fun isUserAuthorized(): Boolean {
        kVault.string(ACCESS_TOKEN_KEY) ?: return false

        val expirationTimestamp = kVault.long(EXPIRATION_TIMESTAMP_TOKEN_KEY) ?: return false

        val currentTimestamp = getTimeMillis()
        return expirationTimestamp > currentTimestamp
    }
}
