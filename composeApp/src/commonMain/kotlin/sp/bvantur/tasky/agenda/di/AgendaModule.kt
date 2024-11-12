package sp.bvantur.tasky.agenda.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import sp.bvantur.tasky.agenda.data.AgendaRepositoryImpl
import sp.bvantur.tasky.agenda.data.local.AgendaLocalDataSource
import sp.bvantur.tasky.agenda.domain.AgendaRepository
import sp.bvantur.tasky.agenda.presentation.AgendaDetailsViewModel
import sp.bvantur.tasky.agenda.presentation.SingleInputViewModel
import sp.bvantur.tasky.core.data.remote.EventRemoteDataSource

val eventModule = module {
    viewModelOf(::SingleInputViewModel)
    viewModelOf(::AgendaDetailsViewModel)
    singleOf(::AgendaRepositoryImpl).bind<AgendaRepository>()
    singleOf(::EventRemoteDataSource)
    singleOf(::AgendaLocalDataSource)
}
