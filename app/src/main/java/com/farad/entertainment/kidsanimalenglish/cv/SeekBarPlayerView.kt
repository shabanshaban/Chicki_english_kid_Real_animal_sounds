package com.farad.entertainment.kidsanimalenglish.cv

import android.content.Context
import android.media.MediaPlayer
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.databinding.ViewSeekbarPlayerBinding
import com.farad.entertainment.kidsanimalenglish.utils.animClickFast
import com.farad.entertainment.kidsanimalenglish.utils.delayOnLifecycle
import com.farad.entertainment.kidsanimalenglish.utils.invisible
import com.farad.entertainment.kidsanimalenglish.utils.isNetworkAvailable
import com.farad.entertainment.kidsanimalenglish.utils.isNotNull
import com.farad.entertainment.kidsanimalenglish.utils.playSoundMediaPlayerUrl
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.toast
import com.farad.entertainment.kidsanimalenglish.utils.visible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

class SeekBarPlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {
    private var mediaPlayer: MediaPlayer? = null


    private var isFirstPlay = false
    private var isStatePlay = false


    private var timer: Timer? = null
    private val binding = ViewSeekbarPlayerBinding.inflate(LayoutInflater.from(context), this, true)


    private var onItemShareClickListener: (() -> Unit)? = null

    private var urlSound = ""


    fun setonItemShareClickListener(listener: () -> Unit) {
        onItemShareClickListener = listener
    }

    init {
        listener()
        initMediaPlayer()
    }


    fun setUrlSound(url:String){
        this.urlSound=url
    }

    private fun setTimer() {
        timer?.cancel()
        timer?.purge()
        timer = null
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                CoroutineScope(Dispatchers.Main).launch {
                    mediaPlayer?.currentPosition?.toFloat()?.let {
                        binding.seekSound.value = it
                        //  binding.seekSound.value = it.toInt().toFloat()
                    }
                }

            }

        }, 0, 500)


    }

    private fun initSeekBar(mediaPlayer: MediaPlayer) {
        isFirstPlay = true

        binding.seekSound.valueTo = mediaPlayer.duration.toFloat()

        setTimer()

        mediaPlayer.setOnCompletionListener {
            mediaPlayer.pause()
            timer?.cancel()
            binding.seekSound.value = 0f
            changeStatePlay()
        }


    }

    private fun changeStatePlay() {

        if (!isStatePlay) {
            binding.btnPlaySound.setImageResource(R.drawable.icon_pause_poem)
            if (mediaPlayer?.isPlaying == false)
                mediaPlayer?.start()

            setTimer()
        } else {
            binding.btnPlaySound.setImageResource(R.drawable.icon_play_poem)
            mediaPlayer?.pause()
        }

        isStatePlay = !isStatePlay
    }

    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer()
    }

    fun pause() {
        isStatePlay = true
        mediaPlayer?.pause()
        changeStatePlay()
    }

    private fun listener() {
        binding.seekSound.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                mediaPlayer?.seekTo(value.toInt())
            }

        }

        binding.btnPlaySound.setOnSafeClickListener {

            it.animClickFast()
            if (context.isNetworkAvailable()) {
                if (!isFirstPlay) {
                    binding.btnPlaySound.invisible(true)
                    binding.progressSound.visible(true)
                    mediaPlayer?.playSoundMediaPlayerUrl(
                        context,
                        urlSound,
                        onPrepared = { player ->
                            if (mediaPlayer.isNotNull()){
                            binding.btnPlaySound.visible(true)
                            binding.progressSound.invisible(true)
                            initSeekBar(player)
                            changeStatePlay()
                            }
                        })
                    delayOnLifecycle(15000)
                    {

                        if (binding.progressSound.isVisible){
                            binding.btnPlaySound.visible(true)
                            binding.progressSound.invisible(true)
                            context.toast(context.getString(R.string.please_try_again))
                        }
                    }

                } else {
                    changeStatePlay()
                }
            }else{
                context.toast(R.string.error_connect_net)
            }
        }

        binding.btnShareApp.setOnSafeClickListener {
            it.animClickFast()
            onItemShareClickListener?.invoke()
        }
    }

    fun destroyPlayer() {
        timer?.cancel()
        timer?.purge()
        timer = null
        mediaPlayer?.pause()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}