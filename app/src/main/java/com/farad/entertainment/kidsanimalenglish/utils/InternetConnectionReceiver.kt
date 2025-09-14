package com.farad.entertainment.kidsanimalenglish.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.util.Calendar
import javax.net.SocketFactory


class InternetConnectionReceiver(val context: Context) : LiveData<Boolean>() {


    private var onStartTimer: (() -> Unit)? = null
    fun setonStartTimer(listener: () -> Unit) {
        onStartTimer = listener
    }

    var job: Job? = null
    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            onStartTimer?.invoke()



                isInternetReachable {
                    postValue(it)
                }


        }

    }
    
    private fun checkSocket(): Socket? {
        val socketFactory = SocketFactory.getDefault()
         val socket = socketFactory.createSocket()
         socket.connect(InetSocketAddress("8.8.8.8", 53), 5000)
         socket.close()
        return socket
    }
    fun isOnline(): Boolean {
        val t: Long = Calendar.getInstance().timeInMillis
        val runtime = Runtime.getRuntime()
        try {
            checkSocket()?.let {
            }
            /*Pinging to Google server*/
            val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
            val exitValue = ipProcess.waitFor()
            return exitValue == 0
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            val t2: Long = Calendar.getInstance().timeInMillis
            Log.i("NetWork check Time", (t2 - t).toString() + "")
        }
        return false
    }
    private fun isInternetReachable(action: (Boolean) -> Unit) {
        job?.cancel()
        job=null
        job = CoroutineScope(Dispatchers.IO).launch {

            action(isOnline())

            delay(5000)
            action(isOnline())
        }

    }


    override fun onActive() {
        super.onActive()
        context.registerReceiver(broadcastReceiver, IntentFilter(CONNECTIVITY_ACTION))
    }

    override fun onInactive() {
        super.onInactive()
        job?.cancel()
        context.unregisterReceiver(broadcastReceiver)
    }
}