package com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.animalFarm

import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.media.MediaPlayer
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import android.widget.RelativeLayout
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.cv.leonids.ParticleSystem
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.getListData
import com.farad.entertainment.kidsanimalenglish.data.model.getSoundFalseRandom
import com.farad.entertainment.kidsanimalenglish.data.model.getSoundTrueRandom
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentAnimalFarmBinding
import com.farad.entertainment.kidsanimalenglish.utils.alphaRepeat
import com.farad.entertainment.kidsanimalenglish.utils.animVibrate
import com.farad.entertainment.kidsanimalenglish.utils.changeScreenOrientation
import com.farad.entertainment.kidsanimalenglish.utils.delayTimerIsNullViewNot
import com.farad.entertainment.kidsanimalenglish.utils.expandReverseFoodAnim
import com.farad.entertainment.kidsanimalenglish.utils.fadeInAnimation
import com.farad.entertainment.kidsanimalenglish.utils.getImageDrawableByName
import com.farad.entertainment.kidsanimalenglish.utils.getMusicInRawByName
import com.farad.entertainment.kidsanimalenglish.utils.getScreenHeight
import com.farad.entertainment.kidsanimalenglish.utils.getScreenWidth
import com.farad.entertainment.kidsanimalenglish.utils.loadGif
import com.farad.entertainment.kidsanimalenglish.utils.playAnimParticleSystem
import com.farad.entertainment.kidsanimalenglish.utils.playSoundMediaPlayer
import com.farad.entertainment.kidsanimalenglish.utils.screenOn
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.visibleOrGone
import com.farad.entertainment.kidsanimalenglish.utils.visibleOrInvisible

class AnimalFarmFragment : BottomNavigationFragment<FragmentAnimalFarmBinding>() {

    private var threeNumberArray = ArrayList<Int>()
    private var question = 0

    private var mediaPlayer: MediaPlayer? = null
    private var mediaPlayer2: MediaPlayer? = null
    private var mediaPlayer3: MediaPlayer? = null
    private var mediaPlayerRain: MediaPlayer? = null
    private var mediaPlayerRain2: MediaPlayer? = null
    private var mediaPlayerSun: MediaPlayer? = null

    private var isClick = false
    private var isRain = false
    private var isSun = false

    private var particleSystem: ParticleSystem? = null

    private var listFood = ArrayList<Int>()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAnimalFarmBinding
        get() = FragmentAnimalFarmBinding::inflate

    override fun setup() {
        initMediaPlayer()
        changeScreenOrientation(true)
        screenOn()
        listener()
        binding.rtlDrawabel.post {

            delayTimerIsNullViewNot(300){
                context?.apply {
                    createQuestion()
                    showQuestion()
                    checkWidth()
                }
            }


        }
        setFood()

    }

    //هرگز پاک نشود جهت جلوگیری از سیاه شدن غذا ها
    private fun setFood() {
        listFood.add(R.drawable.f1)
        listFood.add(R.drawable.f2)
        listFood.add(R.drawable.f3)
        listFood.add(R.drawable.f4)
        listFood.add(R.drawable.f5)
        listFood.add(R.drawable.f6)
        listFood.add(R.drawable.f7)
        listFood.add(R.drawable.f8)
        listFood.add(R.drawable.f9)
        listFood.add(R.drawable.f10)
        listFood.add(R.drawable.f11)
        listFood.add(R.drawable.f12)
        listFood.add(R.drawable.f13)
        listFood.add(R.drawable.f14)
        listFood.add(R.drawable.f15)
        listFood.add(R.drawable.f16)
        listFood.add(R.drawable.f17)
        listFood.add(R.drawable.f18)
        listFood.add(R.drawable.f19)
        listFood.add(R.drawable.f20)
        listFood.add(R.drawable.f21)
        listFood.add(R.drawable.f22)
        listFood.add(R.drawable.f23)
        listFood.add(R.drawable.f24)
        listFood.add(R.drawable.f25)
        listFood.add(R.drawable.f26)
        listFood.add(R.drawable.f27)
    }

