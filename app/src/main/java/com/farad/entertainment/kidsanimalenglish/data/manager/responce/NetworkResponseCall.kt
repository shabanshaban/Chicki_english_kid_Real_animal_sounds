package com.farad.entertainment.kidsanimalenglish.data.manager.responce

import android.util.Log
import androidx.media3.common.util.UnstableApi
import com.farad.entertainment.kidsanimalenglish.app.BaseApp
import com.farad.entertainment.kidsanimalenglish.utils.isNetworkAvailable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse
@UnstableApi
internal class NetworkResponseCall<S>(
    private val delegate: Call<S>,
    private val app: BaseApp,
    private val coroutineScope: CoroutineScope,
) : Call<NetworkResponse<S>> {

    override fun enqueue(callback: Callback<NetworkResponse<S>>) {
        coroutineScope.launch {
            val call = this@NetworkResponseCall
            if (!app.isNetworkAvailable()) {
                val networkResponse = NetworkResponse.Failure.Exception<S>(OfflineException())
                Log.e("ApiManager", "OfflineException $networkResponse")
                callback.onResponse(call, Response.success(networkResponse))
                return@launch
            }

            runCatching {
                val response = delegate.awaitResponse()
                NetworkResponse.of { response }

            }.onSuccess { networkResponse ->
                Log.e("ApiManager", "Success $networkResponse")
                callback.onResponse(call, Response.success(networkResponse))
            }.onFailure {
                when (it) {
                    is ApiException -> {
                        val messageServer = it.messageServer
                        val responseOkhttp = it.response
                        val response = Response.error<S>(responseOkhttp.code, responseOkhttp.body)
                        val networkResponse = NetworkResponse.Failure.Error(response, it.message)
                        callback.onResponse(call, Response.success(networkResponse))
                        Log.e("ApiManager", "ApiException $networkResponse")
                        //Sentry.captureMessage("ApiException $messageServer \n $response")
                    }
                    is UnauthorizedException -> {
                        val networkResponse = NetworkResponse.Failure.Exception<S>(it)
                        callback.onResponse(call, Response.success(networkResponse))
                        Log.e("ApiManager", "UnauthorizedException $networkResponse")
                      //  app.logOut()
                       // Sentry.captureMessage("ApiUnauthorizedException $networkResponse")
                    }
                    else -> {
                        val networkResponse = NetworkResponse.Failure.Exception<S>(it)
                        callback.onResponse(call, Response.success(networkResponse))
                        Log.e("ApiManager", "Exception $networkResponse")
                       // Sentry.captureMessage("ApiFailure $networkResponse")
                    }
                }

            }
        }
    }

    override fun isExecuted() = delegate.isExecuted

    override fun clone() = NetworkResponseCall(delegate.clone(), app, coroutineScope)

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<NetworkResponse<S>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}