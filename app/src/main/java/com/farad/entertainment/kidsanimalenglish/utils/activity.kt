package com.farad.entertainment.kidsanimalenglish.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun AppCompatActivity.lifecycleScopeDelayTryCatch(delay: Long, action: () -> Unit) {
    lifecycleScope.launch {
        delay(delay)
        try {
            action()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
fun AppCompatActivity.statusBarColorTransparent(){

       /* window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = Color.TRANSPARENT
        }*/


}
fun AppCompatActivity.setStatusBarIconsColor(isDark: Boolean) {

  /*      window.apply {
            WindowCompat.getInsetsController(this, this.decorView).apply {
                isAppearanceLightStatusBars = isDark
            }

    }*/

}