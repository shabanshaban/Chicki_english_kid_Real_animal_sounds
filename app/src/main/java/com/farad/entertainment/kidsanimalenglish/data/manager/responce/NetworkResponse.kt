package com.farad.entertainment.kidsanimalenglish.data.manager.responce

import androidx.annotation.Keep
import okhttp3.Headers
import okhttp3.ResponseBody
import retrofit2.Response
import java.net.SocketTimeoutException

@Suppress("unused", "MemberVisibilityCanBePrivate")
@Keep
sealed class NetworkResponse<out T> {


    data class Success<T>(
        val response: Response<T>,
        val baseResponse: BaseResponse<T>,
    ) : NetworkResponse<T>() {
        val statusCode: StatusCode = getStatusCodeFromResponse(response)
        val headers: Headers = response.headers()
        val raw: okhttp3.Response = response.raw()
        val message: String? = baseResponse.message
        val data: T by lazy { baseResponse.data ?: throw NoContentException(statusCode.code) }
        val safeData: T? by lazy { baseResponse.data }
        val hasData = baseResponse.data != null

        @Suppress("IMPLICIT_CAST_TO_ANY")
        override fun toString(): String =
            "[NetworkResponse.Success](${if (hasData) data else message})"
    }


    sealed class Failure<T> : NetworkResponse<T>() {


        data class Error<T>(val response: Response<T>, val messageServer: String?) :
            Failure<T>() {
            val statusCode: StatusCode = getStatusCodeFromResponse(response)
            val headers: Headers = response.headers()
            val raw: okhttp3.Response = response.raw()
            val message: String = messageServer.toString()
            val errorBody: ResponseBody? = response.errorBody()
            override fun toString(): String {
                return try {
                    val errorBody = errorBody?.string()
                    if (!errorBody.isNullOrEmpty()) {
                        errorBody
                    } else {
                        "[NetworkResponse.Failure.Error-$statusCode]"
                    }
                } catch (t: Throwable) {
                    "[NetworkResponse.Failure.Error-$t]"
                }
            }
        }


        data class Exception<T>(val exception: Throwable) : Failure<T>() {
            val message: String =
                when (exception) {
                    is OfflineException ->  "OfflineException"
                    is SocketTimeoutException -> "SocketTimeoutException"
                    is CustomException -> exception.message
                    else -> exception.message.toString()
                }

            override fun toString(): String =
                "[NetworkResponse.Failure.Exception](exception= $exception)"
        }

    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        inline fun <T> of(
            crossinline f: () -> Response<T>,
        ): NetworkResponse<T> = try {
            val response = f()
            val baseResponse = response.body() as? BaseResponse<T>
            if (response.isSuccessful && baseResponse != null) {
                Success(response, baseResponse)
            } else {
                Failure.Error(response, baseResponse?.message)
            }
        } catch (ex: Exception) {
            Failure.Exception(ex)
        }


        fun getStatusCodeFromResponse(response: Response<*>): StatusCode {
            return StatusCode.values().find { it.code == response.code() }
                ?: StatusCode.Unknown
        }

        fun getStatusCodeFromCode(code: Int): StatusCode {
            return StatusCode.values().find { it.code == code }
                ?: StatusCode.Unknown
        }
    }

}





