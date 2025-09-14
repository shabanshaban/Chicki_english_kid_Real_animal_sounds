package com.farad.entertainment.kidsanimalenglish.ui.fragment.home.soundGifAnimal

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.cv.lyricPlayer.DefaultLrcBuilder
import com.farad.entertainment.kidsanimalenglish.cv.lyricPlayer.ILrcView
import com.farad.entertainment.kidsanimalenglish.cv.lyricPlayer.LrcRow
import com.farad.entertainment.kidsanimalenglish.cv.lyricPlayer.LrcView
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.MarketName
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentSoundGifAnimalBinding
import com.farad.entertainment.kidsanimalenglish.utils.MARKET_NAME
import com.farad.entertainment.kidsanimalenglish.utils.expandFadeTxtMusicAnim
import com.farad.entertainment.kidsanimalenglish.utils.fadeInAnimation
import com.farad.entertainment.kidsanimalenglish.utils.gone
import com.farad.entertainment.kidsanimalenglish.utils.isNotNull
import com.farad.entertainment.kidsanimalenglish.utils.loadGif
import com.farad.entertainment.kidsanimalenglish.utils.playMusicFadeOutScaleAnim2
import com.farad.entertainment.kidsanimalenglish.utils.playSoundMediaPlayer
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.visibleOrGone
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask


class SoundGifAnimalFragment : BottomNavigationFragment<FragmentSoundGifAnimalBinding>() {


    private var mLrcView: LrcView? = null

    private var mediaPlayer: MediaPlayer? = null

    private var mTimer: Timer? = null
    private var mTask: TimerTask? = null

    private var isMute = false

    private val args by navArgs<SoundGifAnimalFragmentArgs>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSoundGifAnimalBinding
        get() = FragmentSoundGifAnimalBinding::inflate

    override fun setup() {
        mediaPlayer = MediaPlayer()
        initGif()
        checkTextSound()
        binding.btnSound.visibleOrGone(args.animalModel.soundPlay.isNotNull())
    }


    private fun checkTextSound() {
        if (args.animalModel.soundPlay.isNotNull()) {

            initMusic()
            listener()
        } else {
            binding.lnrTxtLrc.gone()
            mediaPlayer = MediaPlayer()
            mediaPlayer?.playSoundMediaPlayer(context, args.animalModel.soundNameEnglish)
        }
    }

    private fun listener() {
        binding.btnSound.setOnSafeClickListener {
            if (isMute) {

                mediaPlayer?.setVolume(1f, 1f)
                binding.imageSound.setImageResource(R.drawable.ic_sound_new)
            } else {
                mediaPlayer?.setVolume(0f, 0f)
                binding.imageSound.setImageResource(R.drawable.no_sound)
            }
            isMute = !isMute
        }
        binding.viewClickGif.setOnSafeClickListener {
            clickGif()
        }
    }

    private fun clickGif() {
        binding.imageGifAnimal.fadeInAnimation()
        if (binding.txtLrcLinear.visibility == View.VISIBLE) {
            binding.txtLrcLinear.visibility = View.GONE
            binding.lrcView.visibility = View.VISIBLE
        } else {
            binding.txtLrcLinear.visibility = View.VISIBLE
            binding.lrcView.visibility = View.GONE
            binding.txtLrcLinear.expandFadeTxtMusicAnim()
        }
    }

    override fun onStop() {
        super.onStop()

        if (MARKET_NAME == MarketName.GALAXY_STORE) {
            popBackStack()
        }
    }

    override fun onDestroyView() {
        mediaPlayer?.pause()
        mediaPlayer?.release()
        mediaPlayer = null
        mTimer?.cancel()
        mTimer?.purge()
        mTimer = null
        mTask?.cancel()
        mTask = null
        super.onDestroyView()

    }

    private fun initGif() {



        binding.imageGifAnimal.loadGif(args.animalModel.imageGif)
        binding.tvTile.text = args.animalModel.title
    }

    private fun initMusic() {
        mediaPlayer?.playSoundMediaPlayer(
            context,
            args.animalModel.soundPlay,
            countLoop = 2,
            onPrepared = {
                if (mTimer == null) {
                    mTimer = Timer()
                    initLrcView()
                    mTask = LrcTask()
                    mTimer?.schedule(mTask, 0, 1000)
                }
            },
            onCompletion = {

            },
            onEndLoop = {
                if (binding.txtLrcLinear.visibility == View.VISIBLE) {
                    popBackStack()
                } else {
                    initMusic()
                }
                stopLrcPlay()
            })
    }

    private fun initLrcView() {
        mLrcView = LrcView(requireActivity(), null)
        mLrcView = binding.lrcView
        val lrc = args.animalModel.textLrcItem

        lrc.let {
            val builder = DefaultLrcBuilder()
            val rows = builder.getLrcRows(lrc)
            mLrcView?.setLrc(rows)
        }





        mLrcView?.setListener(object : ILrcView.LrcViewListener {
            override fun onLrcSeeked(newPosition: Int, row: LrcRow) {
                mediaPlayer?.seekTo(row.time.toInt())
            }

        })

    }

    private fun stopLrcPlay() {
        if (mTimer != null) {
            mTimer?.cancel()
            mTimer = null
        }
    }

    inner class LrcTask : TimerTask() {
        private var beginTime: Long = -1
        private var textBefore = ""
        override fun run() {

            if (isNullView().not()) {
                lifecycleScope.launch {

                    try {
                        mediaPlayer?.let { mPlayer ->
                            if (beginTime == -1L) {
                                beginTime = System.currentTimeMillis()
                            }
                            val timePassed = mPlayer.currentPosition.toLong()
                            mLrcView?.seekLrcToTime(timePassed)
                            textBefore = binding.txtLrcLinear.text.toString()
                            binding.txtLrcLinear.text = mLrcView?.currentLrc.toString()


                            if (textBefore != binding.txtLrcLinear.text) {
                                if (binding.txtLrcLinear.visibility == View.VISIBLE) {
                                    binding.txtLrcLinear.playMusicFadeOutScaleAnim2()
                                }
                            }
                        }
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }

                }

            }

        }
    }
}