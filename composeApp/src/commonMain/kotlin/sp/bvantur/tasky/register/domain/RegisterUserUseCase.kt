package sp.bvantur.tasky.register.domain

import sp.bvantur.tasky.register.data.RegisterRepository

class RegisterUserUseCase(private val repository: RegisterRepository) {
    suspend operator fun invoke(name: String, email: String, password: String): Result<Unit> = repository.register(
        name = name,
        email = email,
        password = password
    )
}
