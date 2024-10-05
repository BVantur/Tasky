package sp.bvantur.tasky.register.domain

class ValidateNameUseCase {
    operator fun invoke(name: String): Boolean = name.length in MIN_NAME_LENGTH..MAX_NAME_LENGTH

    companion object {
        private const val MIN_NAME_LENGTH = 4
        const val MAX_NAME_LENGTH = 50
    }
}
