package sp.bvantur.tasky.core.data.local

import sp.bvantur.tasky.core.data.local.SecurePersistentStorageProvider.Companion.ACCESS_TOKEN_KEY
import sp.bvantur.tasky.core.data.local.SecurePersistentStorageProvider.Companion.REFRESH_TOKEN_KEY

class UserAuthDataProvider(private val secureStorageProvider: SecurePersistentStorageProvider) {
    fun getAuthData(): Pair<String?, String?> = secureStorageProvider.kVault.string(ACCESS_TOKEN_KEY) to
        secureStorageProvider.kVault.string(REFRESH_TOKEN_KEY)
}
