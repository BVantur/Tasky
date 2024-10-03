package sp.bvantur.tasky.core.data

import android.content.Context
import com.liftric.kvault.KVault

class SecurePersistentStorageProviderImpl(context: Context) : SecurePersistentStorageProvider() {
    override var kVault: KVault = KVault(context, "sp.bvantur.tasky.kvault")
}
