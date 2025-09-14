package com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.wordGame.wordBoardGame

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.forEach
import androidx.navigation.fragment.navArgs
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.data.model.entity.WordGame
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentGameWordBinding
import com.farad.entertainment.kidsanimalenglish.databinding.ViewWordGameBinding
import com.farad.entertainment.kidsanimalenglish.ui.dialog.DialogHelpWordGame
import com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.wordGame.vm.WordGameViewModel
import com.farad.entertainment.kidsanimalenglish.utils.changeScreenOrientation
import com.farad.entertainment.kidsanimalenglish.utils.clearFlag
import com.farad.entertainment.kidsanimalenglish.utils.getScreenHeight
import com.farad.entertainment.kidsanimalenglish.utils.getScreenWidth
import com.farad.entertainment.kidsanimalenglish.utils.gone
import com.farad.entertainment.kidsanimalenglish.utils.lifecycleScopeDelayTryCatch
import com.farad.entertainment.kidsanimalenglish.utils.loadImage
import com.farad.entertainment.kidsanimalenglish.utils.playSoundMediaPlayer
import com.farad.entertainment.kidsanimalenglish.utils.safeShow
import com.farad.entertainment.kidsanimalenglish.utils.screenOn
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.setTextColorCompat
import com.farad.entertainment.kidsanimalenglish.utils.toast
import com.farad.entertainment.kidsanimalenglish.utils.visible
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class WordGameFragment : BottomNavigationFragment<FragmentGameWordBinding>() {


    private var _viewWordGameBinding: ViewWordGameBinding? = null
    private val viewWordGameBinding get() = requireNotNull(_viewWordGameBinding)


    private var tRootViewPlaceList = FloatArray(0)
    private var squareList: Array<View> = Array(0) { View(context) }

    private var screenWidth = 0
    private var screenHeight = 0
    private var squareWidth = 0
    private var squareSpace = 0
    private var lastIndex = 0

    private var locationX2 = 0f
    private var locationX3 = 0f
    private var locationX4 = 0f
    private var locationX5 = 0f
    private var locationX6 = 0f
    private var locationX7 = 0f
    private var locationX8 = 0f
    private var locationX9 = 0f
    private var locationX10 = 0f
    private var locationX11 = 0f
    private var lastLocation = 0f

    private var isFindWord = false

    private val args by navArgs<WordGameFragmentArgs>()

    private var mediaPlayer: MediaPlayer? = null

    private var word = ""
    private var wordConfusion = ""
    private var wordId = 0L

    private val activityViewModel by activityViewModel<WordGameViewModel>()

    private val listWord = ArrayList<WordGame>()

    private var itemPosition = 0

    private var isUseHelp = false
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGameWordBinding
        get() = FragmentGameWordBinding::inflate

    override fun setup() {
        getArgument()
        initMediaPlayer()
        screenOn()
        changeScreenOrientation(true)
        listener()
        initView()
        binding.root.post {
            lifecycleScopeDelayTryCatch(300){
                createQuestion()
            }

        }

    }

    override fun initObserveViewModel() {
        activityViewModel.listSortOpenWord.let {
            if (listWord.isEmpty()) {
                listWord.clear()
                listWord.addAll(it)
            }


        }
    }

    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer()
    }

    private fun getArgument() {
        args.wordGame.also { data ->
            wordId = data.id
            word = data.word
            wordConfusion = data.wordConfusion
            itemPosition = args.position
        }
    }

    private fun getLayout() {
        _viewWordGameBinding = ViewWordGameBinding.inflate(layoutInflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer =null
        changeScreenOrientation(false)
        clearFlag()
    }

    private fun initView() {
        binding.frameResult.gone()
        binding.imageBack.loadImage(R.drawable.bg_word_game)
    }


    private fun String.convertUppercase(char: String): String {

        return this.lowercase().replace(char.lowercase(), char.uppercase())
    }

    private fun listener() {

        binding.btnHelp.setOnSafeClickListener {
            val dialogHelp = DialogHelpWordGame()
            dialogHelp.isCanceledOnTouchOutside = true
            dialogHelp.isCancelable = true

            dialogHelp.safeShow(childFragmentManager)
        }

        binding.btnDisplayLatter.setOnSafeClickListener {
            if (isUseHelp) {
                toast(getString(R.string.you_used_your_help))
            } else {
                getLayout()
                val params = RelativeLayout.LayoutParams(squareWidth, squareWidth)
                params.addRule(RelativeLayout.CENTER_IN_PARENT)
                viewWordGameBinding.root.translationY =
                    (((screenHeight / 2).toFloat()) + squareSpace)
                viewWordGameBinding.root.translationX =
                    tRootViewPlaceList[tRootViewPlaceList.size - 1]
                viewWordGameBinding.txtLetter.layoutParams = params
                viewWordGameBinding.txtLetter.setBackgroundResource(R.drawable.shape_animal_profile_word_game)
                viewWordGameBinding.txtLetter.text = word.toCharArray()[0].toString()
                binding.rltDrawing.addView(viewWordGameBinding.root)
                isUseHelp = true
            }

        }

        binding.btnNextLevel.setOnSafeClickListener {
            binding.frameResult.gone()
            resetView()
            createQuestion()
        }
    }


    private fun resetView() {
        binding.rltDrawing.removeAllViews()
        tRootViewPlaceList = FloatArray(0)
        squareList = Array(0) { View(context) }
        isFindWord = false
        isUseHelp = false
        val data = listWord[itemPosition]

        binding.txtResult.setTextColorCompat(R.color.black)
        wordId = data.id
        word = data.word
        wordConfusion = data.wordConfusion

        binding.rltDrawing.forEach {
            it.isEnabled = true
        }
        binding.root.forEach {
            it.isEnabled = true
        }
    }

    private fun createQuestion() {
        binding.rltDrawing.post {
            context?.apply {
                screenWidth =  getScreenWidth()
                screenHeight = getScreenHeight()
                squareWidth = screenWidth / (12) // سایز هر مربع
                squareSpace = screenWidth / (11) // فاصله ی هر مربع

                locationX2 = (screenWidth - (squareSpace * 2)).toFloat()
                locationX3 = (screenWidth - (squareSpace * 3)).toFloat()
                locationX4 = (screenWidth - (squareSpace * 4)).toFloat()
                locationX5 = (screenWidth - (squareSpace * 5)).toFloat()
                locationX6 = (screenWidth - (squareSpace * 6)).toFloat()
                locationX7 = (screenWidth - (squareSpace * 7)).toFloat()
                locationX8 = (screenWidth - (squareSpace * 8)).toFloat()
                locationX9 = (screenWidth - (squareSpace * 9)).toFloat()
                locationX10 = (screenWidth - (squareSpace * 10)).toFloat()
                locationX11 = (screenWidth - (squareSpace * 11)).toFloat()
                lastLocation = ((screenWidth - (squareSpace))).toFloat()


                //تبدیل هر کلمه به یک ارایه از حروف و اد کردن آن ها به صفحه
                val currentWordList = wordConfusion.replace(" ", "").reversed().toCharArray()

                binding.txtResult.text = wordConfusion
                tRootViewPlaceList = FloatArray(currentWordList.size)
                squareList = Array(currentWordList.size) { View(context) }

                lastIndex = currentWordList.size

                currentWordList.forEachIndexed { index, c ->
                    addViewWord((index + 1), c.toString())
                }
            }


        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun addViewWord(index: Int, letter: String, isHelp: Boolean = false) {
        getLayout()

        val index_1 = index - 1

        val params = RelativeLayout.LayoutParams(squareWidth, squareWidth)
        params.addRule(RelativeLayout.CENTER_IN_PARENT)

        viewWordGameBinding.txtLetter.layoutParams = params
        viewWordGameBinding.root.translationY = (screenHeight / 2).toFloat() //تعیین مکان عمودی حروف


        when (index) {
            1 -> {
                if (isHelp) {
                    viewWordGameBinding.root.translationX = tRootViewPlaceList[index_1]
                } else {
                    viewWordGameBinding.root.translationX = locationX6
                    tRootViewPlaceList[0] = locationX6

                }
            }

            2 -> {
                if (isHelp) {
                    viewWordGameBinding.root.translationX = tRootViewPlaceList[index_1]
                } else {


                    squareList[0].translationX = locationX5

                    viewWordGameBinding.root.translationX = locationX6

                    tRootViewPlaceList[0] = locationX5
                    tRootViewPlaceList[1] = locationX6
                }
            }

            3 -> {
                squareList[0].translationX = locationX5
                squareList[1].translationX = locationX6

                viewWordGameBinding.root.translationX = locationX7

                tRootViewPlaceList[0] = locationX5
                tRootViewPlaceList[1] = locationX6
                tRootViewPlaceList[2] = locationX7
            }

            4 -> {

                squareList[0].translationX = locationX4
                squareList[1].translationX = locationX5
                squareList[2].translationX = locationX6

                viewWordGameBinding.root.translationX = locationX7



                tRootViewPlaceList[0] = locationX4
                tRootViewPlaceList[1] = locationX5
                tRootViewPlaceList[2] = locationX6
                tRootViewPlaceList[3] = locationX7
            }

            5 -> {
                squareList[0].translationX = locationX3
                squareList[1].translationX = locationX4
                squareList[2].translationX = locationX5
                squareList[3].translationX = locationX6

                viewWordGameBinding.root.translationX = locationX7

                tRootViewPlaceList[0] = locationX3
                tRootViewPlaceList[1] = locationX4
                tRootViewPlaceList[2] = locationX5
                tRootViewPlaceList[3] = locationX6
                tRootViewPlaceList[4] = locationX7
            }

            6 -> {
                squareList[0].translationX = locationX3
                squareList[1].translationX = locationX4
                squareList[2].translationX = locationX5
                squareList[3].translationX = locationX6
                squareList[4].translationX = locationX7

                viewWordGameBinding.root.translationX = locationX8

                tRootViewPlaceList[0] = locationX3
                tRootViewPlaceList[1] = locationX4
                tRootViewPlaceList[2] = locationX5
                tRootViewPlaceList[3] = locationX6
                tRootViewPlaceList[4] = locationX7
                tRootViewPlaceList[5] = locationX8
            }

            7 -> {
                squareList[0].translationX = locationX2
                squareList[1].translationX = locationX3
                squareList[2].translationX = locationX4
                squareList[3].translationX = locationX5
                squareList[4].translationX = locationX6
                squareList[5].translationX = locationX7

                viewWordGameBinding.root.translationX = locationX8

                tRootViewPlaceList[0] = locationX2
                tRootViewPlaceList[1] = locationX3
                tRootViewPlaceList[2] = locationX4
                tRootViewPlaceList[3] = locationX5
                tRootViewPlaceList[4] = locationX6
                tRootViewPlaceList[5] = locationX7
                tRootViewPlaceList[6] = locationX8

            }

            8 -> {
                squareList[0].translationX = locationX2
                squareList[1].translationX = locationX3
                squareList[2].translationX = locationX4
                squareList[3].translationX = locationX5
                squareList[4].translationX = locationX6
                squareList[5].translationX = locationX7
                squareList[6].translationX = locationX8

                viewWordGameBinding.root.translationX = locationX9

                tRootViewPlaceList[0] = locationX2
                tRootViewPlaceList[1] = locationX3
                tRootViewPlaceList[2] = locationX4
                tRootViewPlaceList[3] = locationX5
                tRootViewPlaceList[4] = locationX6
                tRootViewPlaceList[5] = locationX7
                tRootViewPlaceList[6] = locationX8
                tRootViewPlaceList[7] = locationX9
            }

            9 -> {
                squareList[0].translationX = lastLocation
                squareList[1].translationX = locationX2
                squareList[2].translationX = locationX3
                squareList[3].translationX = locationX4
                squareList[4].translationX = locationX5
                squareList[5].translationX = locationX6
                squareList[6].translationX = locationX7
                squareList[7].translationX = locationX8

                viewWordGameBinding.root.translationX = locationX9

                tRootViewPlaceList[0] = lastLocation
                tRootViewPlaceList[1] = locationX2
                tRootViewPlaceList[2] = locationX3
                tRootViewPlaceList[3] = locationX4
                tRootViewPlaceList[4] = locationX5
                tRootViewPlaceList[5] = locationX6
                tRootViewPlaceList[6] = locationX7
                tRootViewPlaceList[7] = locationX8
                tRootViewPlaceList[8] = locationX9
            }

            10 -> {
                squareList[0].translationX = lastLocation
                squareList[1].translationX = locationX2
                squareList[2].translationX = locationX3
                squareList[3].translationX = locationX4
                squareList[4].translationX = locationX5
                squareList[5].translationX = locationX6
                squareList[6].translationX = locationX7
                squareList[7].translationX = locationX8
                squareList[8].translationX = locationX9
                viewWordGameBinding.root.translationX = locationX10

                tRootViewPlaceList[0] = lastLocation
                tRootViewPlaceList[1] = locationX2
                tRootViewPlaceList[2] = locationX3
                tRootViewPlaceList[3] = locationX4
                tRootViewPlaceList[4] = locationX5
                tRootViewPlaceList[5] = locationX6
                tRootViewPlaceList[6] = locationX7
                tRootViewPlaceList[7] = locationX8
                tRootViewPlaceList[8] = locationX9
                tRootViewPlaceList[9] = locationX10
            }

            11 -> {
                squareList[0].translationX = lastLocation
                squareList[1].translationX = locationX2
                squareList[2].translationX = locationX3
                squareList[3].translationX = locationX4
                squareList[4].translationX = locationX5
                squareList[5].translationX = locationX6
                squareList[6].translationX = locationX7
                squareList[7].translationX = locationX8
                squareList[8].translationX = locationX9
                squareList[9].translationX = locationX10
                viewWordGameBinding.root.translationX = locationX11

                tRootViewPlaceList[0] = lastLocation
                tRootViewPlaceList[1] = locationX2
                tRootViewPlaceList[2] = locationX3
                tRootViewPlaceList[3] = locationX4
                tRootViewPlaceList[4] = locationX5
                tRootViewPlaceList[5] = locationX6
                tRootViewPlaceList[6] = locationX7
                tRootViewPlaceList[7] = locationX8
                tRootViewPlaceList[8] = locationX9
                tRootViewPlaceList[9] = locationX10
                tRootViewPlaceList[10] = locationX11
            }
        }

        viewWordGameBinding.root.tag = index
        viewWordGameBinding.txtLetter.text = letter
        var mActivePointerId = 0
        var mPrevX = 0
        var mPrevY = 0
        var pointerIndexMove: Int
        if (!isHelp) {
            viewWordGameBinding.root.setOnTouchListener { view, event ->

                val action = event.action

                when (action and event.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        //"ثبت مختصات اولیه نقطه تاچ شده توسط کاربر"
                        mPrevX = event.x.toInt()
                        mPrevY = event.y.toInt()
                        mActivePointerId = event.getPointerId(0)
                        //"انتقال ویو تاچ شده بالاتر از ویوهای دیگر"
                        view.bringToFront()
                    }

                    MotionEvent.ACTION_MOVE -> {

                        pointerIndexMove = event.findPointerIndex(mActivePointerId)
                        if (pointerIndexMove == 0) {
                            val currX = event.getX(pointerIndexMove)
                            val currY = event.getY(pointerIndexMove)
                            adjustTranslation(view, currX - mPrevX, currY - mPrevY)
                        }


                        val tag = view.tag as Int
                        var a: Float
                        when (tag) {
                            1 -> {
                                a = view.translationX - tRootViewPlaceList[0]
                                if (a < 0) { //به طرف چپ
                                    a *= -1
                                    if (a > squareSpace / 2) {
                                        squareList[1].translationX = tRootViewPlaceList[0]
                                        squareList[1].tag = 1 //تغییر تگ بعد از جابجایی حروف
                                        //انتقال حرف اول به مکان حرف دوم
                                        squareList[0].translationX = tRootViewPlaceList[1]
                                        squareList[0].tag = 2 //تغییر تگ بعد از جابجایی حروف
                                        squareList[0] = squareList[1] //جابجایی ویوها در ارایه ویوها
                                        squareList[1] = view //جابجایی ویوها در ارایه ویوها
                                    }
                                }
                            }

                            11 -> {
                                a = view.translationX - tRootViewPlaceList[10]
                                //شرط گذاشتیم تشخیص بده مکان حرف خالی شده یا نه
                                if (a > 0 && a > squareSpace / 2) { //به طرف راست
                                    // Toast.makeText(WordGame.this, "place is null", Toast.LENGTH_SHORT).show();
                                    //انتقال حرف نهم به مکان حرف دهم
                                    squareList[9].translationX = tRootViewPlaceList[10]
                                    squareList[9].tag = 11
                                    //انتقال حرف دهم به مکان حرف نهم
                                    squareList[10].translationX = tRootViewPlaceList[9]
                                    squareList[10].tag = 10
                                    squareList[10] = squareList[9]
                                    squareList[9] = view
                                }
                            }

                            else -> {
                                a = view.translationX - tRootViewPlaceList[tag - 1]
                                if (a > 0 && a > squareSpace / 2) { //به طرف راست
                                    squareList[tag - 2].translationX = tRootViewPlaceList[tag - 1]
                                    squareList[tag - 2].tag = tag
                                    squareList[tag - 1].translationX = tRootViewPlaceList[tag - 2]
                                    squareList[tag - 1].tag = tag - 1
                                    squareList[tag - 1] = squareList[tag - 2]
                                    squareList[tag - 2] = view
                                } else if (a < 0) { //به طرف چپ
                                    a *= -1
                                    if (a > squareSpace / 2) {
                                        if (tag == lastIndex) return@setOnTouchListener true
                                        squareList[tag].translationX = tRootViewPlaceList[tag - 1]
                                        squareList[tag].tag = tag
                                        squareList[tag - 1].translationX = tRootViewPlaceList[tag]
                                        squareList[tag - 1].tag =
                                            tag + 1
                                        squareList[tag - 1] =
                                            squareList[tag]
                                        squareList[tag] = view
                                    }
                                }
                            }
                        }

                    }

                    MotionEvent.ACTION_UP -> {

                        //انقال همه ی ویوها(مربع ها) به مکان دقیق انها

                        squareList.forEachIndexed { index, v ->
                            v.translationX = tRootViewPlaceList[index]
                            v.translationY = (screenHeight / 2).toFloat()
                        }
                        showResult()
                    }
                }




                true
            }

            squareList[index - 1] = viewWordGameBinding.root

        }


        binding.rltDrawing.addView(viewWordGameBinding.root)
    }

    private fun showResult() {
        if (!isFindWord) {
            binding.txtResult.text = ""
            var text = ""
            squareList.forEach {
                text += (it).findViewById<AppCompatTextView>(R.id.txt_letter).text.toString().trim()
            }
            binding.txtResult.text = text.reversed()

            if (text.lowercase().trim().reversed() == word.lowercase().trim()) {
                isFindWord = true

                mediaPlayer?.playSoundMediaPlayer(context, R.raw.right_crowd)
                binding.txtResult.setTextColorCompat(R.color.color_success)
                lifecycleScopeDelayTryCatch(1000){
                    binding.frameResult.visible()
                    binding.animationView.playAnimation()
                }

                binding.rltDrawing.forEach {
                    it.isEnabled = false
                }
                binding.root.forEach {
                    it.isEnabled = false
                }

                itemPosition += 1

                if (itemPosition<listWord.size) {
                    val model = listWord[itemPosition]
                    model.isOpen = true
                    model.idItem = itemPosition
                    activityViewModel.updateWordGame(model)
                }else{
                    toast(getString(R.string.game_is_finishing))
                    popBackStack()
                }


            } else {
                binding.txtResult.setTextColorCompat(R.color.error)
            }

        }
    }

    //"متدی برای جابجایی ویو موردنظر متناسب با تاچ کاربر"
    private fun adjustTranslation(view: View, deltaX: Float, deltaY: Float) {

        if (view.translationX > 1) {
            val deltaVector = floatArrayOf(deltaX, deltaY)
            view.matrix.mapVectors(deltaVector)
            view.translationX = view.translationX + deltaVector[0]
            view.translationY = view.translationY + deltaVector[1]
        }
    }
}