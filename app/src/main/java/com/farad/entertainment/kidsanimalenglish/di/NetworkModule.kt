package com.farad.entertainment.kidsanimalenglish.di

import com.farad.entertainment.kidsanimalenglish.data.apiService.ApiService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val networkModule = module {
   single { ApiService(get(),androidContext(),get()) }

}




