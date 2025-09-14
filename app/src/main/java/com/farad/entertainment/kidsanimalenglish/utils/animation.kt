package com.farad.entertainment.kidsanimalenglish.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.LinearGradient
import android.graphics.Shader
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.farad.entertainment.kidsanimalenglish.R


fun View.enterJumpSlowAnimation() {
    val enterJumpSlow = AnimationUtils.loadAnimation(context, R.anim.enter_jump_slow)
    startAnimation(enterJumpSlow)
}

fun View.scaleAnim2() {
    val enterJumpSlow = AnimationUtils.loadAnimation(context, R.anim.scale_anim2)
    startAnimation(enterJumpSlow)
}

fun View.alphaRepeat() {
    val alphaRepeat = AnimationUtils.loadAnimation(context, R.anim.alpha_repeat)
    startAnimation(alphaRepeat)
}

fun View.expandReverseFoodAnim() {
    val expandReverseFoodAnim =
        AnimationUtils.loadAnimation(context, R.anim.expand_reverse_animal_food)
    startAnimation(expandReverseFoodAnim)
}

fun View.scaleAnimation(endAnim: (() -> Unit)? = null) {
    val animScaleBigger = ScaleAnimation(
        0f,
        1f,
        0f,
        1f,
        Animation.RELATIVE_TO_SELF,
        0.5f,
        Animation.RELATIVE_TO_SELF,
        0.5f
    )
    animScaleBigger.fillAfter = true
    animScaleBigger.duration = 1000
    animScaleBigger.setAnimationListener(object : AnimationListener {
        override fun onAnimationStart(p0: Animation?) {

        }

        override fun onAnimationEnd(p0: Animation?) {
            endAnim?.let { it() }
        }

        override fun onAnimationRepeat(p0: Animation?) {
        }

    })
    startAnimation(animScaleBigger)
}

fun View.zoomAnimation(durationAnim: Int = 5000) {
    val scaleAnimation = ScaleAnimation(
        0f,
        1.0f,
        0f,
        1.0f,
        Animation.RELATIVE_TO_SELF,
        .5f,
        Animation.RELATIVE_TO_SELF,
        .5f
    )

    scaleAnimation.duration = durationAnim.toLong()
    scaleAnimation.fillAfter = true

    startAnimation(scaleAnimation)
}

fun View.shakeAnimation2() {
    val shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.vibrate_anim2)
    this.startAnimation(shakeAnimation)
}

fun View.shakeAnimation() {
    val shakeAnimation = AnimationUtils.loadAnimation(context, R.anim.shake)
    this.startAnimation(shakeAnimation)
}

fun View.fadeInAnimation() {
    val animation = AnimationUtils.loadAnimation(context, R.anim.fade_in_longer)
    startAnimation(animation)
}

fun View.expandFadeTxtMusicAnim() {
    val animation = AnimationUtils.loadAnimation(context, R.anim.expand_fade_txt_music_anim)
    startAnimation(animation)
}

fun View.playMusicFadeOutScaleAnim2(endAnim: (() -> Unit)? = null) {
    val fadeOutScaleAnim2 =
        AnimationUtils.loadAnimation(context, R.anim.play_music_fade_out_scale_anim2)
    startAnimation(fadeOutScaleAnim2)
    fadeOutScaleAnim2.setAnimationListener(object : AnimationListener {
        override fun onAnimationStart(p0: Animation?) {

        }

        override fun onAnimationEnd(p0: Animation?) {
            endAnim?.let { it() }
        }

        override fun onAnimationRepeat(p0: Animation?) {
        }

    })
}

fun View.animZoomInZoomOut() {
    val zoomIn = AnimationUtils.loadAnimation(context, R.anim.zoom_in_anim)
    val zoomOut = AnimationUtils.loadAnimation(context, R.anim.zoom_out_anim)
    startAnimation(zoomIn)
    zoomIn.setAnimationListener(object : AnimationListener {
        override fun onAnimationStart(p0: Animation?) {

        }

        override fun onAnimationEnd(p0: Animation?) {
            startAnimation(zoomOut)
        }

        override fun onAnimationRepeat(p0: Animation?) {
        }

    })
}

fun View.animClickFast(endAnim: (() -> Unit)? = null) {
    val animClickFast = AnimationUtils.loadAnimation(context, R.anim.click_anim_fast)
    animClickFast.setAnimationListener(object : AnimationListener {
        override fun onAnimationStart(p0: Animation?) {

        }

        override fun onAnimationEnd(p0: Animation?) {
            endAnim?.let { it() }
        }

        override fun onAnimationRepeat(p0: Animation?) {

        }

    })
    startAnimation(animClickFast)
}

