package sp.bvantur.tasky.login.data

import sp.bvantur.tasky.core.data.SecurePersistentStorageProvider

class LoginLocalDataSource(private val secureStorageProvider: SecurePersistentStorageProvider) {

    companion object {
        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val EXPIRATION_TIMESTAMP_TOKEN_KEY = "expiration_timestamp_token_key"
    }

    fun saveLoginData(accessToken: String, expirationTimestamp: Long) {
        secureStorageProvider.kVault.set(key = ACCESS_TOKEN_KEY, stringValue = accessToken)
        secureStorageProvider.kVault.set(key = EXPIRATION_TIMESTAMP_TOKEN_KEY, longValue = expirationTimestamp)
    }
}