    override fun onStart() {
        super.onStart()
        if (isRain) {
            mediaPlayerRain?.start()
            mediaPlayerRain2?.start()
        }

        if (isSun) {
            mediaPlayerSun?.start()
        }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayerRain?.pause()
        mediaPlayerRain2?.pause()
        mediaPlayer?.pause()
        mediaPlayer2?.pause()
        mediaPlayer3?.pause()
        mediaPlayerSun?.pause()
    }

    override fun onBackPressedCompact() {
        particleSystem?.cancel()
        particleSystem?.stopEmitting()
        super.onBackPressedCompact()
    }

    private fun listener() {

        binding.imgCloud.alphaRepeat()
        binding.imgSun.setOnSafeClickListener {
            it.fadeInAnimation()
            isSun = !isSun
            if (isSun) {
                mediaPlayerSun?.playSoundMediaPlayer(context, R.raw.lala1, onCompletion = {
                    isSun = !isSun


                    binding.gifView.isEnabled = true
                    binding.imgFood1.isEnabled = true
                    binding.imgFood2.isEnabled = true
                    binding.imgFood3.isEnabled = true

                    binding.imgSun.colorFilter = brightIt(0)
                    binding.imgFood1.colorFilter = brightIt(0)
                    binding.imgFood2.colorFilter = brightIt(0)
                    binding.imgFood3.colorFilter = brightIt(0)
                    binding.imageBack.colorFilter = brightIt(0)
                    binding.imgCloud.colorFilter = brightIt(0)


                })
                binding.gifView.isEnabled = false
                binding.imgFood1.isEnabled = false
                binding.imgFood2.isEnabled = false
                binding.imgFood3.isEnabled = false

                binding.imgSun.colorFilter = brightIt(-180)
                binding.imgCloud.colorFilter = brightIt(-180)
                binding.imgFood1.colorFilter = brightIt(-180)
                binding.imgFood2.colorFilter = brightIt(-180)
                binding.imgFood3.colorFilter = brightIt(-180)
                binding.imageBack.colorFilter = brightIt(-180)
            } else {
                mediaPlayerSun?.pause()
                binding.gifView.isEnabled = true
                binding.imgFood1.isEnabled = true
                binding.imgFood2.isEnabled = true
                binding.imgFood3.isEnabled = true
                binding.imgSun.colorFilter = brightIt(0)
                binding.imgFood1.colorFilter = brightIt(0)
                binding.imgFood2.colorFilter = brightIt(0)
                binding.imgFood3.colorFilter = brightIt(0)
                binding.imageBack.colorFilter = brightIt(0)
                binding.imgCloud.colorFilter = brightIt(0)
            }


        }

        binding.imgCloud.setOnSafeClickListener {
            isRain = !isRain

            it.fadeInAnimation()

            makeDropAnim(isRain)
        }
        binding.imgFood1.setOnSafeClickListener {

            checkAnswer(binding.imgFood1)
        }
        binding.imgFood2.setOnSafeClickListener {
            checkAnswer(binding.imgFood2)
        }
        binding.imgFood3.setOnSafeClickListener {
            checkAnswer(binding.imgFood3)
        }

        binding.gifView.setOnSafeClickListener {
            mediaPlayer3?.playSoundMediaPlayer(context, context?.getMusicInRawByName("en$question"))
        }
    }

    fun brightIt(fb: Int): ColorMatrixColorFilter {
        val cmB = ColorMatrix()
        cmB.set(
            floatArrayOf(
                1f, 0f, 0f, 0f, fb.toFloat(),
                0f, 1f, 0f, 0f, fb.toFloat(),
                0f, 0f, 1f, 0f, fb.toFloat(),
                0f, 0f, 0f, 1f, 0f
            )
        )
        val colorMatrix = ColorMatrix()
        colorMatrix.set(cmB)

        return ColorMatrixColorFilter(colorMatrix)
    }

