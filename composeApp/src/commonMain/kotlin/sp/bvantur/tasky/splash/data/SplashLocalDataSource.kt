package sp.bvantur.tasky.splash.data

import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.withContext
import sp.bvantur.tasky.core.data.TaskyDatabase
import sp.bvantur.tasky.core.data.local.SecurePersistentStorageProvider
import sp.bvantur.tasky.core.data.local.SecurePersistentStorageProvider.Companion.ACCESS_TOKEN_KEY
import sp.bvantur.tasky.core.data.local.SecurePersistentStorageProvider.Companion.EXPIRATION_TIMESTAMP_TOKEN_KEY
import sp.bvantur.tasky.core.domain.DispatcherProvider

class SplashLocalDataSource(
    private val secureStorageProvider: SecurePersistentStorageProvider,
    private val database: TaskyDatabase,
    private val dispatcherProvider: DispatcherProvider
) {

    fun isUserAuthorized(): Boolean {
        secureStorageProvider.kVault.string(ACCESS_TOKEN_KEY) ?: return false

        val expirationTimestamp = secureStorageProvider.kVault.long(EXPIRATION_TIMESTAMP_TOKEN_KEY) ?: return false

        val currentTimestamp = getTimeMillis()
        return expirationTimestamp > currentTimestamp
    }

    suspend fun clearDatabaseContent() {
        withContext(dispatcherProvider.io) {
            database.getAttendeeDao().removeAttendeeData()
        }
    }
}
