package sp.bvantur.tasky.login.domain

import sp.bvantur.tasky.login.data.LoginRepository

class LoginUseCase(private val repository: LoginRepository) {
    suspend operator fun invoke(email: String, password: String): Boolean {
        val response = repository.login(email, password)
        if (response.isSuccess) {
            val userInformation = response.getOrNull() ?: return false

            repository.storeTokenInformation(
                userInformation.accessToken,
                userInformation.accessTokenExpirationTimestamp
            )
            return true
        }
        return false
    }
}
