package sp.bvantur.tasky.splash.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import sp.bvantur.tasky.splash.data.SplashLocalDataSource
import sp.bvantur.tasky.splash.data.SplashRepositoryImpl
import sp.bvantur.tasky.splash.domain.SplashRepository
import sp.bvantur.tasky.splash.presentation.SplashViewModel

val splashModule = module {
    viewModelOf(::SplashViewModel)
    singleOf(::SplashRepositoryImpl).bind<SplashRepository>()
    singleOf(::SplashLocalDataSource)
}
