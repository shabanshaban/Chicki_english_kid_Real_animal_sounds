package com.farad.entertainment.kidsanimalenglish.utils


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.provider.Settings
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BaseFragment
import com.farad.entertainment.kidsanimalenglish.cv.leonids.ParticleSystem
import com.permissionx.guolindev.PermissionX
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


fun Fragment.playAnimParticleSystem(view: View) {
    activity?.let {
        ParticleSystem(activity, 500, R.drawable.animated_confetti, 500)
            .setSpeedRange(0.1f, 0.2f)
            .setRotationSpeedRange(90f, 180f)
            .setInitialRotationRange(0, 360)
            .oneShot(view, 30)
        lifecycleScopeDelayTryCatch(500) {
            ParticleSystem(activity, 500, R.drawable.animated_confetti, 500)
                .setSpeedRange(0.1f, 0.2f)
                .setRotationSpeedRange(90f, 180f)
                .setInitialRotationRange(0, 360)
                .oneShot(view, 30)
        }


    }
}

fun Fragment.getAndroidIdUser() =
    Settings.Secure.getString(context?.contentResolver, Settings.Secure.ANDROID_ID)

fun Context.getAndroidIdUser() =
    Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

fun AppCompatActivity.getAndroidIdUser() =
    Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

fun <T : ViewBinding> BaseFragment<T>.delayTimerIsNullViewNot(delay: Long, action: () -> Unit) {
    lifecycleScope.launch {
        delay(delay)
        if (isNullView().not()) {
            try {

                action()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}

fun Fragment.delayTimer(delay: Long, action: () -> Unit) {
    lifecycleScope.launch {
        action()
        delay(delay)
        delayTimer(delay) {
            action()
        }
    }
}

fun AppCompatActivity.delayTimer(delay: Long, action: () -> Unit) {
    lifecycleScope.launch {
        action()
        delay(delay)
        delayTimer(delay) {
            action()
        }
    }
}

fun Fragment.getPermission(action: () -> Unit) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .request { allGranted, _, _ ->
                if (allGranted) {
                    action()
                } else {
                    toast(getString(R.string.these_permissions_are_denied))
                }
            }
    } else {
        action()
    }
}
fun AppCompatActivity.statusBarColor(
    @ColorRes color: Int,
    isAppearanceLightStatusBars: Boolean = false
) {


    try {
        val isAfterVanillaIceCream = Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = isAppearanceLightStatusBars
        insetsController.isAppearanceLightNavigationBars = isAppearanceLightStatusBars

        if (isAfterVanillaIceCream) {
            window?.decorView?.setOnApplyWindowInsetsListener { view, insets ->
                view.setBackgroundColor(getColorCompat(color))
                insets
            }
        } else {
            @Suppress("DEPRECATION")
            window.statusBarColor = getColorCompat(color)
            //  @Suppress("DEPRECATION")
            //   window.navigationBarColor = getColorCompat(statusBarColor)
        }
    }catch (e:Exception){
        e.fillInStackTrace()
    }


}
fun Activity.statusBarColor(@ColorRes color: Int) {
    /*   window.statusBarColor = ContextCompat.getColor(this, color)*/
}

fun Fragment.statusBarColor(@ColorRes color: Int) {
    /*  context?.let {
          activity?.window?.statusBarColor = ContextCompat.getColor(it, color)
      }*/

}

fun Activity.removeStatusBar() {
    window.setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.insetsController?.hide(WindowInsets.Type.statusBars())
    }
}

fun Fragment.showSnackBarMessage(
    message: String?,
    status: Status = Status.Error,
) {
    SimpleSnackBar.Builder(requireContext(), requireView(), layoutInflater)
        .type(status)
        .text(message)
        .build()
        .show()
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Fragment.statusBarColorTransparent() {
    activity?.apply {
        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = Color.TRANSPARENT
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        }
    }

}

fun Fragment.setStatusBarIconsColor(isDark: Boolean) {
    /*    activity?.apply {
            window.apply {
                WindowCompat.getInsetsController(this, this.decorView).apply {
                    isAppearanceLightStatusBars = isDark
                }
            }
        }*/

}

fun Fragment.screenOn() {
    activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
}

fun Fragment.fullScreenFragment() {
    /* activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)*/
}

fun Fragment.clearFlag() {
    /* requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)*/
}

@SuppressLint("SourceLockedOrientationActivity")
fun Fragment.changeScreenOrientation(landscape: Boolean) {
    if (landscape)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    else
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
}

fun Fragment.hideStatusBar() {
    requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        requireActivity().window.decorView.windowInsetsController?.setSystemBarsAppearance(
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
    }
}

fun Fragment.lifecycleScopeDelayTryCatch(delay: Long, action: () -> Unit) {
    lifecycleScope.launch {
        delay(delay)
        try {
            action()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun DialogFragment.lifecycleScopeDelayTryCatch(delay: Long, action: () -> Unit) {
    lifecycleScope.launch {
        delay(delay)
        try {
            action()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun Fragment.showStatusBar() {
    requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
}

fun Fragment.sensorScreenOrientation() {
    activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
}

fun Fragment.toast(text: String, duration: Int = Toast.LENGTH_SHORT) =
    context?.let { Toast.makeText(it, text, duration).show() }

fun Fragment.toast(@StringRes textId: Int, duration: Int = Toast.LENGTH_LONG) =
    context?.let { Toast.makeText(it, textId, duration).show() }


fun Fragment.getOrientation(): Int? {
    return activity?.resources?.configuration?.orientation
}

fun Fragment.hideSystemUI() {

    if (Build.VERSION.SDK_INT >= 30) {
        activity?.window?.insetsController?.apply {
            hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
        }
    } else {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        activity?.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}