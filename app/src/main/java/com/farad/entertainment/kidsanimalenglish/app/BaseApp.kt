package com.farad.entertainment.kidsanimalenglish.app

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.MarketName
import com.farad.entertainment.kidsanimalenglish.di.appModule
import com.farad.entertainment.kidsanimalenglish.di.daoModule
import com.farad.entertainment.kidsanimalenglish.di.dataSourceModule
import com.farad.entertainment.kidsanimalenglish.di.dbModule
import com.farad.entertainment.kidsanimalenglish.di.networkModule
import com.farad.entertainment.kidsanimalenglish.di.repositoryModule
import com.farad.entertainment.kidsanimalenglish.di.restModule
import com.farad.entertainment.kidsanimalenglish.di.viewModelModule
import com.farad.entertainment.kidsanimalenglish.utils.APP_NUMBER
import com.farad.entertainment.kidsanimalenglish.utils.MARKET_NAME
import com.farad.entertainment.kidsanimalenglish.utils.setLocaleApp
import com.google.android.gms.ads.MobileAds
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.io.File


@UnstableApi
class BaseApp : Application() {
    companion object {
        lateinit var simpleCache: SimpleCache
        lateinit var leastRecentlyUsedCacheEvictor: LeastRecentlyUsedCacheEvictor
        lateinit var standaloneDatabaseProvider: StandaloneDatabaseProvider
        private const val exoCacheSize: Long = 100 * 1024 * 1024
    }

    override fun onCreate() {
        super.onCreate()
        initModule()
        initAds()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        chooseMarket()
        // AnCrashlytics(this,baseUrl = "https://androidkurd.ir/Crash/getInfo.php").init()
        applicationContext.setLocaleApp()

        initCashExo()
    }

    private fun initCashExo() {
        leastRecentlyUsedCacheEvictor = LeastRecentlyUsedCacheEvictor(exoCacheSize)
        standaloneDatabaseProvider = StandaloneDatabaseProvider(this)
        simpleCache = SimpleCache(
            File(this.cacheDir, "media"),
            leastRecentlyUsedCacheEvictor,
            standaloneDatabaseProvider
        )
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase.setLocaleApp())
    }

    private fun chooseMarket() {

        when (MARKET_NAME) {
            MarketName.CAFE_BAZAAR -> {
                APP_NUMBER = 34
            }

            MarketName.MYKET -> {
                APP_NUMBER = 134
            }

            MarketName.CHAR_KHUNE -> {
                APP_NUMBER = 100
            }


            MarketName.GOOGLE_PLY -> {
                APP_NUMBER = 534
            }

            else -> {
                APP_NUMBER = 450
            }


        }

    }


    private fun initAds() {
        try {
            MobileAds.initialize(applicationContext) {}
        }catch (e:Exception){
            e.fillInStackTrace()
        }

    }

    private fun initModule() {
        startKoin {
            androidLogger()
            androidContext(this@BaseApp)
            modules(
                appModule,
                dbModule,
                daoModule,
                networkModule,
                repositoryModule,
                dataSourceModule,
                restModule,
                viewModelModule,
                module {
                    single { this@BaseApp.contentResolver }
                }
            )
        }
    }

}