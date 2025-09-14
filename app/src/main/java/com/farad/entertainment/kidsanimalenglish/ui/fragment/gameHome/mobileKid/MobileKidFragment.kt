package com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.mobileKid

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.AnimalModel
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.getListData
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentMobileKidBinding
import com.farad.entertainment.kidsanimalenglish.utils.animClick
import com.farad.entertainment.kidsanimalenglish.utils.getMusicInRawByName
import com.farad.entertainment.kidsanimalenglish.utils.isNotNull
import com.farad.entertainment.kidsanimalenglish.utils.loadGif
import com.farad.entertainment.kidsanimalenglish.utils.playSoundMediaPlayer
import com.farad.entertainment.kidsanimalenglish.utils.screenOn
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

class MobileKidFragment : BottomNavigationFragment<FragmentMobileKidBinding>() {

    private var mediaPlayer: MediaPlayer? = null
    private var isAnimalMode = false

    private val listNumberRandom = ArrayList<Int>()
    private val listAllAnimal = ArrayList<AnimalModel>()
    var timer: Timer? = null
    private var counter = 0
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMobileKidBinding
        get() = FragmentMobileKidBinding::inflate

    override fun setup() {
        screenOn()
        initMediaPlayer()
        listener()
    }

    override fun onPause() {
        super.onPause()
        timer?.cancel()
        mediaPlayer?.pause()
    }

    override fun onStart() {
        super.onStart()
        setTimer()
    }

    private fun setTimer() {
        timer?.cancel()
        timer?.purge()
        timer = null
        timer = Timer()
        counter = 0
        timer?.schedule(object : TimerTask() {
            override fun run() {
                lifecycleScope.launch {
                    if (isNullView().not() && isAnimalMode.not()) {
                        counter++

                        if (counter == 10) {
                            counter = 0
                            val navigate =
                                MobileKidFragmentDirections.actionMobileKidFragmentToCallRandomAnimal()
                            navigate(navigate)

                        }
                    }
                }

            }

        }, 1000, 1000)

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun listener() {
        binding.txtPhoneKidsNumScreen.setOnClickListener {
            binding.txtPhoneKidsNumScreen.text = ""

        }
        binding.touchView.setOnTouchListener { view, motionEvent ->
            counter = 0
            false
        }
        listAllAnimal.clear()
        context?.getListData()?.filter { it.soundPlay.isNotNull() }?.let {
            listAllAnimal.addAll(it)
        }
        binding.imageNumber0.setOnSafeClickListener { clickNumber(it) }
        binding.imageNumber1.setOnSafeClickListener { clickNumber(it) }
        binding.imageNumber2.setOnSafeClickListener { clickNumber(it) }
        binding.imageNumber3.setOnSafeClickListener { clickNumber(it) }
        binding.imageNumber4.setOnSafeClickListener { clickNumber(it) }
        binding.imageNumber5.setOnSafeClickListener { clickNumber(it) }
        binding.imageNumber6.setOnSafeClickListener { clickNumber(it) }
        binding.imageNumber7.setOnSafeClickListener { clickNumber(it) }
        binding.imageNumber8.setOnSafeClickListener { clickNumber(it) }
        binding.imageNumber9.setOnSafeClickListener { clickNumber(it) }
        binding.imageNumberStar.setOnSafeClickListener { clickNumber(it) }
        binding.imageNumberSharp.setOnSafeClickListener { clickNumber(it) }

        binding.btnChangeType.setOnSafeClickListener {

            binding.txtPhoneKidsNumScreen.text = ""
            mediaPlayer?.playSoundMediaPlayer(context, R.raw.memory_game_success)
            if (isAnimalMode) {
                binding.imageGif.visibility = View.GONE
                binding.imageGif.setImageResource(0)
                binding.imageGif.loadGif(0)
                binding.btnChangeType.setImageResource(R.drawable.phone_kids_mode_numbers_en)
            } else {

                generateRandomArray()
                binding.imageGif.visibility = View.VISIBLE
                binding.btnChangeType.setImageResource(R.drawable.phone_kids_mode_animals_en)
            }


            isAnimalMode = !isAnimalMode
        }
    }

    private fun generateRandomArray() {
        listNumberRandom.clear()
        while (listNumberRandom.size != 12) {
            (0 until listAllAnimal.size).random().let { number ->

                if (!listNumberRandom.contains(number))
                    listNumberRandom.add(number)
            }
        }
    }

    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        isAnimalMode = false
        timer?.cancel()
        timer?.purge()
        timer = null
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun clickNumber(view: View) {

        view.animClick()
        if (isAnimalMode) {
            val number = listNumberRandom[view.tag.toString().toInt()]
            val animal = listAllAnimal[number]
            binding.imageGif.loadGif(animal.imageGif)
            animal.soundPlay?.let {
                mediaPlayer?.playSoundMediaPlayer(context, animal.soundPlay, isLoop = true)
            }


        } else {

            val idSound: Int? = when (view.tag) {
                "11" -> {
                    R.raw.phone_kids_tone_hash
                }

                "10" -> {
                    R.raw.phone_kids_tone_star
                }

                else -> {

                    context?.getMusicInRawByName("phone_kids_tone${view.tag}")
                }
            }

            val textNumber = binding.txtPhoneKidsNumScreen.text
            var newTag = view.tag

            if (view.tag == "10" || view.tag == "11") {
                newTag = if (view.tag == "10")
                    "*"
                else "#"
            }

            val finalText = "$textNumber${newTag}"
            binding.txtPhoneKidsNumScreen.text = finalText
            mediaPlayer?.playSoundMediaPlayer(context, idSound)
        }
    }


}