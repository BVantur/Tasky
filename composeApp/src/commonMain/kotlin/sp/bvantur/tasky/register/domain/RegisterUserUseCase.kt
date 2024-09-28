package sp.bvantur.tasky.register.domain

import sp.bvantur.tasky.register.data.RegisterRepository

interface RegisterUserUseCase {
    suspend operator fun invoke(name: String, email: String, password: String): Result<Unit>
}

class RegisterUserUseCaseImpl(private val repository: RegisterRepository) : RegisterUserUseCase {
    override suspend operator fun invoke(name: String, email: String, password: String): Result<Unit> =
        repository.register(
            name = name,
            email = email,
            password = password
        )
}
