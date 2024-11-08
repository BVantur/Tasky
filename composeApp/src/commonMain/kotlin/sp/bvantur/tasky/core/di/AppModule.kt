package sp.bvantur.tasky.core.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import sp.bvantur.tasky.agenda.di.eventModule
import sp.bvantur.tasky.core.domain.DispatcherProvider
import sp.bvantur.tasky.core.domain.TaskyDispatcherProvider
import sp.bvantur.tasky.home.di.homeModule
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
        loginModule,
        homeModule,
        eventModule
    )
}

val appModule = module {
    single<DispatcherProvider> {
        TaskyDispatcherProvider()
    }
}