fun View.animClick(endAnim: (() -> Unit)? = null) {
    val animClickFast = AnimationUtils.loadAnimation(context, R.anim.click_anim)
    animClickFast.setAnimationListener(object : AnimationListener {
        override fun onAnimationStart(p0: Animation?) {

        }

        override fun onAnimationEnd(p0: Animation?) {
            endAnim?.let { it() }
        }

        override fun onAnimationRepeat(p0: Animation?) {

        }

    })
    startAnimation(animClickFast)
}

fun View.shakeInfiniteRingingAnimation(endAnim: (() -> Unit)? = null) {
    val animClickFast = AnimationUtils.loadAnimation(context, R.anim.shake_infinite_ringing)
    animClickFast.setAnimationListener(object : AnimationListener {
        override fun onAnimationStart(p0: Animation?) {

        }

        override fun onAnimationEnd(p0: Animation?) {
            endAnim?.let { it() }
        }

        override fun onAnimationRepeat(p0: Animation?) {

        }

    })
    startAnimation(animClickFast)
}

fun View.dropAnim(endAnim: (() -> Unit)? = null) {
    val dropAnim = AnimationUtils.loadAnimation(context, R.anim.drop_anim)
    dropAnim.setAnimationListener(object : AnimationListener {
        override fun onAnimationStart(p0: Animation?) {

        }

        override fun onAnimationEnd(p0: Animation?) {
            endAnim?.let { it() }
        }

        override fun onAnimationRepeat(p0: Animation?) {
        }

    })
    startAnimation(dropAnim)
}

fun ImageView.shimmerAnim(){


}

fun View.animFadeInfinite() {
    val fadeIn = ObjectAnimator.ofFloat(this, "alpha", 0.7f)
    val fadeOut = ObjectAnimator.ofFloat(this, "alpha", 1.0f)
    fadeIn.duration = 800
    fadeOut.duration = 800
    fadeIn.repeatCount = ObjectAnimator.INFINITE
    fadeOut.repeatCount = ObjectAnimator.INFINITE


    val scaleUp = AnimatorSet()
    scaleUp.playTogether(   fadeIn)

    val scaleDown = AnimatorSet()
    scaleDown.playTogether(  fadeOut)

    val animatorSet = AnimatorSet()
    animatorSet.playSequentially(scaleUp, scaleDown)
    animatorSet.interpolator = DecelerateInterpolator()
    animatorSet.startDelay = 800
    animatorSet.start()
}

fun View.animVibrate(endAnim: (() -> Unit)? = null) {
    val vibrateAnim = AnimationUtils.loadAnimation(context, R.anim.vibrate_anim)
    vibrateAnim.setAnimationListener(object : AnimationListener {
        override fun onAnimationStart(p0: Animation?) {

        }

        override fun onAnimationEnd(p0: Animation?) {
            endAnim?.let { it() }
        }

        override fun onAnimationRepeat(p0: Animation?) {
        }

    })
    startAnimation(vibrateAnim)
}

fun View.playMusicFadeOutScaleAnim(endAnim: (() -> Unit)? = null) {
    val vibrateAnim = AnimationUtils.loadAnimation(context, R.anim.play_music_fade_out_scale_anim)
    startAnimation(vibrateAnim)
    vibrateAnim.setAnimationListener(object : AnimationListener {
        override fun onAnimationStart(p0: Animation?) {

        }

        override fun onAnimationEnd(p0: Animation?) {
            endAnim?.let { it() }
        }

        override fun onAnimationRepeat(p0: Animation?) {
        }

    })

}

fun View.vibrateAnimByTimer() {
    val vibrateAnim = AnimationUtils.loadAnimation(context, R.anim.vibrate_anim_by_timer)

    vibrateAnim.setAnimationListener(object : AnimationListener {
        override fun onAnimationStart(p0: Animation?) {

        }

        override fun onAnimationEnd(p0: Animation?) {
            // Toast.makeText(context, "onAnimationEnd", Toast.LENGTH_SHORT).show()
            //  endAnim?.let { it() }
        }

        override fun onAnimationRepeat(p0: Animation?) {
        }

    })
    startAnimation(vibrateAnim)
}

fun View.setTranslateAnimView(x: Float, y: Float, duration: Int, action: () -> Unit) {
    ObjectAnimator
        .ofFloat(this, View.TRANSLATION_X, this.translationX, x).apply {

            addUpdateListener {
                action()
            }

        }
        .setDuration(duration.toLong())
        .start()

    ObjectAnimator
        .ofFloat(this, View.TRANSLATION_Y, this.translationY, y)
        .setDuration(duration.toLong())
        .start()
}