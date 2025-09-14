package com.farad.entertainment.kidsanimalenglish.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BaseDialogFragment
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.GameLevel
import com.farad.entertainment.kidsanimalenglish.databinding.DialogSelectLevelBinding
import com.farad.entertainment.kidsanimalenglish.utils.safeDismiss
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener

class DialogSelectLevel : BaseDialogFragment<DialogSelectLevelBinding>() {

    private var gameLevel = GameLevel.Easy

    private var onSelectItemClickListener: ((GameLevel) -> Unit)? = null
    fun onSelectItemClickListener(listener: (GameLevel) -> Unit) {
        onSelectItemClickListener=listener
    }

    init {
        setThem(R.style.Theme_Dialog)
    }
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogSelectLevelBinding
        get() = DialogSelectLevelBinding::inflate

    override fun setup() {

        listener()
    }

    private fun listener() {
        binding.btnEasy.setOnSafeClickListener {
            gameLevel = GameLevel.Easy
            onSelectItemClickListener?.invoke(gameLevel)
            safeDismiss()
        }
        binding.btnMedium.setOnSafeClickListener {
            gameLevel = GameLevel.Medium
            onSelectItemClickListener?.invoke(gameLevel)
            safeDismiss()
        }
        binding.btnHard.setOnSafeClickListener {
            gameLevel = GameLevel.Hard
            onSelectItemClickListener?.invoke(gameLevel)
            safeDismiss()
        }
    }


}