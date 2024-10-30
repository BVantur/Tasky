package sp.bvantur.tasky.core.di

import android.content.Context
import androidx.work.WorkManager
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import sp.bvantur.tasky.TaskyApplication
import sp.bvantur.tasky.core.data.SecurePersistentStorageProviderImpl
import sp.bvantur.tasky.core.data.SyncCreateEventWorker
import sp.bvantur.tasky.core.data.SyncDeleteEventWorker
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
        (get<Context>() as TaskyApplication).applicationScope
    }

    singleOf(::TaskySyncScheduler)
    workerOf(::SyncCreateEventWorker)
    workerOf(::SyncDeleteEventWorker)

    single {
        WorkManager.getInstance(get())
    }
}
