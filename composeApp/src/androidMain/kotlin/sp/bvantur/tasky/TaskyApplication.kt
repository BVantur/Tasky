package sp.bvantur.tasky

import android.app.Application
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.dsl.module
import sp.bvantur.tasky.core.di.initKoin

class TaskyApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        initKoin(
            targetModule = module {
                single<Context> { this@TaskyApplication }
            }
        ).also {
            it.workManagerFactory()
        }
    }
}
