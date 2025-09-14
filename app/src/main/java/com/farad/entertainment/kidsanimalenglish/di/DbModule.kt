package com.farad.entertainment.kidsanimalenglish.di


import androidx.preference.PreferenceManager
import androidx.room.Room
import com.farad.entertainment.kidsanimalenglish.data.db.MainDatabase
import com.farad.entertainment.kidsanimalenglish.data.manager.SharedPreferencesManager
import com.farad.entertainment.kidsanimalenglish.data.model.MIGRATION_1_2
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dbModule = module {

    single { PreferenceManager.getDefaultSharedPreferences(androidApplication()) }
    singleOf(::SharedPreferencesManager)

    single {
        Room.databaseBuilder(androidApplication(), MainDatabase::class.java, "animalDB")
            .addMigrations(MIGRATION_1_2)
            .build()
    }

}