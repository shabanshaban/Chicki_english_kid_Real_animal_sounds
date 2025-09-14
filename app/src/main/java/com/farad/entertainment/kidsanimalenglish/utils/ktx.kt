package com.farad.entertainment.kidsanimalenglish.utils

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.net.Uri
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.FileProvider
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.datasource.DataSource
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target

import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.farad.entertainment.kidsanimalenglish.base.BaseActivity
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.MarketName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Locale
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.TimeUnit
import kotlin.math.abs

fun getDurationString(seconds: Int): String {
    val hours = seconds / 3600
    val minutes = seconds % 3600 / 60
    val newSeconds = seconds % 60




    return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : " + twoDigitString(
        newSeconds
    )
}

fun checkLanguage(farsi:()->Unit, english:()->Unit){
    when (MARKET_NAME)
    {
        MarketName.CAFE_BAZAAR,
        MarketName.CHAR_KHUNE,
        MarketName.MYKET,
        -> {
            farsi()
        }
        MarketName.GALAXY_STORE ,
        MarketName.GOOGLE_PLY -> {
            english()
        }
    }
}
fun AppCompatImageView.setBackgroundTint(color: Int) {
    backgroundTintList = ColorStateList.valueOf(color)
}

fun AdView.initBannerStandard(loadBanner: (() -> Unit)? = null) {
    context?.let {
        var mAdView = AdView(it)
        mAdView.setAdSize(AdSize.BANNER)
        mAdView.adUnitId = BANNER_STANDARD_ID
        mAdView = this
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }
    adListener = object : AdListener() {
        override fun onAdClicked() {
            // toast("onAdClicked" )
        }

        override fun onAdClosed() {
            // toast("onAdClosed" )
        }

        override fun onAdFailedToLoad(adError: LoadAdError) {
            //toast("onAdImpression"+adError.message)
        }

        override fun onAdImpression() {
            //  toast("onAdImpression")
        }

        override fun onAdLoaded() {
            loadBanner?.let { it() }
        }

        override fun onAdOpened() {
            //  toast("onAdOpened")
        }
    }
}

private fun twoDigitString(number: Int): String {
    if (number == 0) {
        return "00"
    }
    return if (number / 10 == 0) {
        "0$number"
    } else number.toString()
}

fun Long.toFormatTime(): String {
    var sec = this / 1000
    val mint = sec / 60
    sec %= 60
    return " " + String.format(
        Locale.ENGLISH, "%02d", sec
    ) + " : " + String.format(
        Locale.ENGLISH, "%02d", mint
    )
}

fun SoundPool.loadSoundPool(context: Context?, nameSound: String): Int {
    return load(context?.assets?.openFd(nameSound), 1)
}

fun SoundPool.loadSoundPoolRaw(context: Context, idSoundRaw: Int): Int {
    return load(context, idSoundRaw, 1)
}

fun SoundPool.playSound(soundDice: Int) {
    play(soundDice, 1f, 1f, 1, 0, 1f)

}

fun AppCompatActivity.getNavHostFragment(id: Int): NavHostFragment? {
    return supportFragmentManager.findFragmentById(id) as? NavHostFragment
}

fun Bitmap.getLocalBitmapUri(appId: String, context: Context): Uri? {
    var bmpUri: Uri? = null
    val authority = "$appId.provider"
    try {
        val file = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "ghooghooli_" + System.currentTimeMillis() + ".png"
        )
        val out = FileOutputStream(file)
        compress(Bitmap.CompressFormat.PNG, 90, out)
        out.close()
        bmpUri = FileProvider.getUriForFile(
            context.applicationContext, authority, file
        )
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return bmpUri
}


fun AppCompatImageView.loadGif(any: Any) {

    try {
        Glide.with(context)
            .asGif()
            .load(any)
            .apply(
                RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL)
            )
            .into(this)
    }catch (e:Exception){
        e.printStackTrace()
    }

}


fun ImageView.loadGlide(imageUrl: String, finishLoading: () -> Unit) {
    try {
        Glide.with(context).load(imageUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: com.bumptech.glide.load.DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    try {

                        finishLoading()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    return false
                }

            })
            .transition(DrawableTransitionOptions.withCrossFade(1000)).into(this)
    }catch (e:Exception){
        e.printStackTrace()
    }

}

fun ImageView.loadImage(any: Any, isCache: Boolean = false) {

    try {
        if (isCache) {
            Glide.with(context)
                .load(any)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .transition(DrawableTransitionOptions.withCrossFade(1000))
                .into(this)
        } else {
            Glide.with(context)
                .load(any)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(this)
        }
    }catch (e:Exception){
        e.printStackTrace()
    }



}

