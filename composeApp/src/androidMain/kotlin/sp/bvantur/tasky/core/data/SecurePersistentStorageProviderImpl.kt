package sp.bvantur.tasky.core.data

import android.content.Context
import com.liftric.kvault.KVault

class SecurePersistentStorageProviderImpl(context: Context) : SecurePersistentStorageProvider {
    private val vault = KVault(context, "sp.bvantur.tasky.kvault")

    override val kVault: KVault
        get() = vault
}
