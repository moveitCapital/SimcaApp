package org.moveitcapital.simcap.core

/** A sealed class to represent success and failure results.
 * @param T The type of the successful result.
 */
sealed class ResultHandler<out T> {
    data class Success<out T>(val data: T) : ResultHandler<T>()
    data class Failure(val error: AppError) : ResultHandler<Nothing>()
}

/**
 * A helper function to safely call a suspend function and handle exceptions.
 * @param block The suspend function to be called.
 * @return A [ResultHandler] containing either the successful result or an [AppError].
 */
suspend fun <T> safeCall(block: suspend () -> T): ResultHandler<T> {
    runCatching {
        block()
    }.onSuccess {
        return ResultHandler.Success(it)
    }.onFailure {
        return ResultHandler.Failure(it.toAppError())
    }
    return ResultHandler.Failure(AppError.Unknown(null))
}

/**
 * Extension function to transform the successful result of a [ResultHandler] using the provided [block].
 * If the [ResultHandler] is a failure, it returns the same failure.
 * Any exceptions thrown during the transformation are caught and converted to a [ResultHandler.Failure].
 *
 * @param block A function that takes the successful result and transforms it to another type.
 * @return A new [ResultHandler] containing the transformed result or a failure.
 */
fun <T, R> ResultHandler<T>.map(block: (T) -> R): ResultHandler<R> {
    return runCatching {
        when (this) {
            is ResultHandler.Success -> ResultHandler.Success(block(data))
            is ResultHandler.Failure -> this
        }
    }.getOrElse { ResultHandler.Failure(it.toAppError()) }
}