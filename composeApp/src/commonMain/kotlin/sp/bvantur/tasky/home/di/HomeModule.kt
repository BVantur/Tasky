package sp.bvantur.tasky.home.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import sp.bvantur.tasky.home.data.HomeRepositoryImpl
import sp.bvantur.tasky.home.data.local.HomeLocalDataSource
import sp.bvantur.tasky.home.data.remote.HomeRemoteDataSource
import sp.bvantur.tasky.home.domain.HomeRepository
import sp.bvantur.tasky.home.presentation.HomeViewModel

val homeModule = module {
    viewModelOf(::HomeViewModel)
    singleOf(::HomeRepositoryImpl).bind<HomeRepository>()
    singleOf(::HomeLocalDataSource)
    singleOf(::HomeRemoteDataSource)
}
