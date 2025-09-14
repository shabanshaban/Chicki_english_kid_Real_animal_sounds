package com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.bubbleGame

import android.animation.ObjectAnimator
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.get
import androidx.core.view.size
import androidx.lifecycle.lifecycleScope
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.cv.explosionfield.ExplosionField
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.AnimalModel
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.NumberBubble
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.TypeGameBubble
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.getListData
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentGameBubbleBinding
import com.farad.entertainment.kidsanimalenglish.databinding.ViewBubbleGameBinding
import com.farad.entertainment.kidsanimalenglish.ui.dialog.DialogStartGameBubble
import com.farad.entertainment.kidsanimalenglish.utils.changeScreenOrientation
import com.farad.entertainment.kidsanimalenglish.utils.clearFlag
import com.farad.entertainment.kidsanimalenglish.utils.fullScreenFragment
import com.farad.entertainment.kidsanimalenglish.utils.lifecycleScopeDelayTryCatch
import com.farad.entertainment.kidsanimalenglish.utils.loadImage
import com.farad.entertainment.kidsanimalenglish.utils.loadSoundPool
import com.farad.entertainment.kidsanimalenglish.utils.loadSoundPoolRaw
import com.farad.entertainment.kidsanimalenglish.utils.playSound
import com.farad.entertainment.kidsanimalenglish.utils.playSoundMediaPlayer
import com.farad.entertainment.kidsanimalenglish.utils.safeShow
import com.farad.entertainment.kidsanimalenglish.utils.screenOn
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.zoomAnimation
import kotlinx.coroutines.launch

class   BubbleGameFragment : BottomNavigationFragment<FragmentGameBubbleBinding>() {
    private var screenWidth = 0
    private var screenHeight = 0

    private var bubbleSize = 0
    private var animalsSize = 0

    private var _viewBubbleGameBinding: ViewBubbleGameBinding? = null
    private val viewBubbleGameBinding get() = requireNotNull(_viewBubbleGameBinding)

    private var directionX_1 = 0
    private var directionX_2 = 1
    private var directionX_3 = 0
    private var directionX_4 = 1
    private var directionX_5 = 0

    private var directionY_1 = 3
    private var directionY_2 = 4
    private var directionY_3 = 3
    private var directionY_4 = 4
    private var directionY_5 = 3

    private var explosionField: ExplosionField? = null


    private var stopBubble1 = false
    private var stopBubble2 = false
    private var stopBubble3 = false
    private var stopBubble4 = false
    private var stopBubble5 = false
    private var isPlayGame = false

    companion object {
        const val RIGHT = 0
        const val LEFT = 1
        const val TOP = 3
        const val BOTTOM = 4
    }

    private var soundPool: SoundPool? = null
    private var mediaPlayer: MediaPlayer? = null
    private var listNumber = ArrayList<Int>()

    private var incorrectSoundPool = 0
    private var clapsSoundPool = 0


    private val listBubble = arrayListOf(
        R.drawable.bubble_blue,
        R.drawable.bubble_green,
        R.drawable.bubble_red,
        R.drawable.bubble_yellow,
        R.drawable.bubble_purple
    )


    private var answer: AnimalModel? = null
    private val listFiveQuintonNumber = ArrayList<Int>()
    private var counterAnswer = 0

    private var listAllAnimal = ArrayList<AnimalModel>()

