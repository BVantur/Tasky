package sp.bvantur.tasky.register.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import sp.bvantur.tasky.register.data.RegisterRemoteDataSource
import sp.bvantur.tasky.register.data.RegisterRepositoryImpl
import sp.bvantur.tasky.register.domain.RegisterRepository
import sp.bvantur.tasky.register.presentation.RegisterViewModel

val registerModule = module {
    viewModelOf(::RegisterViewModel)
    singleOf(::RegisterRepositoryImpl).bind<RegisterRepository>()
    singleOf(::RegisterRemoteDataSource)
}