inline fun <reified T : BaseActivity<*>> Context.openActivity(block: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    block(intent)
    startActivity(intent)
}


fun sinaLog(message: Any) {
    // Log.e("shaban", "sinaLog: $message")
}


fun MediaPlayer.playSoundMediaPlayer(
    context: Context?,
    idSound: Int?,
    countLoop: Int = 0,
    isLoop: Boolean = false,
    onPrepared: ((MediaPlayer) -> Unit)? = null,
    onEndLoop: (() -> Unit)? = null,
    onCompletion: ((MediaPlayer) -> Unit)? = null
): MediaPlayer {


    try {
        context?.let {
            idSound?.let {

                pause()
                reset()
                var counter = 1
                setDataSource(
                    context.applicationContext,
                    Uri.parse("android.resource://${context.packageName}/${idSound}")
                )
                this.setOnCompletionListener {
                    if (!isLoop) {
                        onCompletion?.let { it1 -> it1(it) }
                        counter++
                        if (countLoop > 0) {
                            if (counter <= countLoop) {
                                start()
                            } else {
                                pause()
                                onEndLoop?.let { it1 -> it1() }
                            }
                        }
                    }
                }
                this.setOnPreparedListener {
                    onPrepared?.let { it1 -> it1(it) }
                }
                prepare()
                start()
            }
        }
        this.isLooping = isLoop
    }catch (e:Exception){
        e.printStackTrace()
    }

    return this
}

fun View.setBackGround(@DrawableRes image: Int) {
    Glide.with(context)
        .load(image)
        .into(object : CustomTarget<Drawable?>() {
            override fun onResourceReady(
                resource: Drawable,
                transition: com.bumptech.glide.request.transition.Transition<in Drawable?>?
            ) {
                try {
                    background = resource
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
}

fun MediaPlayer.playSoundMediaPlayerUrl(
    context: Context?,
    idSound: String?,
    countLoop: Int = 0,
    isLoop: Boolean = false,
    onPrepared: ((MediaPlayer) -> Unit)? = null,
    onEndLoop: (() -> Unit)? = null,
    onCompletion: ((MediaPlayer) -> Unit)? = null
): MediaPlayer {


    context?.let {
        idSound?.let {
            pause()
            reset()
            var counter = 1

            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
            setAudioAttributes(audioAttributes)
            setDataSource(idSound)
            prepareAsync()

            this@playSoundMediaPlayerUrl.setOnCompletionListener {
                if (!isLoop) {
                    onCompletion?.let { it1 -> it1(it) }
                    counter++
                    if (countLoop > 0) {
                        if (counter <= countLoop) {

                            start()
                        } else {
                            pause()
                            onEndLoop?.let { it1 -> it1() }
                        }
                    }
                }
            }
            this@playSoundMediaPlayerUrl.setOnPreparedListener {
                start()
                onPrepared?.let { it1 -> it1(it) }
            }


        }
    }
    this.isLooping = isLoop
    return this
}


fun Timer.startTimer( delay:Long=0,period:Long,action:()->Unit){
     schedule(object : TimerTask() {
        override fun run() {
            try {
                action()
            }catch (e:Exception){
                e.printStackTrace()
            }

        }

    }, delay, period)
}

fun ViewPager2.setPageTransformerAnim() {
    val MIN_SCALE = 0.5f
    val MIN_ALPHA = 0.5f
    val transformer = ViewPager2.PageTransformer { page, position ->
        val pageWidth = page.width
        val pageHeight = page.height


        when {
            position < -1 -> {
                page.alpha = 0f
            }

            position <= 1 -> {
                val scaleFactor = MIN_SCALE.coerceAtLeast(1 - abs(position))
                val vertMargin = pageHeight * (1 - scaleFactor) / 2
                val horzMargin = pageWidth * (1 - scaleFactor) / 2
                if (position < 0) {
                    page.translationX = horzMargin - vertMargin / 2
                } else {
                    page.translationX = -horzMargin + vertMargin / 2
                }

                page.scaleX = scaleFactor
                page.scaleY = scaleFactor

                page.alpha =
                    (MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA))
            }

            else -> {
                page.alpha = 0f
            }
        }
    }

    setPageTransformer(transformer)
}



fun <T> debounce(
    duration: Int = 500,
    timeUnit: TimeUnit = TimeUnit.MILLISECONDS,
    coroutineScope: CoroutineScope,
    destinationFunction: (T) -> Unit,
): (T) -> Unit {
    val timeMilli = timeUnit.toMillis(duration.toLong())
    var debounceJob: Job? = null
    return { param: T ->
        debounceJob?.cancel()
        debounceJob = coroutineScope.launch {
            delay(timeMilli)
            destinationFunction(param)
        }
    }
}