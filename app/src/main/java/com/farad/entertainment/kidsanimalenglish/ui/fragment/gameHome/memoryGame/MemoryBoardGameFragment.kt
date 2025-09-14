package com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.memoryGame

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.navigation.fragment.navArgs
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.AnimalModel
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.GameLevel
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.getListData
import com.farad.entertainment.kidsanimalenglish.data.manager.SharedPreferencesManager
import com.farad.entertainment.kidsanimalenglish.data.model.StructMemoryScore
import com.farad.entertainment.kidsanimalenglish.databinding.DialogSaveRecourdMemoryBinding
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentMemoryBoardGameBinding
import com.farad.entertainment.kidsanimalenglish.ui.dialog.DialogFinishMemoryGame
import com.farad.entertainment.kidsanimalenglish.ui.dialog.DialogListRecordMemoryGame
import com.farad.entertainment.kidsanimalenglish.utils.animClick
import com.farad.entertainment.kidsanimalenglish.utils.getImageDrawableByName
import com.farad.entertainment.kidsanimalenglish.utils.playSoundMediaPlayer
import com.farad.entertainment.kidsanimalenglish.utils.safeDismiss
import com.farad.entertainment.kidsanimalenglish.utils.safeShow
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel
import com.farad.entertainment.kidsanimalenglish.utils.lifecycleScopeDelayTryCatch
import org.koin.android.ext.android.inject
import java.util.Random
import java.util.Timer
import java.util.TimerTask

class MemoryBoardGameFragment : BottomNavigationFragment<FragmentMemoryBoardGameBinding>() {
    private var columnNum = 3
    private var rowNum = 4
    private var imgArray = ArrayList<ImageView>()

    private var questionArray = ArrayList<Int>()
    private var questionPlacesArray = ArrayList<Int>()

    private val listAllAnimal = ArrayList<AnimalModel>()

    private var hasStart = true

    private var timer: Timer? = null

    private var canClick = true

    private var timeSec = 0
    private var clickNumber = 0


    private var tag1: String? = null
    private var tag2: String? = null

    private var imgTemp: ImageView? = null

    private var correctNumber = 0

    private var score = 0

    private var mediaPlayer: MediaPlayer? = null

    private val sharedPreferencesManager: SharedPreferencesManager by inject()

    private val arrayScoreLevel1 = ArrayList<StructMemoryScore>()

    private var gameLevel = 1
    var name = ""


    private var typeImage = "p_"

    private val args by navArgs<MemoryBoardGameFragmentArgs>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMemoryBoardGameBinding
        get() = FragmentMemoryBoardGameBinding::inflate

    override fun setup() {
        getArgument()
        getAllAnimal()
        listener()
        loadView()
        createQuestion()
        initMediaPlayer()

    }


    private fun getArgument() {
        binding.tvLevelGame.text = getString(R.string.game_level_s, args.gameLevl)
        typeImage = args.typeImage
        when (args.gameLevl) {
            GameLevel.Easy -> {
                columnNum = 3
                rowNum = 4
                gameLevel = 1
            }

            GameLevel.Medium -> {
                rowNum = 5
                columnNum = 4
                gameLevel = 2
            }

            GameLevel.Hard -> {
                rowNum = 7
                columnNum = 6
                gameLevel = 3
            }

            GameLevel.VeryHard -> {
                rowNum = 10
                columnNum = 7
                gameLevel = 4
            }

        }

    }

