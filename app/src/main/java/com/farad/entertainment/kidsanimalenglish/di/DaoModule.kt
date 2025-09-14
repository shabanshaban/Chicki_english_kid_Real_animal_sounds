package com.farad.entertainment.kidsanimalenglish.di
import com.farad.entertainment.kidsanimalenglish.data.db.MainDatabase
import org.koin.dsl.module

val daoModule = module {

    single { get<MainDatabase>().wordGameDao() }
    single { get<MainDatabase>().scratchGameDao() }
    single { get<MainDatabase>().userInfoDao() }
    single { get<MainDatabase>().VideoYoutubeDao() }
}