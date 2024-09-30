package sp.bvantur.tasky.core.data

import com.liftric.kvault.KVault

class SecurePersistentStorageProviderImpl : SecurePersistentStorageProvider() {

    override var kVault: KVault = KVault()
}
