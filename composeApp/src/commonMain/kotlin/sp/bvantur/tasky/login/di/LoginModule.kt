package sp.bvantur.tasky.login.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import sp.bvantur.tasky.login.data.LoginLocalDataSource
import sp.bvantur.tasky.login.data.LoginRemoteDataSource
import sp.bvantur.tasky.login.data.LoginRepositoryImpl
import sp.bvantur.tasky.login.domain.LoginRepository
import sp.bvantur.tasky.login.presentation.LoginViewModel

val loginModule = module {
    viewModelOf(::LoginViewModel)
    singleOf(::LoginRepositoryImpl).bind<LoginRepository>()
    singleOf(::LoginRemoteDataSource)
    singleOf(::LoginLocalDataSource)
}
