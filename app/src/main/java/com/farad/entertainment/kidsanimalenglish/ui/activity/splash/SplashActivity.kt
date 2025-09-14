package com.farad.entertainment.kidsanimalenglish.ui.activity.splash

import android.annotation.SuppressLint
import android.net.Uri
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BaseActivity
import com.farad.entertainment.kidsanimalenglish.base.navigation.main.MainNavigator
import com.farad.entertainment.kidsanimalenglish.databinding.ActivitySplashBinding
import com.farad.entertainment.kidsanimalenglish.ui.activity.main.MainActivity
import com.farad.entertainment.kidsanimalenglish.utils.enterJumpSlowAnimation
import com.farad.entertainment.kidsanimalenglish.utils.gone
import com.farad.entertainment.kidsanimalenglish.utils.lifecycleScopeDelayTryCatch
import com.farad.entertainment.kidsanimalenglish.utils.openActivity


@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>(), MainNavigator {

    override fun getBindingView() = ActivitySplashBinding.inflate(layoutInflater)


    override fun afterCreateView() {
        super.afterCreateView()

        try {
            binding.tvTitleSplash.enterJumpSlowAnimation()

            lifecycleScopeDelayTryCatch(0) {
                goHome()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    override fun onStart() {
        super.onStart()
        playLogoSplash()
    }

    private fun playLogoSplash() {

        try {
            val path = "android.resource://" + packageName + "/" + R.raw.logo_splash
            binding.videoPlayer.apply {
                requestFocus()
                setVideoURI(Uri.parse(path))
                setOnPreparedListener {
                    start()
                    lifecycleScopeDelayTryCatch(300){
                        binding.frameVideo.gone()
                    }

                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun goHome() {
        openActivity<MainActivity>()
        finish()
    }


}