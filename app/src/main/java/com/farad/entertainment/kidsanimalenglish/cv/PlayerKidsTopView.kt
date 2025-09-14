package com.farad.entertainment.kidsanimalenglish.cv

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.AnimalModel
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.getListData
import com.farad.entertainment.kidsanimalenglish.databinding.ViewPlayerTopBinding
import com.farad.entertainment.kidsanimalenglish.utils.animClick
import com.farad.entertainment.kidsanimalenglish.utils.animVibrate
import com.farad.entertainment.kidsanimalenglish.utils.getColorCompat
import com.farad.entertainment.kidsanimalenglish.utils.isNotNull
import com.farad.entertainment.kidsanimalenglish.utils.isNull
import com.farad.entertainment.kidsanimalenglish.utils.playMusicFadeOutScaleAnim
import com.farad.entertainment.kidsanimalenglish.utils.playSoundMediaPlayer
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.setTextColorCompat
import com.farad.entertainment.kidsanimalenglish.utils.setTintColor
import com.farad.entertainment.kidsanimalenglish.utils.toast
import com.farad.entertainment.kidsanimalenglish.utils.vibrateAnimByTimer


class PlayerKidsTopView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private val binding = ViewPlayerTopBinding.inflate(LayoutInflater.from(context), this, true)

    private var mediaPlayer: MediaPlayer? = null

    private var isPlayMusic = false
    private var isEmptyPlay = false
    private var counter = -1

      private  var listSound = ArrayList<AnimalModel>()

    private var onChangeListener: ((Int) -> Unit)? = null

    fun setOnChangeListener(listener: (Int) -> Unit) {
        onChangeListener = listener
    }

    init {
        initView()
        listener()

    }

    fun setColorItem(@ColorRes color: Int) {
        binding.imageNext.setTintColor(context.getColorCompat(color))
        binding.imagePrevious.setTintColor(context.getColorCompat(color))
        binding.tvCounterMusic.setTextColorCompat(color)
    }

    fun setLock(isEnabled: Boolean) {
        binding.imageNext.isEnabled = isEnabled
        binding.imagePrevious.isEnabled = isEnabled
    }

    fun destroyMediaPlayer() {
        try {
            mediaPlayer?.pause()
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun pauseMusic() {
        mediaPlayer?.pause()
        isPlayMusic = false
    }


      fun initPlayer(soundPlay:List<AnimalModel>) {

            listSound.clear()
            listSound.addAll(soundPlay)

          if (mediaPlayer.isNull()) {
              mediaPlayer = MediaPlayer()
          }
          initView()
    }



    fun playMusic() {
        if (isEmptyPlay){
            context.toast("First you have to unlock the animals")
        }else {
            binding.imagePlayMusic.playMusicFadeOutScaleAnim {
                binding.imagePlayMusic.vibrateAnimByTimer()
            }
            binding.layoutPlay.playMusicFadeOutScaleAnim { }
            if (counter == -1) {
                counter = 0
            }
            mediaPlayer?.playSoundMediaPlayer(binding.root.context, listSound[counter].soundPlay)
            mediaPlayer?.setOnPreparedListener { m ->
                mediaPlayer?.duration?.let { duration ->
                    isPlayMusic = true
                    checkPlay()
                }

                onChangeListener?.invoke(counter)
                setImageMusic()
            }
            checkCompletionMusic()
        }
    }

    private fun checkCompletionMusic() {
        mediaPlayer?.setOnCompletionListener {

            if (counter != -1) {
                nextMusic()

            }
        }
    }

    private fun nextMusic() {
        isPlayMusic = true

        if (counter == listSound.size - 1) {
            counter = 0
            val music = listSound[counter].soundPlay
            resetMediaPlayer()
            mediaPlayer?.playSoundMediaPlayer(context, music)
            binding.tvCounterMusic.text = "${(counter + 1)}/${listSound.size}"

        } else {
            counter++
            val music = listSound[counter].soundPlay
            mediaPlayer?.playSoundMediaPlayer(context, music, onPrepared = {
                mediaPlayer = it
            })
            binding.tvCounterMusic.text = "${(counter + 1)}/${listSound.size}"

        }
        binding.imagePlayMusic.playMusicFadeOutScaleAnim {
            binding.imagePlayMusic.vibrateAnimByTimer()
        }
        onChangeListener?.invoke(counter)
        checkCompletionMusic()
        setImageMusic()
    }

    private fun setImageMusic() {

        binding.imagePlayMusic.setImageResource(listSound[counter].image)


    }

    private fun previousMusic() {
        isPlayMusic = true
        counter--
        if (counter == -1) {
            counter = listSound.size - 1
            val music = listSound[counter].soundPlay
            resetMediaPlayer()
            mediaPlayer?.playSoundMediaPlayer(context, music)
            binding.tvCounterMusic.text = "${(counter + 1)}/${listSound.size}"

        } else {
            val music = listSound[counter].soundPlay
            resetMediaPlayer()
            mediaPlayer?.playSoundMediaPlayer(context, music)
            binding.tvCounterMusic.text = "${(counter + 1)}/${listSound.size}"
        }
        binding.imagePlayMusic.playMusicFadeOutScaleAnim {
            binding.imagePlayMusic.vibrateAnimByTimer()
        }
        setImageMusic()
        onChangeListener?.invoke(counter)
    }


    private fun resetMediaPlayer() {
        mediaPlayer?.pause()
        mediaPlayer?.reset()
    }

    private fun checkPlay() {
        if (isPlayMusic) {
            isPlayMusic = false
            mediaPlayer?.start()
        } else {
            isPlayMusic = true
            mediaPlayer?.pause()

        }
    }

    private fun initView() {
        binding.tvCounterMusic.text = "1/${listSound.size}"

    }

    private fun listener() {
        binding.imagePlayMusic.setOnSafeClickListener {
            it.animVibrate {
                it.playMusicFadeOutScaleAnim {
                    val music = listSound[counter].soundPlay
                    resetMediaPlayer()
                    mediaPlayer?.playSoundMediaPlayer(context, music)
                    it.vibrateAnimByTimer()
                }
            }


        }

        binding.imageNext.setOnSafeClickListener {
            it.animClick()
            nextMusic()
        }
        binding.imagePrevious.setOnSafeClickListener {
            previousMusic()
            it.animClick()
        }
        /* binding.imagePlay.setOnSafeClickListener {
             checkPlay()
         }*/

    }
}