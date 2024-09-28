package sp.bvantur.tasky.register.domain

class ValidateNameUseCase {
    companion object {
        private const val MIN_NAME_LENGTH = 4
        const val MAX_NAME_LENGTH = 50
    }
    operator fun invoke(name: String): Boolean = name.length in MIN_NAME_LENGTH..MAX_NAME_LENGTH
}
