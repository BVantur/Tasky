package sp.bvantur.tasky.event.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import sp.bvantur.tasky.event.presentation.CreateEventViewModel
import sp.bvantur.tasky.event.presentation.SingleInputViewModel

val eventModule = module {
    viewModelOf(::SingleInputViewModel)
    viewModelOf(::CreateEventViewModel)
}
