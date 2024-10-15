package sp.bvantur.tasky.core.domain

sealed interface CommunicationError : TaskyError {
    data class UnknownError(val message: String? = null) : CommunicationError
    // TODO add different errors
}
