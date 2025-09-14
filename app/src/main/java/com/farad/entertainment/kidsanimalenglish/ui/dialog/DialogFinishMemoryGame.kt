package com.farad.entertainment.kidsanimalenglish.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.farad.entertainment.kidsanimalenglish.base.BaseDialogFragment
import com.farad.entertainment.kidsanimalenglish.databinding.DialogFinishMemoryGameBinding

class DialogFinishMemoryGame : BaseDialogFragment<DialogFinishMemoryGameBinding>() {

    var time = 0
    var numberMoving = 0
    var level = ""

    init {
        setThem(android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogFinishMemoryGameBinding
        get() = DialogFinishMemoryGameBinding::inflate

    override fun setup() {

        binding.txtTimeText.text = getDurationString(time)
        binding.txtScoreText.text = numberMoving.toString()
        binding.txtLevelText.text = level
    }

    private fun getDurationString(seconds: Int): String {
        val hours = seconds / 3600
        val minutes = seconds % 3600 / 60
        val newSeconds = seconds % 60
        return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : " + twoDigitString(
            newSeconds
        )
    }

    private fun twoDigitString(number: Int): String {
        if (number == 0) {
            return "00"
        }
        return if (number / 10 == 0) {
            "0$number"
        } else number.toString()
    }
}