package sp.bvantur.tasky.register.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import sp.bvantur.tasky.register.presentation.RegisterViewModel

val registerModule = module {
    viewModelOf(::RegisterViewModel)
}
