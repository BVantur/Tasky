package sp.bvantur.tasky.core.data

import com.liftric.kvault.KVault
import sp.bvantur.tasky.core.data.local.SecurePersistentStorageProvider

class SecurePersistentStorageProviderImpl : SecurePersistentStorageProvider() {

    override var kVault: KVault = KVault()
}
