package com.farad.entertainment.kidsanimalenglish.utils

import android.annotation.SuppressLint
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

fun DialogFragment.safeDismiss() {
    try {
        if (isVisible || isAdded || isResumed)
            dismiss()
    }catch (e:Exception){
        e.printStackTrace()
    }

}

@SuppressLint("RestrictedApi")
fun DialogFragment.safeShow(manager: FragmentManager, tag: String? = null) {
    try {
        if (!isVisible && !isAdded && !isResumed&&manager.host.isNotNull() &&!isDetached)
            show(manager, tag)
    }catch (e:Exception){
        e.printStackTrace()
    }

}