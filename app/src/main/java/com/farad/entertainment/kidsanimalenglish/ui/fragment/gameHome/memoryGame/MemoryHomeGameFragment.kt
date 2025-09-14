package com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.memoryGame

import android.view.LayoutInflater
import android.view.ViewGroup
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.GameLevel
import com.farad.entertainment.kidsanimalenglish.data.manager.SharedPreferencesManager
import com.farad.entertainment.kidsanimalenglish.data.model.StructMemoryScore
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentMemoryHomeGameBinding
import com.farad.entertainment.kidsanimalenglish.ui.dialog.DialogListRecordMemoryGame
import com.farad.entertainment.kidsanimalenglish.ui.dialog.DialogMemoryGameSetting
import com.farad.entertainment.kidsanimalenglish.utils.invitedFriend
import com.farad.entertainment.kidsanimalenglish.utils.safeShow
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.shareText
import org.koin.android.ext.android.inject

class MemoryHomeGameFragment : BottomNavigationFragment<FragmentMemoryHomeGameBinding>() {

    private var isMute = false
    private var typeImage = "p_"
    private var gameLevel = 1
    private var arrayScoreLevel1 = ArrayList<StructMemoryScore>()
    private val sharedPreferencesManager: SharedPreferencesManager by inject()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMemoryHomeGameBinding
        get() = FragmentMemoryHomeGameBinding::inflate

    override fun setup() {
        getTypeImage()
        listener()
        getMuteSound()
    }

    private fun getTypeImage(){
        typeImage = sharedPreferencesManager.typeImageMemory
    }
    private fun getMuteSound() {
        isMute = sharedPreferencesManager.muteSoundMemoryGame
        if (!isMute) {
            binding.btnMute.setImageResource(R.drawable.sound_icon)
        } else {
            binding.btnMute.setImageResource(R.drawable.sound_off_icon)
        }
    }

    private fun loadArrayListToPrefs() {
        arrayScoreLevel1.clear()
        val size = sharedPreferencesManager.sharedPreferencesInit?.getInt("Status_size" + gameLevel + "_", 0)
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

    private fun dialogShowMemoryRecordList() {
        loadArrayListToPrefs()
        sortListOfArrayScore()
        val dialog = DialogListRecordMemoryGame()
        dialog.isLineBottom = true
        dialog.arrayScoreLevel1 = arrayScoreLevel1
        dialog.safeShow(childFragmentManager)
    }

    private fun showDialogSetting() {
        val dialog = DialogMemoryGameSetting()
        dialog.setOnItemClickListener {
            typeImage = it
            sharedPreferencesManager.typeImageMemory=it
        }
        dialog.safeShow(childFragmentManager)
    }


    private fun listener() {

        binding.btnEasy.setOnSafeClickListener {
            val navigate =
                MemoryHomeGameFragmentDirections.actionMemoryHomeGameFragmentToMemoryBoardGameFragment(
                    GameLevel.Easy,
                    typeImage
                )
            navigate(navigate)
        }
        binding.btnMedium.setOnSafeClickListener {
            val navigate =
                MemoryHomeGameFragmentDirections.actionMemoryHomeGameFragmentToMemoryBoardGameFragment(
                    GameLevel.Medium,
                    typeImage
                )
            navigate(navigate)
        }
        binding.btnHard.setOnSafeClickListener {
            val navigate =
                MemoryHomeGameFragmentDirections.actionMemoryHomeGameFragmentToMemoryBoardGameFragment(
                    GameLevel.Hard,
                    typeImage
                )
            navigate(navigate)
        }
        binding.btnVeryHard.setOnSafeClickListener {
            val navigate =
                MemoryHomeGameFragmentDirections.actionMemoryHomeGameFragmentToMemoryBoardGameFragment(
                    GameLevel.VeryHard,
                    typeImage
                )
            navigate(navigate)
        }
        binding.btnMute.setOnSafeClickListener {
            if (isMute) {
                binding.btnMute.setImageResource(R.drawable.sound_icon)
            } else {
                binding.btnMute.setImageResource(R.drawable.sound_off_icon)
            }

            isMute = !isMute
            sharedPreferencesManager.muteSoundMemoryGame = isMute
        }
        binding.btnShare.setOnSafeClickListener {
            context?.invitedFriend()
        }
        binding.btnScore.setOnSafeClickListener {
            dialogShowMemoryRecordList()
        }
        binding.btnSetting.setOnSafeClickListener {
            showDialogSetting()

        }
    }
}