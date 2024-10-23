package sp.bvantur.tasky.core.di

import androidx.work.WorkManager
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import sp.bvantur.tasky.core.data.SecurePersistentStorageProviderImpl
import sp.bvantur.tasky.core.data.SyncEventsWorker
import sp.bvantur.tasky.core.data.TaskyDatabase
import sp.bvantur.tasky.core.data.TaskySyncScheduler
import sp.bvantur.tasky.core.data.createRoomDatabase
import sp.bvantur.tasky.core.data.local.SecurePersistentStorageProvider

actual val platformModule = module {
    single {
        OkHttp.create()
    }

    singleOf(::SecurePersistentStorageProviderImpl).bind<SecurePersistentStorageProvider>()

    single<TaskyDatabase> { createRoomDatabase(get(), get()) }

    single {
        CoroutineScope(SupervisorJob())
    }

    singleOf(::TaskySyncScheduler)
    workerOf(::SyncEventsWorker)

    single {
        WorkManager.getInstance(get())
    }
}
