package sp.bvantur.tasky.event.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import sp.bvantur.tasky.event.data.CreateEventRemoteDataSource
import sp.bvantur.tasky.event.data.CreateEventRepositoryImpl
import sp.bvantur.tasky.event.domain.CreateEventRepository
import sp.bvantur.tasky.event.presentation.CreateEventViewModel
import sp.bvantur.tasky.event.presentation.SingleInputViewModel

val eventModule = module {
    viewModelOf(::SingleInputViewModel)
    viewModelOf(::CreateEventViewModel)
    viewModelOf(::CreateEventViewModel)
    singleOf(::CreateEventRepositoryImpl).bind<CreateEventRepository>()
    singleOf(::CreateEventRemoteDataSource)
}
