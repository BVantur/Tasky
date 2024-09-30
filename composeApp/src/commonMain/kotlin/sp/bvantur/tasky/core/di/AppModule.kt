package sp.bvantur.tasky.core.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import sp.bvantur.tasky.core.DispatcherProvider
import sp.bvantur.tasky.core.TaskyDispatcherProvider
import sp.bvantur.tasky.login.di.loginModule
import sp.bvantur.tasky.register.di.registerModule
import sp.bvantur.tasky.splash.di.splashModule

fun initKoin(targetModule: Module = module { }): KoinApplication = startKoin {
    modules(
        targetModule,
        appModule,
        dataModule,
        domainModule,
        platformModule,
        splashModule,
        registerModule,
        loginModule
    )
}

val appModule = module {
    single<DispatcherProvider> {
        TaskyDispatcherProvider()
    }
}
