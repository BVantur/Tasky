package sp.bvantur.tasky.core.data

import android.content.Context
import com.liftric.kvault.KVault
import sp.bvantur.tasky.core.data.local.SecurePersistentStorageProvider

class SecurePersistentStorageProviderImpl(context: Context) : SecurePersistentStorageProvider() {
    override var kVault: KVault = KVault(context, "sp.bvantur.tasky.kvault")
}
