package com.farad.entertainment.kidsanimalenglish.cv

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.databinding.ViewSoundBinding
import com.farad.entertainment.kidsanimalenglish.utils.animClickFast
import com.farad.entertainment.kidsanimalenglish.utils.gone
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.visible

class SoundVolumeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {
    private val binding = ViewSoundBinding.inflate(LayoutInflater.from(context), this, true)

    private var onChangeListener: ((Float) -> Unit)? = null

    private var isClose=false
    fun setOnChangeListener(listener: (Float) -> Unit) {
        onChangeListener = listener
    }


    init {
        listener()
    }

    fun setFistValue(value: Float) {
        binding.seekSound.value = value
        if (value == 0f) {
            binding.imageMute.setImageResource(R.drawable.button_mute_on)
        } else {
            binding.imageMute.setImageResource(R.drawable.button_mute_off)
        }
    }

    private fun listener() {


        binding.seekSound.addOnChangeListener { slider, value, fromUser ->
            onChangeListener?.invoke(value)
            if (value == 0f) {
                binding.imageMute.setImageResource(R.drawable.button_mute_on)
            } else {
                binding.imageMute.setImageResource(R.drawable.button_mute_off)
            }


        }
        binding.motionLayout.setTransitionListener(object :MotionLayout.TransitionListener{
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {

            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {

                if (!isClose){

                    binding.btnSound.setImageResource(R.drawable.ic_close)
                }else{
                    binding.btnSound.setImageResource(R.drawable.button_music)
                }
                isClose=!isClose


            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
            }

        })
        binding.btnSound.setOnSafeClickListener {
            it.animClickFast()
            if (binding.groupSlider.isVisible) {
                binding.groupSlider.gone(true)
                binding.btnSound.setImageResource(R.drawable.button_music)
            } else {
                binding.groupSlider.visible(true)
                binding.btnSound.setImageResource(R.drawable.ic_close)

            }



        }
    }


}