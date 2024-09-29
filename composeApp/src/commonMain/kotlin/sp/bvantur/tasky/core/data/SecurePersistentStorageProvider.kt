package sp.bvantur.tasky.core.data

import com.liftric.kvault.KVault

interface SecurePersistentStorageProvider {
    val kVault: KVault
}
