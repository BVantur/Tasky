package sp.bvantur.tasky.event.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import sp.bvantur.tasky.event.presentation.SingleInputViewModel
import sp.bvantur.tasky.event.presentation.CreateEventViewModel

val eventModule = module {
    viewModelOf(::SingleInputViewModel)
    viewModelOf(::CreateEventViewModel)
}
