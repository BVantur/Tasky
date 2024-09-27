package sp.bvantur.tasky.core.domain

interface ValidateNameUseCase {
    operator fun invoke(name: String): Boolean
}

class ValidateNameUseCaseImpl : ValidateNameUseCase {
    companion object {
        private const val MIN_NAME_LENGTH = 4
        private const val MAX_NAME_LENGTH = 50
    }
    override fun invoke(name: String): Boolean = name.length in MIN_NAME_LENGTH..MAX_NAME_LENGTH
}
