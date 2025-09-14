package com.farad.entertainment.kidsanimalenglish.data.manager.responce

import com.farad.entertainment.kidsanimalenglish.app.BaseApp
import com.squareup.moshi.Types
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class NetworkCallAdapterFactory(
    private val app: BaseApp,
    private val baseRawType: Type,
    private val coroutineScope: CoroutineScope,
) : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit,
    ): CallAdapter<*, *>? {
        // suspend functions wrap the response type in `Call`
        if (Call::class.java != getRawType(returnType)) {
            return null
        }
        // check first that the return type is `ParameterizedType`
        check(returnType is ParameterizedType) {
            "return type must be parameterized as Call<NetworkResponse<<Foo>> or Call<NetworkResponse<out Foo>>"
        }

        // get the response type inside the `Call` type
        val responseType = getParameterUpperBound(0, returnType)
        if (getRawType(responseType) != NetworkResponse::class.java) {
            return null
        }

        check(responseType is ParameterizedType) {
            "Response must be parameterized as NetworkResponse<*>"
        }

        val successBodyType = getParameterUpperBound(0, responseType)
        val baseType = Types.newParameterizedType(baseRawType, successBodyType)
        return NetworkResponseAdapter<Any>(baseType, app, coroutineScope)
    }


    companion object {
        @JvmStatic
        fun create(
            app: BaseApp,
            baseRawType: Type,
            coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO),
        ): NetworkCallAdapterFactory = NetworkCallAdapterFactory(app, baseRawType, coroutineScope)
    }
}
