package sp.bvantur.tasky.core.domain

sealed interface TaskyError {
    data object SqlError : TaskyError
    data object RequestTimeout : TaskyError
    data object BadRequest : TaskyError
    data object RedirectError : TaskyError
    data object Unauthorized : TaskyError
    data object Conflict : TaskyError
    data object NoInternet : TaskyError
    data object ServerError : TaskyError
    data class UnknownError(val message: String? = null) : TaskyError

    fun isSyncError() = this !is UnknownError
}
