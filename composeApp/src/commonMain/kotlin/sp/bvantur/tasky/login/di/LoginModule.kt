package sp.bvantur.tasky.login.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import sp.bvantur.tasky.login.presentation.LoginViewModel

val loginModule = module {
    viewModelOf(::LoginViewModel)
}
