package sp.bvantur.tasky.core.domain

class ValidateEmailUseCase {
    operator fun invoke(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(Regex(emailPattern))
    }
}
