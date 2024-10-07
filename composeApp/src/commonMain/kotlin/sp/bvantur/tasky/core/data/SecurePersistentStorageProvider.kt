package sp.bvantur.tasky.core.data

import com.liftric.kvault.KVault

abstract class SecurePersistentStorageProvider {
    open lateinit var kVault: KVault

    companion object {
        const val ACCESS_TOKEN_KEY = "access_token"
        const val EXPIRATION_TIMESTAMP_TOKEN_KEY = "expiration_timestamp_token_key"
    }
}