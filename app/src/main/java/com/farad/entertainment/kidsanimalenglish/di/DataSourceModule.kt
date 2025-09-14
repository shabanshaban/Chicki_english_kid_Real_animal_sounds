package com.farad.entertainment.kidsanimalenglish.di

import com.farad.entertainment.kidsanimalenglish.data.dataSource.DataSourceLocalWordGameHome
import com.farad.entertainment.kidsanimalenglish.data.dataSource.DataSourceLocalScratchGame
import com.farad.entertainment.kidsanimalenglish.data.dataSource.DataSourceLocalUserInfo
import com.farad.entertainment.kidsanimalenglish.data.dataSource.DataSourceLocalVideoYouTube
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val dataSourceModule = module {
    singleOf(::DataSourceLocalWordGameHome)
    singleOf(::DataSourceLocalScratchGame)
    singleOf(::DataSourceLocalUserInfo)
    singleOf(::DataSourceLocalVideoYouTube)
}