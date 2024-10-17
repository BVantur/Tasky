package sp.bvantur.tasky.event.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import sp.bvantur.tasky.event.data.EventLocalDataSource
import sp.bvantur.tasky.event.data.EventRemoteDataSource
import sp.bvantur.tasky.event.data.EventRepositoryImpl
import sp.bvantur.tasky.event.domain.EventRepository
import sp.bvantur.tasky.event.presentation.CreateEventViewModel
import sp.bvantur.tasky.event.presentation.SingleInputViewModel

val eventModule = module {
    viewModelOf(::SingleInputViewModel)
    viewModelOf(::CreateEventViewModel)
    viewModelOf(::CreateEventViewModel)
    singleOf(::EventRepositoryImpl).bind<EventRepository>()
    singleOf(::EventRemoteDataSource)
    singleOf(::EventLocalDataSource)
}
