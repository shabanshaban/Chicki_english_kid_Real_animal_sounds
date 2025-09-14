package com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.guessAnimal

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.AnimalModel
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.TypeGame
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.getListData
import com.farad.entertainment.kidsanimalenglish.data.model.getSoundFalseRandom
import com.farad.entertainment.kidsanimalenglish.data.model.getSoundTrueRandom
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentGuessAnimalBinding
import com.farad.entertainment.kidsanimalenglish.utils.SIZE_ITEM
import com.farad.entertainment.kidsanimalenglish.utils.animClickFast
import com.farad.entertainment.kidsanimalenglish.utils.animVibrate
import com.farad.entertainment.kidsanimalenglish.utils.dropAnim
import com.farad.entertainment.kidsanimalenglish.utils.gone
import com.farad.entertainment.kidsanimalenglish.utils.lifecycleScopeDelayTryCatch
import com.farad.entertainment.kidsanimalenglish.utils.playSoundMediaPlayer
import com.farad.entertainment.kidsanimalenglish.utils.scaleAnimation
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.visible

class GuessAnimalFragment : BottomNavigationFragment<FragmentGuessAnimalBinding>() {


    private val listImage = ArrayList<AppCompatImageView>()

    private var mediaPlayer: MediaPlayer? = null

    private val listAllAnimal = ArrayList<AnimalModel>()

    private var answer: AnimalModel? = null

    private var isFirstRun = false

    private var typeGame = TypeGame.SOUND_ANIMAL

    private var isNextLevel = false
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGuessAnimalBinding
        get() = FragmentGuessAnimalBinding::inflate

    override fun setup() {
        getListAnimal()
        addImage()
        initMediaPlayer()
        playMediaPlayer()
        createRandomAnimal()
        listener()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun listener() {
        listImage.forEach {
            it.setOnSafeClickListener {imageAnimal->

                if (!isNextLevel) {
                    if (imageAnimal.tag.toString().uppercase() == answer?.title?.uppercase()) {
                        isNextLevel=true
                        mediaPlayer?.playSoundMediaPlayer(context, getSoundTrueRandom())
                            binding.frameImageAnswer.visible()
                            binding.imgPic.dropAnim()
                            lifecycleScopeDelayTryCatch(2000){
                                if (binding.frameImageAnswer.isVisible) {
                                    binding.frameImageAnswer.gone()
                                    createRandomAnimal()
                                }
                            }

                    } else {
                        if (imageAnimal.tag != "-1") {
                            mediaPlayer?.playSoundMediaPlayer(context, getSoundFalseRandom())
                        }
                        imageAnimal.animVibrate()
                        imageAnimal.tag = "-1"
                    }
                }
            }
        }

        binding.imageRepeat.setOnSafeClickListener {
            it.animClickFast()
            playSound()
        }
        binding.btnNext.setOnSafeClickListener {
            if (!isNextLevel) {
                binding.frameImageAnswer.gone()
                it.animClickFast()
                createRandomAnimal()
            }
        }

        binding.radioGroup.setOnCheckedChangeListener { _, i ->

            typeGame = if (i == R.id.animalSound) {
                TypeGame.SOUND_ANIMAL
            } else {
                TypeGame.NAME_ANIMAL
            }

        }
    }

    private fun getListAnimal() {
        context?.getListData()?.let {
            listAllAnimal.clear()
            listAllAnimal.addAll(it)
        }
    }

    private fun createRandomAnimal() {
        isNextLevel = true
        val listRandom = ArrayList<AnimalModel>()
        while (listRandom.size != 4) {
            val random = (0..<SIZE_ITEM).random()
            if (!listRandom.contains(listAllAnimal[random])) {
                listRandom.add(listAllAnimal[random])
            }
        }

        listRandom.forEachIndexed { index, animalModel ->
            listImage[index].apply {
                setImageResource(animalModel.bigImage)
                this.tag = animalModel.title
            }


        }
        val random = (0..3).random()
        answer = listRandom[random]

        answer?.bigImage?.let { binding.imgPic.setImageResource(it) }
        listImage.forEach {
            it.scaleAnimation {
                isNextLevel = false
            }
        }



        if (!isFirstRun) {
            isFirstRun = true
            mediaPlayer?.playSoundMediaPlayer(context, R.raw.exam_explain_1, onCompletion = {
                playSound()

            })
        } else {
            playSound()
        }

    }


    private fun playSound() {

        if (typeGame == TypeGame.NAME_ANIMAL) {
            mediaPlayer?.playSoundMediaPlayer(context, answer?.soundNameEnglish)
        } else {
            mediaPlayer?.playSoundMediaPlayer(context, answer?.soundAnimal)
        }
    }

    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer()
    }

    private fun playMediaPlayer() {
        mediaPlayer?.playSoundMediaPlayer(context, R.raw.exam_explain_1)
    }

    private fun addImage() {
        listImage.add(binding.imgAnswer1)
        listImage.add(binding.imgAnswer2)
        listImage.add(binding.imgAnswer3)
        listImage.add(binding.imgAnswer4)
    }
}