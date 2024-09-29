package sp.bvantur.tasky.core.di

import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import sp.bvantur.tasky.core.data.SecurePersistentStorageProvider
import sp.bvantur.tasky.core.data.SecurePersistentStorageProviderImpl

actual val platformModule = module {
    single {
        Darwin.create()
    }

    singleOf(::SecurePersistentStorageProviderImpl).bind<SecurePersistentStorageProvider>()
}
