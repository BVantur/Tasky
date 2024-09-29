package sp.bvantur.tasky.core.data

import com.liftric.kvault.KVault

class SecurePersistentStorageProviderImpl : SecurePersistentStorageProvider {
    override val kVault: KVault
        get() = KVault()
}
