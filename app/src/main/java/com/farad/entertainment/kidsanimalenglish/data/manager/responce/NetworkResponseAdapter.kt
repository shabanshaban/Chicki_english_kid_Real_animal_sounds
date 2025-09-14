package com.farad.entertainment.kidsanimalenglish.data.manager.responce

import com.farad.entertainment.kidsanimalenglish.app.BaseApp
import kotlinx.coroutines.CoroutineScope
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class NetworkResponseAdapter<S>(
    private val baseType: Type,
    private val app: BaseApp,
    private val coroutineScope: CoroutineScope,
) : CallAdapter<S, Call<NetworkResponse<S>>> {

    override fun responseType(): Type = baseType

    override fun adapt(call: Call<S>): Call<NetworkResponse<S>> {
        return NetworkResponseCall(call, app, coroutineScope)
    }
}
