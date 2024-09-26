package sp.bvantur.tasky

import android.app.Application
import sp.bvantur.tasky.core.di.initKoin

class TaskyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