    private fun makeDropAnim(exist: Boolean) {

        binding.canvasRain.visibleOrGone(exist, true)
        if (isRain) {
            mediaPlayerRain?.playSoundMediaPlayer(context, R.raw.rain_1, isLoop = true)
            mediaPlayerRain2?.playSoundMediaPlayer(context, R.raw.drop_effect_sound, isLoop = true)
            binding.imgSun.colorFilter = brightIt(-100)
        } else {
            mediaPlayerRain?.pause()
            mediaPlayerRain2?.pause()
            binding.imgSun.colorFilter = brightIt(0)
        }
        activity?.let { activity ->

            if (exist) {
                particleSystem = ParticleSystem(activity, 3, R.drawable.shape_drop, 1000)
                particleSystem?.setAcceleration(0.00013f, 90)
                particleSystem?.setSpeedByComponentsRange(0f, 0f, 0.05f, 0.1f)
                particleSystem?.setFadeOut(200, AccelerateInterpolator())
                particleSystem?.emitWithGravity(binding.imgCloud, Gravity.BOTTOM, 3)
            } else {
                particleSystem?.stopEmitting()
                particleSystem = null
            }
        }

    }

    private fun checkWidth() {
        context?.apply {
            val screenWidth = getScreenWidth()
            binding.imgCloud.visibleOrInvisible(screenWidth > 400)
        }

    }

