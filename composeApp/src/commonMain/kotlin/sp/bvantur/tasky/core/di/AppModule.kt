package sp.bvantur.tasky.core.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module
import sp.bvantur.tasky.core.domain.DispatcherProvider
import sp.bvantur.tasky.core.domain.TaskyDispatcherProvider
import sp.bvantur.tasky.login.di.loginModule
import sp.bvantur.tasky.register.di.registerModule

fun initKoin(): KoinApplication = startKoin {
    modules(
        appModule,
        dataModule,
        domainModule,
        platformModule,
        registerModule,
        loginModule
    )
}

val appModule = module {
    single<DispatcherProvider> {
        TaskyDispatcherProvider()
    }
}
