package sp.bvantur.tasky

import android.app.Application
import android.content.Context
import org.koin.dsl.module
import sp.bvantur.tasky.core.di.initKoin

class TaskyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(
            targetModule = module {
                single<Context> { this@TaskyApplication }
            }
        )
    }
}
