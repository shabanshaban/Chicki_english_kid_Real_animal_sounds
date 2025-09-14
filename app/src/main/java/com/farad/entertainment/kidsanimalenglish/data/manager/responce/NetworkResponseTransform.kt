@file:Suppress("unused")

package com.farad.entertainment.kidsanimalenglish.data.manager.responce

suspend inline fun <T> NetworkResponse<T>.onSuccess(
    crossinline onResult: suspend NetworkResponse.Success<T>.() -> Unit,
): NetworkResponse<T> {
    if (this is NetworkResponse.Success) {
        onResult(this)
    }
    return this
}

suspend inline fun <T> NetworkResponse<T>.onFailure(
    crossinline onResult: suspend NetworkResponse.Failure<T>.() -> Unit,
): NetworkResponse<T> {
    if (this is NetworkResponse.Failure<T>) {
        onResult(this)
    }
    return this
}

suspend inline fun <T> NetworkResponse<T>.onError(
    crossinline onResult: suspend NetworkResponse.Failure.Error<T>.() -> Unit,
): NetworkResponse<T> {
    if (this is NetworkResponse.Failure.Error) {
        onResult(this)
    }
    return this
}


suspend inline fun <T> NetworkResponse<T>.onException(
    crossinline onResult: suspend NetworkResponse.Failure.Exception<T>.() -> Unit,
): NetworkResponse<T> {
    if (this is NetworkResponse.Failure.Exception) {
        onResult(this)
    }
    return this
}

suspend inline fun <T> NetworkResponse<T>.onMessage(
    crossinline onResult: suspend (String?, StatusCode) -> Unit,
): NetworkResponse<T> {
    when (this) {
        is NetworkResponse.Success -> onResult(message, statusCode)
        is NetworkResponse.Failure.Error -> onResult(message, statusCode)
        is NetworkResponse.Failure.Exception -> onResult(message, StatusCode.Unknown)
    }
    return this
}


fun <T> NetworkResponse<T>.getOrNull(): T? {
    return when (this) {
        is NetworkResponse.Success -> safeData
        is NetworkResponse.Failure.Error -> null
        is NetworkResponse.Failure.Exception -> null
    }
}


fun <T> NetworkResponse<T>.getOrThrow(): T {
    when (this) {
        is NetworkResponse.Success -> return data
        is NetworkResponse.Failure.Error -> throw RuntimeException(response.message())
        is NetworkResponse.Failure.Exception -> throw exception
    }
}


fun <T> NetworkResponse.Failure<T>.getMessage(): String {
    return when (this) {
        is NetworkResponse.Failure.Error -> message
        is NetworkResponse.Failure.Exception -> message
    }
}

fun <T> NetworkResponse<T>.getMessage(): String? {
    return when (this) {
        is NetworkResponse.Success -> message
        is NetworkResponse.Failure -> getMessage()
    }
}

fun <T> NetworkResponse<T>.safeGetMessage(): String {
    return getMessage().toString()
}

fun <T> NetworkResponse<T>.getStatusCode(): StatusCode {
    return when (this) {
        is NetworkResponse.Success -> statusCode
        is NetworkResponse.Failure.Error -> statusCode
        is NetworkResponse.Failure.Exception -> StatusCode.Unknown
    }
}


inline val NetworkResponse<Any>.isSuccess: Boolean
    get() = this is NetworkResponse.Success

inline val NetworkResponse<Any>.isFailure: Boolean
    get() = this is NetworkResponse.Failure

inline val NetworkResponse<Any>.isError: Boolean
    get() = this is NetworkResponse.Failure.Error

inline val NetworkResponse<Any>.isException: Boolean
    get() = this is NetworkResponse.Failure.Exception


