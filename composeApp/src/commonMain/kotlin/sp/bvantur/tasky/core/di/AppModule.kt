package sp.bvantur.tasky.core.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module
import sp.bvantur.tasky.core.DispatcherProvider
import sp.bvantur.tasky.core.TaskyDispatcherProvider
import sp.bvantur.tasky.register.di.registerModule

fun initKoin(): KoinApplication = startKoin {
    modules(
        appModule,
        dataModule,
        domainModule,
        platformModule,
        registerModule
    )
}

val appModule = module {
    single<DispatcherProvider> {
        TaskyDispatcherProvider()
    }
}