    private var typeGameBubble = TypeGameBubble.SOUND_ANIMAL
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGameBubbleBinding
        get() = FragmentGameBubbleBinding::inflate

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
        _viewBubbleGameBinding = ViewBubbleGameBinding.inflate(layoutInflater)
    }

    private fun initView() {
        binding.imageMain.loadImage(R.drawable.bg_sky_day)
        explosionField = ExplosionField.attach2Window(requireNotNull(activity))
        listNumber = createRandom()



        lifecycleScopeDelayTryCatch(300){
            binding.root.post {
                showDialogStartGame()
            }
        }

    }


    private fun showDialogStartGame() {
        lifecycleScopeDelayTryCatch(300){
            val dialog = DialogStartGameBubble()
            dialog.isCancelable = false
            dialog.isCanceledOnTouchOutside = false
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

    }

    private fun createQuestion() {
        lifecycleScopeDelayTryCatch(0){
            isPlayGame = true
            listFiveQuintonNumber.clear()

            (0..4).forEach {
                listFiveQuintonNumber.add(listNumber[it])
            }

            listNumber = try {
                listNumber.drop(70) as ArrayList<Int>
            } catch (e: Exception) {
                e.printStackTrace()
                createRandom()
            }


            binding.tvAnswer.text = getString(R.string.correct_answer)


            binding.rltDrawing.post {
                screenWidth = binding.rltDrawing.width
                screenHeight = binding.rltDrawing.height
                bubbleSize = screenWidth / 6

                animalsSize = (bubbleSize / 1.3).toInt()
                context?.getListData()?.let {
                    listAllAnimal = it
                }

                answer = listAllAnimal[listFiveQuintonNumber[counterAnswer]]
                playSoundAnswer()
                binding.rltDrawing.removeAllViews()
                listFiveQuintonNumber.forEachIndexed { index, i ->
                    addBubble(index + 1, listAllAnimal[i])
                }


            }
        }

    }

    private fun setTranslationBlobs(view: View) {
        Handler(requireNotNull(Looper.myLooper())).postDelayed({
            if (isPlayGame) {
                val tag = view.tag
                if (isNullView().not()) {
                    if (tag == NumberBubble.BUBBLE_1.value && !stopBubble1) {
                        //"حرکت در جهت افقی"
                        if (view.translationX < 0.0) {
                            directionX_1 = RIGHT
                        }
                        //"اگر حباب به دیوار سمت راست بخوره جهت حرکتش عوض بشه به چپ"
                        if (view.x > screenWidth - view.width) {
                            directionX_1 = LEFT
                        }
                        if (directionX_1 == RIGHT) {
                            view.translationX = view.translationX + 2
                        } else if (directionX_1 == LEFT) {
                            view.translationX = view.translationX - 2
                        }
                        //"حرکت در جهت عمودی"
                        if (view.translationY < 0.0) {
                            directionY_1 = BOTTOM
                        }
                        //"اگر حباب به دیوار پایین خورد جهت حرکتش به سمت بالا عوض بشه"
                        if (view.y > screenHeight - view.height) {
                            directionY_1 = TOP
                        }
                        if (directionY_1 == TOP) {
                            view.translationY = view.translationY - 2
                        } else if (directionY_1 == BOTTOM) {
                            view.translationY = view.translationY + 2
                        }
                        setTranslationBlobs(view)
                    } else if (tag == NumberBubble.BUBBLE_2.value && !stopBubble2) {
                        //"حرکت در جهت افقی"
                        if (view.translationX < 0.0) {
                            directionX_2 = RIGHT
                        }
                        //"اگر حباب به دیوار سمت راست بخوره جهت حرکتش عوض بشه به چپ"
                        if (view.x > screenWidth - view.width) {
                            directionX_2 = LEFT
                        }
                        if (directionX_2 == RIGHT) {
                            view.translationX = view.translationX + 2
                        } else if (directionX_2 == LEFT) {
                            view.translationX = view.translationX - 2
                        }
                        //"حرکت در جهت عمودی"
                        if (view.translationY < 0.0) {
                            directionY_2 = BOTTOM
                        }
                        //"اگر حباب به دیوار پایین خورد جهت حرکتش به سمت بالا عوض بشه"
                        if (view.y > screenHeight - view.height) {
                            directionY_2 = TOP
                        }
                        if (directionY_2 == TOP) {
                            view.translationY = view.translationY - 2
                        } else if (directionY_2 == BOTTOM) {
                            view.translationY = view.translationY + 2
                        }
                        setTranslationBlobs(view)
                    } else if (tag == NumberBubble.BUBBLE_3.value && !stopBubble3) {
                        //"حرکت در جهت افقی"
                        if (view.translationX < 0.0) {
                            directionX_3 = RIGHT
                        }
                        //"اگر حباب به دیوار سمت راست بخوره جهت حرکتش عوض بشه به چپ"
                        if (view.x > screenWidth - view.width) {
                            directionX_3 = LEFT
                        }
                        if (directionX_3 == RIGHT) {
                            view.translationX = view.translationX + 2
                        } else if (directionX_3 == LEFT) {
                            view.translationX = view.translationX - 2
                        }
                        //"حرکت در جهت عمودی"
                        if (view.translationY < 0.0) {
                            directionY_3 = BOTTOM
                        }
                        //"اگر حباب به دیوار پایین خورد جهت حرکتش به سمت بالا عوض بشه"
                        if (view.y > screenHeight - view.height) {
                            directionY_3 = TOP
                        }
                        if (directionY_3 == TOP) {
                            view.translationY = view.translationY - 2
                        } else if (directionY_3 == BOTTOM) {
                            view.translationY = view.translationY + 2
                        }
                        setTranslationBlobs(view)
                    } else if (tag == NumberBubble.BUBBLE_4.value && !stopBubble4) {
                        //"حرکت در جهت افقی"
                        if (view.translationX < 0.0) {
                            directionX_4 = RIGHT
                        }
                        //"اگر حباب به دیوار سمت راست بخوره جهت حرکتش عوض بشه به چپ"
                        if (view.x > screenWidth - view.width) {
                            directionX_4 = LEFT
                        }
                        if (directionX_4 == RIGHT) {
                            view.translationX = view.translationX + 2
                        } else if (directionX_4 == LEFT) {
                            view.translationX = view.translationX - 2
                        }
                        //"حرکت در جهت عمودی"
                        if (view.translationY < 0.0) {
                            directionY_4 = BOTTOM
                        }
                        //"اگر حباب به دیوار پایین خورد جهت حرکتش به سمت بالا عوض بشه"
                        if (view.y > screenHeight - view.height) {
                            directionY_4 = TOP
                        }
                        if (directionY_4 == TOP) {
                            view.translationY = view.translationY - 2
                        } else if (directionY_4 == BOTTOM) {
                            view.translationY = view.translationY + 2
                        }
                        setTranslationBlobs(view)
                    } else if (tag == NumberBubble.BUBBLE_5.value && !stopBubble5) {
                        //"حرکت در جهت افقی"
                        if (view.translationX < 0.0) {
                            directionX_5 = RIGHT
                        }
                        //"اگر حباب به دیوار سمت راست بخوره جهت حرکتش عوض بشه به چپ"
                        if (view.x > screenWidth - view.width) {
                            directionX_5 = LEFT
                        }
                        if (directionX_5 == RIGHT) {
                            view.translationX = view.translationX + 2
                        } else if (directionX_5 == LEFT) {
                            view.translationX = view.translationX - 2
                        }
                        //"حرکت در جهت عمودی"
                        if (view.translationY < 0.0) {
                            directionY_5 = BOTTOM
                        }
                        //"اگر حباب به دیوار پایین خورد جهت حرکتش به سمت بالا عوض بشه"
                        if (view.y > screenHeight - view.height) {
                            directionY_5 = TOP
                        }
                        if (directionY_5 == TOP) {
                            view.translationY = view.translationY - 2
                        } else if (directionY_5 == BOTTOM) {
                            view.translationY = view.translationY + 2
                        }
                        setTranslationBlobs(view)
                    }
                }
            }
        }, 15)
    }


    private fun createRandom(): ArrayList<Int> {
        val listNumber = ArrayList<Int>()


        while (listNumber.size < 75) {
            val number = (0..74).random()
            if (!listNumber.contains(number))
                listNumber.add(number)
        }

        return listNumber
    }


    private fun View.incorrect() {
        soundPool?.playSound(incorrectSoundPool)
        val viewX = (this.translationX * 1.2).toFloat()
        val viewY = (this.translationY * 1.4).toFloat()
        ObjectAnimator.ofFloat(this, View.TRANSLATION_X, this.translationX, viewX).setDuration(800)
            .start()
        ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, this.translationY, viewY).setDuration(800)
            .start()
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

    private fun changeLevel() {
        counterAnswer += 1
        val number = listFiveQuintonNumber[counterAnswer]

        val animal = listAllAnimal[number]

        soundPool?.playSound(clapsSoundPool)
        mediaPlayer?.pause()


        lifecycleScope.launch {
            if (counterAnswer == 4) {
                isPlayGame = false
                lifecycleScopeDelayTryCatch(1000){
                    val imageBubble = binding.rltDrawing.get(binding.rltDrawing.size - 1)
                        .findViewById<AppCompatImageView>(R.id.imageBubble)
                    explosionField?.explode(imageBubble)
                }
            }


        }
        lifecycleScopeDelayTryCatch(3000){
            answer = animal
            mediaPlayer?.playSoundMediaPlayer(context, animal.soundAnimal)

            if (counterAnswer == 4) {

                createQuestion()
                stopBubble1 = false
                stopBubble2 = false
                stopBubble3 = false
                stopBubble4 = false
                stopBubble5 = false
                counterAnswer = 0

                directionX_1 = 0
                directionX_2 = 1
                directionX_3 = 0
                directionX_4 = 1
                directionX_5 = 0
                directionY_1 = 3
                directionY_2 = 4
                directionY_3 = 3
                directionY_4 = 4
                directionY_5 = 3
            }
        }
    }

    private fun addBubble(tagView: Int, animalModel: AnimalModel) {
        getLayout()
        val rootView = viewBubbleGameBinding
        val params = RelativeLayout.LayoutParams(bubbleSize, bubbleSize)
        rootView.frmBorder.layoutParams = params


        val random = java.util.Random()
        val randomX = random.nextInt(screenWidth)
        val randomY = random.nextInt(screenHeight)


        rootView.frmBorder.translationX = randomX.toFloat()
        rootView.frmBorder.translationY = randomY.toFloat()

        binding.rltDrawing.addView(rootView.root)

        rootView.root.zoomAnimation()
        rootView.root.tag = tagView
        rootView.imageAnimal.setImageResource(animalModel.image)
        rootView.imageBubble.setImageResource(listBubble[tagView - 1])
        setTranslationBlobs(rootView.root)

        rootView.root.setOnSafeClickListener {

            if (animalModel == answer) {
                it.isEnabled = false
                explosionField?.explode(rootView.imageBubble)
                binding.tvAnswer.text = animalModel.title
                changeLevel()

                when (tagView) {
                    NumberBubble.BUBBLE_1.value -> {
                        stopBubble1 = true
                    }

                    NumberBubble.BUBBLE_2.value -> {
                        stopBubble2 = true
                    }

                    NumberBubble.BUBBLE_3.value -> {
                        stopBubble3 = true
                    }

                    NumberBubble.BUBBLE_4.value -> {
                        stopBubble4 = true
                    }

                    NumberBubble.BUBBLE_5.value -> {
                        stopBubble5 = true
                    }
                }
            } else {
                it.incorrect()
            }


        }
    }


}