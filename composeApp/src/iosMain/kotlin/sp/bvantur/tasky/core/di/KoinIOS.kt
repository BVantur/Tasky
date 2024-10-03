package sp.bvantur.tasky.core.di

import org.koin.core.KoinApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import sp.bvantur.tasky.core.data.SecurePersistentStorageProvider
import sp.bvantur.tasky.core.data.SecurePersistentStorageProviderImpl

fun initKoinIos(): KoinApplication = initKoin(
    module {
        singleOf(::SecurePersistentStorageProviderImpl).bind<SecurePersistentStorageProvider>()
    }
)
