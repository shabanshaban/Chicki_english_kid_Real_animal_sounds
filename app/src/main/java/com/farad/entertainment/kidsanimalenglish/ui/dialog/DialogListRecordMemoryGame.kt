package com.farad.entertainment.kidsanimalenglish.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BaseDialogFragment
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.GameLevel
import com.farad.entertainment.kidsanimalenglish.data.manager.SharedPreferencesManager
import com.farad.entertainment.kidsanimalenglish.data.model.StructMemoryScore
import com.farad.entertainment.kidsanimalenglish.databinding.DialogRecordMemoryGameBinding
import com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.memoryGame.ListRecordMemoryGameAdapter
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.visibleOrGone
import org.koin.android.ext.android.inject

class DialogListRecordMemoryGame : BaseDialogFragment<DialogRecordMemoryGameBinding>() {


    var arrayScoreLevel1 = ArrayList<StructMemoryScore>()

    init {
        setThem(android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }

    var isLineBottom = false

    private var gameLevel = 1
    private val sharedPreferencesManager: SharedPreferencesManager by inject()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogRecordMemoryGameBinding
        get() = DialogRecordMemoryGameBinding::inflate

    override fun setup() {
        initRecyclerview()
        binding.linBottom.visibleOrGone(isLineBottom)
        binding.txtRankingCaption.text = getString(R.string.game_level_s,checkLevel(gameLevel))
        binding.btnBack.setOnSafeClickListener {
            if (gameLevel > 1) {
                gameLevel--
            } else gameLevel = 4

            loadArrayListToPrefs()
            sortListOfArrayScore()

            binding.txtRankingCaption.text = getString(R.string.game_level_s,checkLevel(gameLevel))
            initRecyclerview()

        }
        binding.txtForward.setOnSafeClickListener {
            if (gameLevel < 4) {
                gameLevel++
            } else gameLevel = 1

            binding.txtRankingCaption.text = getString(R.string.game_level_s,checkLevel(gameLevel))
            loadArrayListToPrefs()
            sortListOfArrayScore()
            initRecyclerview()

        }
    }

    private fun checkLevel(gameLevel: Int): String {

       return when (gameLevel) {
            1 -> {
                  getString(GameLevel.Easy.value)
            }

            2 -> {
                  getString(GameLevel.Medium.value)
            }

            3 -> {
                  getString(GameLevel.Hard.value)
            }

            4 -> {
                  getString(GameLevel.VeryHard.value)
            }

           else -> {
               return ""
           }
       }
    }

    private fun loadArrayListToPrefs() {
        arrayScoreLevel1.clear()
        val size = sharedPreferencesManager.sharedPreferencesInit?.getInt(
            "Status_size" + gameLevel + "_",
            0
        )
        size?.let {
            for (i in 0 until 10) {
                sharedPreferencesManager.sharedPreferencesInit?.apply {
                    val score = getInt("Status" + gameLevel + "_" + i, 30000)
                    val name = getString("StatusName" + gameLevel + "_" + i, "_")
                    val time = getInt("StatusTime" + gameLevel + "_" + i, 0)
                    arrayScoreLevel1.add(StructMemoryScore(score, time, name.toString()))
                }
            }

        }

    }

    private fun sortListOfArrayScore() {
        for (i in 0 until arrayScoreLevel1.size) {
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

    private fun initRecyclerview() {
        binding.recyclerView.adapter = ListRecordMemoryGameAdapter().apply {

            submitList(arrayScoreLevel1)
        }
    }


}