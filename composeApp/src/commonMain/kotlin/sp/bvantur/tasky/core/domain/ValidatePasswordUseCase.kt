package sp.bvantur.tasky.core.domain

class ValidatePasswordUseCase {
    operator fun invoke(password: String): Boolean {
        val passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{9,}$"
        return password.matches(Regex(passwordPattern))
    }
}
