package com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.balloonsGame

import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.lifecycle.lifecycleScope
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.cv.explosionfield.ExplosionField
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.AnimalModel
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.getListData
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.TypeGameBubble
import com.farad.entertainment.kidsanimalenglish.data.model.getSoundTrueRandom
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentGameBalloonsBinding
import com.farad.entertainment.kidsanimalenglish.databinding.ViewBallonGameBinding
import com.farad.entertainment.kidsanimalenglish.ui.dialog.DialogStartGameBubble
import com.farad.entertainment.kidsanimalenglish.utils.changeScreenOrientation
import com.farad.entertainment.kidsanimalenglish.utils.clearFlag
import com.farad.entertainment.kidsanimalenglish.utils.fullScreenFragment
import com.farad.entertainment.kidsanimalenglish.utils.gone
import com.farad.entertainment.kidsanimalenglish.utils.lifecycleScopeDelayTryCatch
import com.farad.entertainment.kidsanimalenglish.utils.loadImage
import com.farad.entertainment.kidsanimalenglish.utils.loadSoundPool
import com.farad.entertainment.kidsanimalenglish.utils.loadSoundPoolRaw
import com.farad.entertainment.kidsanimalenglish.utils.playAnimParticleSystem
import com.farad.entertainment.kidsanimalenglish.utils.playSound
import com.farad.entertainment.kidsanimalenglish.utils.playSoundMediaPlayer
import com.farad.entertainment.kidsanimalenglish.utils.safeShow
import com.farad.entertainment.kidsanimalenglish.utils.screenOn
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.visible
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BalloonsFragment : BottomNavigationFragment<FragmentGameBalloonsBinding>() {
    private var screenWidth = 0
    private var screenHeight = 0

    private var balloonSizeWidth = 0
    private var balloonSizeHeight = 0
    private var animalsSize = 0

    private var _viewBalloonGameBinding: ViewBallonGameBinding? = null
    private val viewBalloonGameBinding get() = requireNotNull(_viewBalloonGameBinding)


    private var explosionField: ExplosionField? = null


    private var isPlayGame = false


    private var soundPool: SoundPool? = null
    private var mediaPlayer: MediaPlayer? = null

    private var incorrectSoundPool = 0
    private var balloonBurstSoundPool = 0
    private var clapsSoundPool = 0


    private val listBalloon = arrayListOf(
        R.drawable.balloon1,
        R.drawable.balloon2,
        R.drawable.balloon3,
        R.drawable.balloon4,
        R.drawable.balloon5
    )
    private var inCorrectCounter = 0

    private var answer: AnimalModel? = null
    private var counterBalloon = 0

    private var listAllAnimal = ArrayList<AnimalModel>()

    private var typeGameBubble = TypeGameBubble.SOUND_ANIMAL

    private var addedViews = ArrayList<View>()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGameBalloonsBinding
        get() = FragmentGameBalloonsBinding::inflate

    override fun setup() {
        changeScreenOrientation(true)
        screenOn()
        fullScreenFragment()
        initView()
        initSoundPool()
        listener()
        binding.root.post {
            setBackGroundRoot(false)
        }

    }

    override fun onBackPressedCompact() {
        super.onBackPressedCompact()
        changeScreenOrientation(false)
    }

    private fun listener() {
        binding.btnBack.setOnSafeClickListener {
            popBackStack()
        }
        binding.btnRepeat.setOnSafeClickListener {
            playSoundAnswer()
        }
    }

    private fun initSoundPool() {
        soundPool = SoundPool.Builder().setMaxStreams(128).build()
        mediaPlayer = MediaPlayer()
        context?.let { context ->
            soundPool?.loadSoundPoolRaw(context, R.raw.incorrect)?.let {
                incorrectSoundPool = it
            }
            soundPool?.loadSoundPoolRaw(context, R.raw.balloon_burst)?.let {
                incorrectSoundPool = it
            }
            soundPool?.loadSoundPool(context, "claps.mp3")?.let {
                clapsSoundPool = it
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        soundPool?.release()
        soundPool = null
        mediaPlayer?.release()
        mediaPlayer = null
        answer = null
        changeScreenOrientation(false)
        clearFlag()
        isPlayGame = false
    }

    private fun getLayout() {
        _viewBalloonGameBinding = ViewBallonGameBinding.inflate(layoutInflater)
    }

    private fun initView() {
        binding.imageMain.loadImage(R.drawable.bg_sky_day)
        activity?.let {
            explosionField = ExplosionField.attach2Window(activity)
        }
        _binding?.root?.post {
            showDialogStartGame()
        }
    }


    private fun showDialogStartGame() {
        val dialog = DialogStartGameBubble()
        dialog.isCanceledOnTouchOutside = false
        dialog.isCancelable = false
        dialog.setOnBackPressedListener {
            popBackStack()
        }
        dialog.setOnItemStartClickListener {
            typeGameBubble = it
            createQuestion()
        }
        dialog.setOnItemExitClickListener {
            popBackStack()
        }
        dialog.safeShow(childFragmentManager)
    }

    private fun setTranslationY(view: View) {
        Looper.myLooper()?.let { looper ->
            Handler(looper).postDelayed({
                if (isPlayGame && isNullView().not()) {
                    view.translationY = view.translationY - 10
                    setTranslationY(view)
                    //                "شرط گذاشتیم که اگر بادکنک ها از صفحه خارج شدن حذف بشن"
                    if (view.y < -view.height * 1.5) {
                        binding.rltDrawing.removeView(view)
                    }
                }

            }, 50)
        }

    }

    private fun createBalloon() {
        Looper.myLooper()?.let { looper ->
            Handler(looper).postDelayed({
                if (isPlayGame && isNullView().not()) {
                    val bouillonBinding = addBouillon()
                    val random = java.util.Random()
                    val randomNumber =
                        random.nextInt((binding.rltDrawing.width - (balloonSizeWidth + (balloonSizeWidth / 2))))
                    bouillonBinding.root.translationY = binding.rltDrawing.height.toFloat()
                    bouillonBinding.root.translationX = randomNumber.toFloat()

                    binding.rltDrawing.addView(bouillonBinding.root)
                    addedViews.add(bouillonBinding.root)
                    setTranslationY(bouillonBinding.root)
                    createBalloon()

                }


            }, 1500)
        }

    }

    private fun createQuestion() {
        isPlayGame = true
        binding.tvAnswer.text = getString(R.string.correct_answer)

        binding.rltDrawing.removeAllViews()
        binding.rltDrawing.post {
            screenWidth = binding.rltDrawing.width
            screenHeight = binding.rltDrawing.height
            balloonSizeWidth = screenWidth / 6
            balloonSizeHeight = balloonSizeWidth + 150

            animalsSize = (balloonSizeWidth / 2)

            context?.let {
                listAllAnimal = it.getListData()

                answer = listAllAnimal[randomAnimal()]
                binding.tvText.text = answer?.title.toString()
            }

            playSoundAnswer()

            createBalloon()

        }
    }


    private fun incorrect() {
        inCorrectCounter += 1
        soundPool?.playSound(incorrectSoundPool)
        binding.txtGuide.text = inCorrectCounter.toString()

    }

    private fun randomAnimal(): Int {
        val random = java.util.Random()
        return random.nextInt(listAllAnimal.size)
    }

    private fun playSoundAnswer() {

        if (typeGameBubble == TypeGameBubble.SOUND_ANIMAL) {
            mediaPlayer?.playSoundMediaPlayer(
                context,
                answer?.soundAnimal
            )
        } else {
            mediaPlayer?.playSoundMediaPlayer(
                context,
                answer?.soundNameEnglish
            )
        }

    }



    private fun addBouillon(): ViewBallonGameBinding {
        getLayout()

        val rootView = viewBalloonGameBinding
        val params = RelativeLayout.LayoutParams(balloonSizeWidth, balloonSizeWidth)
        rootView.frmBorder.layoutParams = params

        val paramsImage = RelativeLayout.LayoutParams(animalsSize, animalsSize)
        paramsImage.addRule(RelativeLayout.CENTER_HORIZONTAL)
        rootView.frmBorder.layoutParams = params
        rootView.imageAnimal.layoutParams = paramsImage


        val animal = listAllAnimal[randomAnimal()]


        if (addedViews.size % 12 == 0 && addedViews.size > 9) {
            answer?.image?.let { image ->
                rootView.imageAnimal.setImageResource(image)
            }
            rootView.tvName.text = answer?.title.toString()
        } else {
            if (counterBalloon == listBalloon.size) {
                counterBalloon = 0
            }
            rootView.tvName.text = animal.title
            rootView.imageBalloon.setImageResource(listBalloon[counterBalloon])
            counterBalloon++
            rootView.imageAnimal.setImageResource(animal.image)

        }


        rootView.root.setOnSafeClickListener {
            it.isEnabled = false
            explosionField?.explode(rootView.imageBalloon)
            binding.tvAnswer.text = rootView.tvName.text.toString()
            soundPool?.playSound(balloonBurstSoundPool)

            if (rootView.tvName.text.toString() == answer?.title) {

                binding.tvText.visible(true)
                soundPool?.playSound(clapsSoundPool)
                inCorrectCounter = 0
                binding.txtGuide.text = inCorrectCounter.toString()
                //        "کاغذباران"


                mediaPlayer?.playSoundMediaPlayer(context, getSoundTrueRandom())
                playAnimParticleSystem(binding.rltDrawing)
                lifecycleScopeDelayTryCatch(1000){
                    binding.rltDrawing.removeAllViews()
                    binding.tvText.gone(true)
                }

                isPlayGame = false
                isPlayGame = true
                lifecycleScopeDelayTryCatch(2000){
                    binding.tvAnswer.text = getString(R.string.correct_answer)
                    answer = listAllAnimal[randomAnimal()]
                    playSoundAnswer()
                    binding.tvText.text = answer?.title.toString()
                }


            } else {
                incorrect()

            }
        }

        return rootView
    }


}