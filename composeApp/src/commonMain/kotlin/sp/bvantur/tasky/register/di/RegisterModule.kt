package sp.bvantur.tasky.register.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import sp.bvantur.tasky.register.data.RegisterRemoteDataSource
import sp.bvantur.tasky.register.data.RegisterRepository
import sp.bvantur.tasky.register.domain.RegisterUserUseCase
import sp.bvantur.tasky.register.domain.RegisterUserUseCaseImpl
import sp.bvantur.tasky.register.presentation.RegisterViewModel

val registerModule = module {
    viewModelOf(::RegisterViewModel)

    single {
        RegisterRemoteDataSource(
            get(),
            get()
        )
    }

    single {
        RegisterRepository(
            get()
        )
    }

    single<RegisterUserUseCase> { RegisterUserUseCaseImpl(get()) }
}