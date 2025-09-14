package com.farad.entertainment.kidsanimalenglish.cv.ancrashlytics.services
/*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Url


interface ApiServices {

    @POST
    @FormUrlEncoded
    suspend fun sendData(
        @Url url: String,
        @Field("exception") exception: String = "",
        @Field("android_version")
        androidVersion: String = "",
        @Field("app_version") appVersion: String = "",
        @Field("phoneName") phoneName: String = "",
        @Field("appName") appName: String = "",
        @Field("dateCrash") dateCrash: String = "",
        @Field("timeCrash") time: String = ""
    ): Response<LoginData>

    companion object {
        operator fun invoke(): ApiServices {
            return Retrofit.Builder()
                .baseUrl("http://192.168.1.53/AlirezaNasrollahzadeh/")
                .addConverterFactory(
                    GsonConverterFactory.create(
                        Gson().newBuilder().setLenient().create()
                    )
                )
                .build()
                .create(ApiServices::class.java)
        }
    }
}*/
