package com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.mobileKid

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.cv.movingbutton.MovingButton
import com.farad.entertainment.kidsanimalenglish.cv.movingbutton.enums.ButtonPosition
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.AnimalModel
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.getListData
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentCallRandomBinding
import com.farad.entertainment.kidsanimalenglish.utils.clearFlag
import com.farad.entertainment.kidsanimalenglish.utils.fullScreenFragment
import com.farad.entertainment.kidsanimalenglish.utils.loadImage
import com.farad.entertainment.kidsanimalenglish.utils.playSoundMediaPlayer
import com.farad.entertainment.kidsanimalenglish.utils.screenOn
import com.farad.entertainment.kidsanimalenglish.utils.shakeInfiniteRingingAnimation

class CallRandomAnimalFragment : BottomNavigationFragment<FragmentCallRandomBinding>() {
    private var mediaPlayer: MediaPlayer? = null
    private var positionBtn = ""

    private var randomAnimal: AnimalModel? = null

    private var isClickBack=false
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCallRandomBinding
        get() = FragmentCallRandomBinding::inflate

    override fun setup() {
        fullScreenFragment()
        screenOn()
        createRandomCAllAnimal()
        initMediaPlayer()
        listener()
        binding.imageBig.loadImage(R.drawable.back)
    }

    override fun onStart() {
        super.onStart()
        mediaPlayer?.start()
    }

    override fun onPause() {
        super.onPause()
        if (!isClickBack)
        mediaPlayer?.pause()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        clearFlag()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun createRandomCAllAnimal() {
        context?.getListData()?.let { listAnimal ->
            val number = (0 until listAnimal.size).random()
            randomAnimal = listAnimal[number]

        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun listener() {
        binding.movingButton.setOnTouchListener { view, motionEvent ->

            when (motionEvent.action) {
                MotionEvent.ACTION_UP -> {

                    if (positionBtn == "LEFT") {
                        binding.btnAnswerCall.visibility = View.INVISIBLE
                        binding.btnReject.visibility = View.INVISIBLE
                        binding.imgPhoneKidsMultiArrow.visibility = View.INVISIBLE
                        binding.movingButton.clearAnimation()
                        binding.movingButton.movementLeft = 1
                        binding.movingButton.setBackgroundResource(R.drawable.kids_phone_reject)
                        mediaPlayer?.playSoundMediaPlayer(
                            context,
                            randomAnimal?.soundAnimal,
                            isLoop = true
                        )
                        binding.movingButton.setOnClickListener {
                            isClickBack=true
                            mediaPlayer?.playSoundMediaPlayer(context, R.raw.phone_kids_hangup)

                            popBackStack()
                        }
                    } else if (positionBtn == "RIGHT") {
                        mediaPlayer?.playSoundMediaPlayer(context, R.raw.phone_kids_hangup)
                        isClickBack=true
                        popBackStack()
                    }
                }

                MotionEvent.ACTION_DOWN -> {

                }
            }
            return@setOnTouchListener false
        }
        binding.movingButton.onPositionChangedListener=object :MovingButton.OnPositionChangedListener{
            override fun onPositionChanged(action: Int, position: ButtonPosition?) {
                positionBtn = position.toString()
            }
        }
    }


    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer()
        binding.movingButton.shakeInfiniteRingingAnimation()
        randomAnimal?.imageGif?.let { binding.imgPhoneKidsRingPic.loadImage(it) }
        mediaPlayer?.playSoundMediaPlayer(context, R.raw.phone_kids_ring, isLoop = true)
    }
}