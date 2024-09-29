package sp.bvantur.tasky.core.di

import org.koin.dsl.module
import sp.bvantur.tasky.core.domain.ValidateEmailUseCase
import sp.bvantur.tasky.core.domain.ValidatePasswordUseCase
import sp.bvantur.tasky.register.domain.ValidateNameUseCase

val domainModule = module {
    single { ValidateNameUseCase() }
    single { ValidateEmailUseCase() }
    single { ValidatePasswordUseCase() }
}