    private fun showDialogName() {
        val dialogName = context?.let { Dialog(it) }
        val dialogBinding = DialogSaveRecourdMemoryBinding.inflate(layoutInflater)
        dialogName?.setContentView(dialogBinding.root)
        dialogName?.apply {
            setCanceledOnTouchOutside(false)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val wlp = window?.attributes

            wlp?.gravity = Gravity.BOTTOM
            wlp?.let {
                wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
                window?.attributes = wlp
                window?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }

            dialogBinding.edtName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (count > 10) {
                        dialogBinding.edtName.filters = arrayOf<InputFilter>(LengthFilter(10))
                    }
                }

                override fun afterTextChanged(s: Editable) {}
            })
            dialogBinding.txtExplainRecord.text = score.toString() + ""

            dialogBinding.btnSave.setOnSafeClickListener {

                name = dialogBinding.edtName.text.toString()
                if (name == "") {
                    name = getString(R.string.memory_game_un_name)
                }
                saveScores()

                dialogShowMemoryRecordList()
                dialogName.cancel()
                dialogName.dismiss()
            }
            dialogBinding.btnCancel.setOnSafeClickListener {
                dialogName.dismiss()
                popBackStack()
            }
        }


        dialogName?.show()
    }

    private fun dialogShowMemoryRecordList() {
        loadArrayListToPrefs()
        sortListOfArrayScore()
        val dialog = DialogListRecordMemoryGame()
        dialog.isLineBottom = false
        dialog.arrayScoreLevel1 = arrayScoreLevel1
        dialog.setOnBackPressedListener {
            popBackStack()
        }
        dialog.safeShow(childFragmentManager)
    }

    private fun saveScores() {
        loadArrayListToPrefs()
        sortListOfArrayScore()
        if (arrayScoreLevel1.size < 10) {
            if (score != 0) {
                val scoreLevel1 = StructMemoryScore()
                scoreLevel1.score = score
                scoreLevel1.name = name
                scoreLevel1.time = timeSec
                arrayScoreLevel1.add(scoreLevel1)
            }
        } else {
            if (score < arrayScoreLevel1[arrayScoreLevel1.size - 1].score && score != 0) {
                val scoreLevel1 = StructMemoryScore()
                scoreLevel1.score = score
                scoreLevel1.name = name
                scoreLevel1.time = timeSec
                arrayScoreLevel1[arrayScoreLevel1.size - 1] = scoreLevel1
            } else {
                if (score == arrayScoreLevel1[arrayScoreLevel1.size - 1].score && score != 0) {
                    if (arrayScoreLevel1[arrayScoreLevel1.size - 1].time <= timeSec) {
                    } else {
                        val scoreLevel1 = StructMemoryScore()
                        scoreLevel1.score = score
                        scoreLevel1.name = name
                        scoreLevel1.time = timeSec
                        arrayScoreLevel1[arrayScoreLevel1.size - 1] = scoreLevel1
                    }
                }
            }
        }
        sortListOfArrayScore()
        saveArrayListInPrefs(arrayScoreLevel1)
    }

    private fun sortListOfArrayScore() {
        for (i in 0 until arrayScoreLevel1.size - 1) {
            for (j in i + 1 until arrayScoreLevel1.size) {
                if (arrayScoreLevel1[i].score < arrayScoreLevel1[j].score) {
                } else {
                    if (arrayScoreLevel1[i].score > arrayScoreLevel1[j].score) {
                        val temp = arrayScoreLevel1[j]
                        arrayScoreLevel1[j] = arrayScoreLevel1[i]
                        arrayScoreLevel1[i] = temp
                    } else {
                        if (arrayScoreLevel1[i].score == arrayScoreLevel1[j].score) {
                            val timeI = arrayScoreLevel1[i].time
                            val timeJ = arrayScoreLevel1[j].time
                            if (timeI <= timeJ) {
                            } else {
                                val temp = arrayScoreLevel1[j]
                                arrayScoreLevel1[j] = arrayScoreLevel1[i]
                                arrayScoreLevel1[i] = temp
                            }
                        }
                    }
                }
            }
        }
    }

    private fun saveArrayListInPrefs(arrayMethodScoreLevel1: ArrayList<StructMemoryScore>) {
        sharedPreferencesManager.sharedPreferencesInit?.apply {
            edit().apply {

                putInt("Status_size" + gameLevel + "_", arrayMethodScoreLevel1.size)
                arrayMethodScoreLevel1.forEachIndexed { i, structMemoryScore ->
                    remove("Status" + gameLevel + "_" + i)
                    putInt("Status" + gameLevel + "_" + i, arrayMethodScoreLevel1[i].score)
                    remove("StatusName" + gameLevel + "_" + i)
                    putString("StatusName" + gameLevel + "_" + i, arrayMethodScoreLevel1[i].name)
                    remove("StatusTime" + gameLevel + "_" + i)
                    putInt("StatusTime" + gameLevel + "_" + i, arrayMethodScoreLevel1[i].time)
                }

                apply()
            }
        }
    }

    private fun loadArrayListToPrefs() {
        arrayScoreLevel1.clear()
        val size =
            sharedPreferencesManager.sharedPreferencesInit?.getInt(
                "Status_size" + gameLevel + "_",
                0
            )
        size?.let {
            for (i in 0 until size) {
                sharedPreferencesManager.sharedPreferencesInit?.apply {
                    val score = getInt("Status" + gameLevel + "_" + i, 30000)
                    val name = getString("StatusName" + gameLevel + "_" + i, "")
                    val time = getInt("StatusTime" + gameLevel + "_" + i, 0)
                    arrayScoreLevel1.add(StructMemoryScore(score, time, name.toString()))
                }
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer?.cancel()
        timer?.purge()
        timer = null
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer()
    }

    private fun getAllAnimal() {
        listAllAnimal.clear()
        context?.getListData()?.let {
            listAllAnimal.addAll(it)
        }
    }

    private fun listener() {
    }

    private fun loadView() {
        context?.let { context ->
            for (i in 0 until rowNum) {
                val lnrHorizontal = LinearLayout(context)
                lnrHorizontal.orientation = LinearLayout.HORIZONTAL
                lnrHorizontal.layoutParams =
                    LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                lnrHorizontal.gravity = Gravity.CENTER
                binding.lnrLevel.addView(lnrHorizontal)
                for (j in 0 until columnNum) {
                    val img = ShapeableImageView(context)
                    img.adjustViewBounds = true
                    val shapeAppearanceModel = ShapeAppearanceModel.builder()
                        .setAllCorners(CornerFamily.ROUNDED, 38f)
                        .build()
                    img.shapeAppearanceModel = shapeAppearanceModel
                    val imgParams =
                        LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT)
                    imgParams.weight = 1.0f
                    imgParams.setMargins(2, 2, 2, 2)
                    img.layoutParams = imgParams
                    img.setImageResource(R.drawable.icon_memory_unseen)
                    onClick(img)
                    lnrHorizontal.addView(img)
                    imgArray.add(img)

                }
            }
        }

    }

    private fun createQuestion() {
        val imgNum = rowNum * columnNum
        questionArray = generateRandomArray(1, listAllAnimal.size, imgNum / 2)
        questionPlacesArray = generateRandomArray(0, imgNum - 1, imgNum)
        for (i in imgArray.indices) {
            imgArray[questionPlacesArray[i]].tag = questionArray[i / 2]
        }
    }

    private fun generateRandomArray(min: Int, max: Int, number: Int): java.util.ArrayList<Int> {
        val randomNumber = ArrayList<Int>()
        randomNumber.clear()
        val ran = Random()
        randomNumber.add(0, ran.nextInt(max - min + 1) + min)
        var i = 1
        while (i < number) {
            randomNumber.add(i, ran.nextInt(max - min + 1) + min)
            var j = 0
            while (j < i) {
                if (randomNumber[i] == randomNumber[j]) {
                    randomNumber.removeAt(i)
                    i--
                    j = 0
                }
                j++
            }
            i++
        }
        return randomNumber
    }

    private fun counter(): Int {
        hasStart = false
        timer?.cancel()
        timer?.purge()
        timer = null
        timer = Timer()
        timer?.schedule(object :TimerTask(){
            override fun run() {
                timeSec++
            }

        },1000,1000)

        return timeSec
    }

    private fun onClick(img1: AppCompatImageView) {
        val imgClick = View.OnClickListener { v ->
            if (hasStart) {
                counter()
            }
            if (canClick) {
                context?.getImageDrawableByName(typeImage + v.tag)?.let {
                    img1.setImageResource(it)
                }

                clickNumber++
                if (clickNumber == 1) {
                    tag1 = v.tag.toString()
                    imgTemp = img1
                    img1.isClickable = false
                }
                if (clickNumber == 2) {
                    tag2 = v.tag.toString()
                    if (tag1 === tag2) {
                        //CORRECT ANSWER
                        imgTemp?.isClickable = false
                        v.isClickable = false
                        if (sharedPreferencesManager.muteSoundMemoryGame.not())
                            mediaPlayer?.playSoundMediaPlayer(context, R.raw.memory_game_success)

                        correctNumber++

                        //vibrate
                        imgTemp?.animClick()
                        img1.animClick()
                        if (correctNumber == rowNum * columnNum / 2) {
                            dialogFinishMemoryGame()

                            if (sharedPreferencesManager.muteSoundMemoryGame.not())
                                mediaPlayer?.playSoundMediaPlayer(context, R.raw.memory_game_win)
                            timer?.cancel()
                        }
                    } else {
                        canClick = false

                        lifecycleScopeDelayTryCatch(300){
                            imgTemp?.setImageResource(R.drawable.icon_memory_seen)
                            img1.setImageResource(R.drawable.icon_memory_seen)
                            imgTemp?.isClickable = true
                            canClick = true
                        }


                    }
                    score++
                    clickNumber = 0
                }
            }
        }
        img1.setOnClickListener(imgClick)
    }

    private fun dialogFinishMemoryGame() {
        val dialog = DialogFinishMemoryGame()
        dialog.level = getString(args.gameLevl.value)
        dialog.time = timeSec
        dialog.numberMoving = score
        dialog.safeShow(childFragmentManager)
        dialog.setOnBackPressedListener {
            dialog.safeDismiss()
            showDialogName()
        }
    }
}