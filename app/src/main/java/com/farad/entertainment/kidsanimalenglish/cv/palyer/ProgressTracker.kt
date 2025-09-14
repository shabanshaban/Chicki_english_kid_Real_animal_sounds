package com.farad.entertainment.kidsanimalenglish.cv.palyer

import android.os.Handler
import android.os.Looper

class ProgressTracker(private val positionListener: () -> Unit) :
    Runnable {

    private val handler: Handler = Handler(Looper.getMainLooper())
    var start: Boolean = false
        set(value) {
            field = value
            if (value)
                handler.post(this)
            else
                handler.removeCallbacks(this)
        }

    override fun run() {
        positionListener.invoke()
        handler.postDelayed(this, 200)
    }
}