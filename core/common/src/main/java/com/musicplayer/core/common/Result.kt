package com.musicplayer.core.common

/**
 * Sealed class representing the result of an operation that can succeed or fail.
 * Used throughout the app for consistent error handling.
 */
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable, val message: String = exception.message ?: "Unknown error") : Result<Nothing>()
    data object Loading : Result<Nothing>()
}

/**
 * Extension to safely unwrap success data or return null.
 */
fun <T> Result<T>.getOrNull(): T? = when (this) {
    is Result.Success -> data
    else -> null
}

/**
 * Extension to get error message or null.
 */
fun <T> Result<T>.errorOrNull(): String? = when (this) {
    is Result.Error -> message
    else -> null
}

/**
 * Extension to check if result is success.
 */
fun <T> Result<T>.isSuccess(): Boolean = this is Result.Success

/**
 * Extension to check if result is error.
 */
fun <T> Result<T>.isError(): Boolean = this is Result.Error

/**
 * Extension to check if result is loading.
 */
fun <T> Result<T>.isLoading(): Boolean = this is Result.Loading

/**
 * Transform success data while preserving error/loading states.
 */
inline fun <T, R> Result<T>.map(transform: (T) -> R): Result<R> = when (this) {
    is Result.Success -> Result.Success(transform(data))
    is Result.Error -> this
    is Result.Loading -> this
}
