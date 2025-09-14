package com.farad.entertainment.kidsanimalenglish.base

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farad.entertainment.kidsanimalenglish.app.BaseApp
import com.farad.entertainment.kidsanimalenglish.data.apiService.ApiConfig
import com.farad.entertainment.kidsanimalenglish.utils.Event
import com.farad.entertainment.kidsanimalenglish.data.manager.responce.NetworkResponse
import com.farad.entertainment.kidsanimalenglish.data.manager.responce.onError
import com.farad.entertainment.kidsanimalenglish.data.manager.responce.onException
import com.farad.entertainment.kidsanimalenglish.data.manager.responce.onFailure
import com.farad.entertainment.kidsanimalenglish.data.manager.responce.onSuccess

import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

@Suppress("unused", "PropertyName")
abstract class BaseViewModel : ViewModel(), KoinComponent {

    companion object {
        const val DEFAULT_TAG_REQUEST = "DEFAULT"
    }
   protected   val _progressLiveData = MutableLiveData<Event<Boolean>>()
    val progressLiveData: LiveData<Event<Boolean>> get() = _progressLiveData
    private val app: Application by inject()
    private val compositeDisposable = Vector<ApiConfig>()

    protected   val _errorLiveData = MutableLiveData<Event<Boolean>>()
    val errorLiveData: LiveData<Event<Boolean>> get() = _errorLiveData

    fun getString(@StringRes id: Int): String {
        return app.getString(id)
    }

    fun disposeAll() {
        compositeDisposable.forEach { apiConfig ->
            apiConfig.job.cancel("dispose $apiConfig")
        }
        compositeDisposable.clear()
    }

    @Suppress("unused")
    fun dispose(apiConfig: ApiConfig) {
        val api = compositeDisposable.firstOrNull { it == apiConfig }
        compositeDisposable.remove(api)
        api?.job?.cancel("dispose $apiConfig")
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }

    fun getBaseApp() = app as? BaseApp

    fun <T> callSafeApi(
        requestMethod: suspend () -> NetworkResponse<T>,
        handleProgress: Boolean = true,
        handleMessage: Boolean = true,
        forceOnline: Boolean = true,
        tag: String = DEFAULT_TAG_REQUEST,
        onSuccess: suspend NetworkResponse.Success<T>.() -> Unit = {},
        onFailure: suspend NetworkResponse.Failure<T>.() -> Unit = {},
        onError: suspend NetworkResponse.Failure.Error<T>.() -> Unit = {},
        onException: suspend NetworkResponse.Failure.Exception<T>.() -> Unit = {},
        onStart: suspend () -> Unit = {},
        onComplete: suspend () -> Unit = {},
    ): ApiConfig {

        val apiConfig = ApiConfig.Builder()
            .setHandleMessage(handleMessage)
            .setHandleProgress(handleProgress)
            .setForceOnline(forceOnline)
            .setTag(tag)
            .setJob(Job())
            .build()

        callRequest(
            apiConfig = apiConfig,
            onStart = {
                compositeDisposable.add(apiConfig)
                onStart()
                //showProgress(true, apiConfig)
            },
            onRequest = {
                requestMethod().onSuccess {  }

                requestMethod()
                    .onSuccess { onSuccess(this) }
                    .onFailure {
                        _errorLiveData.postValue(Event(true))
                        onFailure(this)
                    }
                    .onError {
                        _errorLiveData.postValue(Event(true))
                        onError(this)
                    }
                    .onException {
                        _errorLiveData.postValue(Event(true))
                        onException(this)
                    }


            } ,
            onCatch = {


                onException(NetworkResponse.Failure.Exception(it))
                // Log.e("request call api crash", it.toString())
            },
            onFinally = {
                // ProgressManager.postEvent(apiConfig, false, apiConfig.tag)
                onComplete()
            }

        )

        return apiConfig
    }

    private fun callRequest(
        apiConfig: ApiConfig,
        onStart: suspend () -> Unit,
        onRequest: suspend () -> Unit,
        onCatch: suspend (Throwable) -> Unit,
        onFinally: suspend () -> Unit,
    ) {

        viewModelScope.launch(apiConfig.job) {
            try {
                onStart()
                onRequest()
            } catch (t: Throwable) {
                onCatch(t)
            } finally {
                onFinally()
            }
        }
    }
}
