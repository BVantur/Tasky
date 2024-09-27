package sp.bvantur.tasky.core.di

import org.koin.dsl.module
import sp.bvantur.tasky.core.domain.ValidateEmailUseCase
import sp.bvantur.tasky.core.domain.ValidateEmailUseCaseImpl
import sp.bvantur.tasky.core.domain.ValidateNameUseCase
import sp.bvantur.tasky.core.domain.ValidateNameUseCaseImpl
import sp.bvantur.tasky.core.domain.ValidatePasswordUseCase
import sp.bvantur.tasky.core.domain.ValidatePasswordUseCaseImpl

val domainModule = module {
    single<ValidateNameUseCase> { ValidateNameUseCaseImpl() }
    single<ValidateEmailUseCase> { ValidateEmailUseCaseImpl() }
    single<ValidatePasswordUseCase> { ValidatePasswordUseCaseImpl() }
}
