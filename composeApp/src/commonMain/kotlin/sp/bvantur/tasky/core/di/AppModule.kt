package sp.bvantur.tasky.core.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import sp.bvantur.tasky.login.di.loginModule

fun initKoin(): KoinApplication = startKoin {
    modules(
        loginModule
    )
}
