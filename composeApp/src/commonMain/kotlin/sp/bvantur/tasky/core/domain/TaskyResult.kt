package sp.bvantur.tasky.core.domain

sealed interface TaskyResult<out D, out E : TaskyError> {
    data class Success<out D>(val data: D) : TaskyResult<D, Nothing>
    data class Error<out E : TaskyError>(val error: E) : TaskyResult<Nothing, E>
}

inline fun <T, E : TaskyError, R> TaskyResult<T, E>.map(map: (T) -> R): TaskyResult<R, E> = when (this) {
    is TaskyResult.Error -> TaskyResult.Error(error)
    is TaskyResult.Success -> TaskyResult.Success(map(data))
}

fun <T, E : TaskyError> TaskyResult<T, E>.asEmptyDataResult(): TaskyEmptyResult<E> = map { }

inline fun <T, E : TaskyError> TaskyResult<T, E>.onSuccess(action: (T) -> Unit): TaskyResult<T, E> = when (this) {
    is TaskyResult.Error -> this
    is TaskyResult.Success -> {
        action(data)
        this
    }
}
inline fun <T, E : TaskyError> TaskyResult<T, E>.onError(action: (E) -> Unit): TaskyResult<T, E> = when (this) {
    is TaskyResult.Error -> {
        action(error)
        this
    }
    is TaskyResult.Success -> this
}
inline fun <T, E : TaskyError> TaskyResult<T, E>.isSuccess() = this is TaskyResult.Success<T>
inline fun <T, E : TaskyError> TaskyResult<T, E>.isError() = this is TaskyResult.Error<E>

typealias TaskyEmptyResult<E> = TaskyResult<Unit, E>
