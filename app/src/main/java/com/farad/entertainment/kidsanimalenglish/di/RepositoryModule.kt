package com.farad.entertainment.kidsanimalenglish.di


import com.farad.entertainment.kidsanimalenglish.data.repository.GameRepository
import com.farad.entertainment.kidsanimalenglish.data.repository.GameRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.core.module.dsl.bind
val repositoryModule = module {


    singleOf(::GameRepositoryImpl) { bind<GameRepository>() }

}