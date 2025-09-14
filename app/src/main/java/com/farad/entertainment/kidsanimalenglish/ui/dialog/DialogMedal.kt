package com.farad.entertainment.kidsanimalenglish.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BaseDialogFragment
import com.farad.entertainment.kidsanimalenglish.data.manager.SharedPreferencesManager
import com.farad.entertainment.kidsanimalenglish.databinding.DialogMedalPuzzleBinding
import com.farad.entertainment.kidsanimalenglish.utils.gone
import com.farad.entertainment.kidsanimalenglish.utils.shakeAnimation
import com.farad.entertainment.kidsanimalenglish.utils.toFormatTime
import com.farad.entertainment.kidsanimalenglish.utils.visible
import org.koin.android.ext.android.inject

class DialogMedal : BaseDialogFragment<DialogMedalPuzzleBinding>() {

    private val sharedPreferencesManager: SharedPreferencesManager by inject()

    init {
        setThem(android.R.style.Theme_Translucent_NoTitleBar)
    }

    private var onSaveNameListener: ((String) -> Unit)? = null
    fun onSaveNameListener(listener: (String) -> Unit) {
        onSaveNameListener = listener
    }

    var timeGameNow = 0
    var countMoving = 0

      var isAnimation=false
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogMedalPuzzleBinding
        get() = DialogMedalPuzzleBinding::inflate

    override fun setup() {
        listener()
        initView()
    }



    private fun initView() {
        if (isAnimation){
            binding.timerUserNow        .shakeAnimation()
            binding.tvCountMovementNow  .shakeAnimation()
            isAnimation=false
        }

        binding.timerUserNow.text = getString(R.string.time_s, timeGameNow.toLong().toFormatTime())
        binding.tvCountMovementNow.text = getString(R.string.movement_s, countMoving.toString())
        if (timeGameNow == 0 && countMoving == 0) {
            binding.timerUserNow.text       = getString(R.string.time)
            binding.tvCountMovementNow.text = getString(R.string.movement)
        }

        binding.tvMovementEasy.text =
            getString(R.string.movement_s, sharedPreferencesManager.recordMovementLevel1.toString())
        binding.tvTimeEasy.text =
            getString(R.string.time_s, sharedPreferencesManager.recordTimeLevel1.toFormatTime())

        binding.tvMediumMovement.text =
            getString(R.string.movement_s, sharedPreferencesManager.recordMovementLevel2.toString())
        binding.tvTimeMedium.text =
            getString(R.string.time_s, sharedPreferencesManager.recordTimeLevel2.toFormatTime())

        binding.tvMovementHard.text =
            getString(R.string.movement_s, sharedPreferencesManager.recordMovementLevel3.toString())
        binding.tvTimeHard.text =
            getString(R.string.time_s, sharedPreferencesManager.recordTimeLevel3.toFormatTime())

        if (sharedPreferencesManager.recordTimeLevel1 == 0L) {
            binding.tvNoRecordEasy.visible()
            binding.groupEasy.gone()
        } else {
            binding.groupEasy.visible()
            binding.tvNoRecordEasy.gone()
        }
        if (sharedPreferencesManager.recordTimeLevel2 == 0L) {
            binding.tvNoRecordMedium.visible()
            binding.groupMedium.gone()
        } else {
            binding.groupMedium.visible()
            binding.tvNoRecordMedium.gone()
        }
        if (sharedPreferencesManager.recordTimeLevel3 == 0L) {
            binding.tvNoRecordHard.visible()
            binding.groupHard.gone()
        } else {
            binding.groupHard.visible()
            binding.tvNoRecordHard.gone()
        }
    }

    private fun listener() {

    }
}