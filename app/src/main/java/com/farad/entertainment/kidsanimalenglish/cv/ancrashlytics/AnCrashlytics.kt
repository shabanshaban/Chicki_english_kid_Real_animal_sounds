/*
package com.farad.entertainment.kidsanimalenglish.cv.ancrashlytics

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import com.farad.entertainment.kidsanimalenglish.cv.ancrashlytics.services.ApiServices
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import java.util.Calendar


class AnCrashlytics constructor(val context: Context, val baseUrl: String) :
    Thread.UncaughtExceptionHandler {
    private val defaultExceptionHandler: Thread.UncaughtExceptionHandler? =
        Thread.getDefaultUncaughtExceptionHandler()

    @OptIn(DelicateCoroutinesApi::class)
    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        val request = ApiServices.invoke()
        GlobalScope.launch(newSingleThreadContext("IO")) {
            try {


                val deviceName = getNamePhone()

                val appVersion = getVersionName()


                request.sendData(
                    baseUrl,
                    exception = throwable.stackTraceToString() + "\n" + "Message : " + throwable.message,
                    androidVersion = Build.VERSION.RELEASE,
                    appVersion = appVersion,
                    phoneName = deviceName,
                    appName = context.packageName,
                    dateCrash = getDate(),
                    time = getTime()
                )
            } catch (e: Exception) {
                Log.e("sina", "Error: ${e.message}")
            }
            defaultExceptionHandler?.uncaughtException(thread, throwable)
        }
    }

    private fun getNamePhone(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            model
        } else {
            "$manufacturer $model"
        }
    }

    private fun getVersionName(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.PackageInfoFlags.of(0)
            ).versionName
        } else {
            context.packageManager.getPackageInfo(context.packageName, 0).versionName
        }
    }

    private fun getDate(): String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return "$year/$month/$day"
    }

    private fun getTime(): String {
        val calendar = Calendar.getInstance()
        var hour = calendar.get(Calendar.HOUR).toString()
        var minute = calendar.get(Calendar.MINUTE).toString()
        var second = calendar.get(Calendar.SECOND).toString()

        if (minute.length == 1) {
            minute = "0$minute"
        }
        if (hour.length == 1) {
            hour = "0$hour"
        }
        if (second.length == 1) {
            second = "0$second"
        }

        return "$hour:$minute:$second"
    }

    fun init() {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }
}

*/