    override fun onDestroyView() {

        particleSystem?.cancel()
        particleSystem?.stopEmitting()
        mediaPlayer?.release()
        mediaPlayer2?.release()
        mediaPlayer3?.release()
        mediaPlayerRain?.release()
        mediaPlayerRain2?.release()
        mediaPlayer = null
        mediaPlayer2 = null
        mediaPlayer3 = null
        mediaPlayerRain = null
        mediaPlayerRain2 = null
        particleSystem = null
        super.onDestroyView()
    }

    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer()
        mediaPlayer2 = MediaPlayer()
        mediaPlayer3 = MediaPlayer()
        mediaPlayerRain = MediaPlayer()
        mediaPlayerRain2 = MediaPlayer()
        mediaPlayerSun = MediaPlayer()
    }

    private fun generateRandomNumber(): Int {
        return (0..74).random()
    }

    private fun animalFood(): ArrayList<String> {
        val foodArray = ArrayList<String>()
        foodArray.add("1,1,1,1") //////0)  ////alaki
        foodArray.add("1,4,5,26") //////1)
        foodArray.add("2") //////2,
        foodArray.add("3,6,7,12") //////3,
        foodArray.add("4,15") //////4,
        foodArray.add("4,5,15,26") //////5,
        foodArray.add("6") //////6,
        foodArray.add("14,18") //////7,
        foodArray.add("6,19,20") //////8,
        foodArray.add("4,5,26") //////9,
        foodArray.add("4,15,18,26") //////10
        foodArray.add("3,6,12,13,23,25") //////11
        foodArray.add("4,5,8,15") //////12
        foodArray.add("18,20") //////13
        foodArray.add("4,5,26") //////14
        foodArray.add("4,5,26") //////15
        foodArray.add("4,15,18,26,") //////16
        foodArray.add("8") //////17
        foodArray.add("3") //////18
        foodArray.add("8,9,15,26") //////19
        foodArray.add("10,21") //////20
        foodArray.add("4,11,15,18") //////21
        foodArray.add("12,13") //////22
        foodArray.add("6,8,27") //////23
        foodArray.add("4,5,8,10,15,19,24") //////24
        foodArray.add("6,12,13,17,18") //////25
        foodArray.add("6") //////26
        foodArray.add("4,5,26") //////27
        foodArray.add("3,12,13,19") //////28
        foodArray.add("3") //////29
        foodArray.add("4,5,15,26") //////30
        foodArray.add("4,5,10,15") //////31
        foodArray.add("3,12,13") //////32
        foodArray.add("4,5,26") //////33
        foodArray.add("4,5,8") //////34
        foodArray.add("4,5") //////35
        foodArray.add("6") //////36
        foodArray.add("4,5,8,15") //////37
        foodArray.add("4") //////38
        foodArray.add("3,12,13,15,18,19,20,22,23") //////39
        foodArray.add("4,5") //////40
        foodArray.add("14,15,24") //////41
        foodArray.add("4,5,26") //////42
        foodArray.add("3") //////43
        foodArray.add("4,5,8") //////44
        foodArray.add("6,15,19,23,24") //////45
        foodArray.add("4,5,26") //////46
        foodArray.add("4,5") //////47
        foodArray.add("15,18") //////48
        foodArray.add("6") //////49
        foodArray.add("4,18") //////50
        foodArray.add("4,18") //////51
        foodArray.add("4,18,26") //////52
        foodArray.add("3,24") //////53
        foodArray.add("18") //////54
        foodArray.add("6") //////55
        foodArray.add("4,15") //////56
        foodArray.add("4,15,16") //////57
        foodArray.add("4,18") //////58
        foodArray.add("3,12,13,25") //////59
        foodArray.add("4,15,18") //////60
        foodArray.add("4,18") //////61
        foodArray.add("3,6,12,13") //////62
        foodArray.add("4,5") //////63
        foodArray.add("6,20") //////64
        foodArray.add("6,8,15,18") //////65
        foodArray.add("3,12,13,18") //////66
        foodArray.add("5,8,4,15,9,1,26") //////67
        foodArray.add("4,5") //////68
        foodArray.add("4,18") //////69
        foodArray.add("3,6,20") //////70
        foodArray.add("4,6,18,19") //////71
        foodArray.add("3") //////72
        foodArray.add("6,12,13,18") //////73
        foodArray.add("4,5,26,15,18,20,6,19") //////74
        foodArray.add("2,15,18") //////75
        return foodArray
    }

    private fun animalHidden(): Array<Int> {
        return arrayOf(6, 7, 8, 11, 13, 15, 17, 26, 36, 38, 40, 44, 50, 58, 60, 61, 65, 69)
    }

    private fun createQuestion() {
        binding.root.post {
            val hiddenArray = animalHidden()
            question = generateRandomNumber()
            while (listOf(*hiddenArray).contains(question)) {
                question = generateRandomNumber()
            }

            val foodArray = animalFood()
            var strings = foodArray[question].split(",".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            var numbers = IntArray(strings.size)
            for (i in numbers.indices) {
                numbers[i] = strings[i].toInt()
            }
            ///List Of Interger Food Of Animal shuffled
            var list = ArrayList<Int>()
            for (i in numbers) {
                list.add(i)
            }
            list.shuffle()
            val foodQuestion = list[0]
            ///////////
            var a = generateRandomNumber()

            strings =
                foodArray[a].split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            numbers = IntArray(strings.size)
            for (i in numbers.indices) {
                numbers[i] = strings[i].toInt()
            }
            list.clear()
            list = java.util.ArrayList()
            for (i in numbers) {
                list.add(i)
            }
            list.shuffle()
            var foodA = list[0]
            while (foodA == foodQuestion) {
                a = generateRandomNumber()
                strings =
                    foodArray[a].split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                numbers = IntArray(strings.size)
                for (i in numbers.indices) {
                    numbers[i] = strings[i].toInt()
                }
                list.clear()
                list = java.util.ArrayList()
                for (i in numbers) {
                    list.add(i)
                }
                list.shuffle()
                foodA = list[0]
            }
            var b = generateRandomNumber()
            strings =
                foodArray[b].split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            numbers = IntArray(strings.size)
            for (i in numbers.indices) {
                numbers[i] = strings[i].toInt()
            }
            list.clear()
            list = java.util.ArrayList()
            for (i in numbers) {
                list.add(i)
            }
            list.shuffle()
            var foodB = list[0]
            while (foodB == foodQuestion || foodB == foodA) {
                b = generateRandomNumber()
                strings =
                    foodArray[b].split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                numbers = IntArray(strings.size)
                for (i in numbers.indices) {
                    numbers[i] = strings[i].toInt()
                }
                list.clear()
                list = java.util.ArrayList()
                for (i in numbers) {
                    list.add(i)
                }
                list.shuffle()
                foodB = list[0]
            }


//
            threeNumberArray.clear()
            threeNumberArray.add(question)
            threeNumberArray.add(foodQuestion)
            threeNumberArray.add(a)
            threeNumberArray.add(foodA)
            threeNumberArray.add(b)
            threeNumberArray.add(foodB)
        }


    }

    private fun showQuestion() {
        binding.root.post {
            isClick = false
            try {
                val imageName1 = "f" + threeNumberArray[1]
                val imageName2 = "f" + threeNumberArray[3]
                val imageName3 = "f" + threeNumberArray[5]

                binding.imgFood1.tag = threeNumberArray[1]
                binding.imgFood2.tag = threeNumberArray[3]
                binding.imgFood3.tag = threeNumberArray[5]

                binding.imgFood1.setBackgroundColor(Color.TRANSPARENT)
                binding.imgFood2.setBackgroundColor(Color.TRANSPARENT)
                binding.imgFood3.setBackgroundColor(Color.TRANSPARENT)



                context?.apply {

                    val screenHeight = getScreenHeight()
                    val screenWidth = getScreenWidth()

                    binding.imgFood1.setImageResource(getImageDrawableByName(imageName1))
                    binding.imgFood2.setImageResource(getImageDrawableByName(imageName2))
                    binding.imgFood3.setImageResource(getImageDrawableByName(imageName3))

                    val imgSunWidth = screenHeight / 4

                    val paramsSun = RelativeLayout.LayoutParams(imgSunWidth, imgSunWidth)
                    paramsSun.addRule(RelativeLayout.ALIGN_PARENT_TOP)
                    paramsSun.addRule(RelativeLayout.RIGHT_OF, R.id.btnNextfarm)
                    paramsSun.setMargins(0, 15, 0, 0)
                    binding.imgSun.layoutParams = paramsSun

                    val imgWidth = screenHeight / 3

                    val params = RelativeLayout.LayoutParams(imgWidth, imgWidth)
                    params.addRule(RelativeLayout.CENTER_VERTICAL)
                    params.leftMargin = 28
                    binding.gifView.layoutParams = params

                    val numberGif=question + 1
                    context?.getListData()?.forEach {
                        if (it.imageGif.contains("g${numberGif}.gif")){
                            binding.gifView.loadGif(it.imageGif )
                            return@forEach
                        }

                    }


                }

            }catch (e:Exception){
                e.printStackTrace()
            }

        }

    }

    private fun checkAnswer(imgAnswerID: View) {

        try {
            if (!isClick) {
                val imgTag1 = binding.imgFood1.tag.toString().toInt()
                val imgTag2 = binding.imgFood2.tag.toString().toInt()
                val imgTag3 = binding.imgFood3.tag.toString().toInt()

                val foodArray = animalFood()

                val strings =
                    foodArray[question].split(",".toRegex()).dropLastWhile { it.isEmpty() }
                        .toTypedArray()
                val numbers = IntArray(strings.size)
                for (i in numbers.indices) {
                    numbers[i] = strings[i].toInt()
                }

                val list: MutableList<Int> = java.util.ArrayList()
                for (i in numbers) {
                    list.add(i)
                }


                when (imgAnswerID) {
                    binding.imgFood1 -> {
                        if (list.contains(imgTag1)) {
                            correctAnswer(binding.imgFood1)
                        } else {
                            wrongAnswer(binding.imgFood1)
                        }
                    }

                    binding.imgFood2 -> {
                        if (list.contains(imgTag2)) {
                            correctAnswer(binding.imgFood2)
                        } else {
                            wrongAnswer(binding.imgFood2)
                        }
                    }

                    binding.imgFood3 -> {
                        if (list.contains(imgTag3)) {
                            correctAnswer(binding.imgFood3)
                        } else {
                            wrongAnswer(binding.imgFood3)
                        }
                    }
                }
            }
        }catch (e:Exception){
            e.fillInStackTrace()
        }

    }

    private fun correctAnswer(imageAnswer: View) {

        val animationSet = AnimationSet(true)
        var translateAnim: TranslateAnimation? = null

        context?.apply {
            binding.rtlDrawabel.post {
                val screenWidth = getScreenWidth()
                val screenHeight = getScreenHeight()
                val imgFoodFromX = 0f
                val imgFoodToX = -(screenWidth / 2 - (binding.gifView.width / 3))
                val imgFoodFromY = 0f
                val imgFoodToY = (-((screenHeight / 2) - (binding.gifView.height / 2))).toFloat()

                val imgFood2ToX = (-(screenWidth / 1.5)).toFloat()
                val imgFood3ToX = (-(screenWidth / 1.2)).toFloat()


                when (imageAnswer) {
                    binding.imgFood1 -> {

                        translateAnim = TranslateAnimation(
                            imgFoodFromX,
                            imgFoodToX.toFloat(),
                            imgFoodFromY,
                            imgFoodToY.toFloat()
                        );
                    }

                    binding.imgFood2 -> {
                        translateAnim =
                            TranslateAnimation(
                                imgFoodFromX,
                                imgFood2ToX.toFloat(),
                                imgFoodFromY,
                                imgFoodToY
                            )
                    }

                    binding.imgFood3 -> {
                        translateAnim =
                            TranslateAnimation(
                                imgFoodFromX,
                                imgFood3ToX.toFloat(),
                                imgFoodFromY,
                                imgFoodToY
                            )
                    }
                }
                translateAnim?.duration = 2000
                animationSet.addAnimation(translateAnim)
                imageAnswer.startAnimation(translateAnim)

                translateAnim?.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {
                        isClick = true
                    }

                    override fun onAnimationEnd(animation: Animation) {

                        binding.gifView.expandReverseFoodAnim()

                        imageAnswer.left = binding.gifView.left
                        imageAnswer.right = binding.gifView.right
                        imageAnswer.top = binding.gifView.top
                        imageAnswer.bottom = binding.gifView.bottom
                        val animScaleSmall: Animation = ScaleAnimation(
                            1f, 0f,
                            1f, 0f,
                            Animation.RELATIVE_TO_SELF, 0.5f,
                            Animation.RELATIVE_TO_SELF, 0.5f
                        )
                        animScaleSmall.fillAfter = true
                        animScaleSmall.duration = 4000
                        imageAnswer.startAnimation(animScaleSmall)
                        animScaleSmall.setAnimationListener(object : Animation.AnimationListener {
                            override fun onAnimationStart(animation: Animation) {

                                //  playClaps()
                                playAnimParticleSystem(binding.gifView)
                                mediaPlayer?.playSoundMediaPlayer(context, getSoundTrueRandom())
                                mediaPlayer2?.playSoundMediaPlayer(context, R.raw.claps)

                            }

                            override fun onAnimationEnd(animation: Animation) {
                                val animScaleBigger: Animation = ScaleAnimation(
                                    0f, 1f,
                                    0f, 1f,
                                    Animation.RELATIVE_TO_SELF, 0.5f,
                                    Animation.RELATIVE_TO_SELF, 0.5f
                                )
                                animScaleBigger.fillAfter =
                                    true // Needed to keep the result of the animation
                                animScaleBigger.duration = 1000
                                binding.imgFood1.startAnimation(animScaleBigger)
                                binding.imgFood2.startAnimation(animScaleBigger)
                                binding.imgFood3.startAnimation(animScaleBigger)
                                animScaleBigger.setAnimationListener(object :
                                    Animation.AnimationListener {
                                    override fun onAnimationStart(animation: Animation) {
                                        //   G.tryToStopPlayer()
                                    }

                                    override fun onAnimationEnd(animation: Animation) {
                                        //  G.tryToStopPlayer()
                                    }

                                    override fun onAnimationRepeat(animation: Animation) {}
                                })
                                createQuestion()
                                showQuestion()
                            }

                            override fun onAnimationRepeat(animation: Animation) {}
                        })
                    }

                    override fun onAnimationRepeat(animation: Animation) {}
                })
            }

        }


    }

    private fun wrongAnswer(view: View) {
        view.animVibrate()
        mediaPlayer?.playSoundMediaPlayer(context, getSoundFalseRandom())

    }

}