package com.farad.entertainment.kidsanimalenglish.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BaseDialogFragment
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.TypeGameBubble
import com.farad.entertainment.kidsanimalenglish.databinding.DialogStartGameBubbleBinding
import com.farad.entertainment.kidsanimalenglish.utils.safeDismiss
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener

class DialogStartGameBubble : BaseDialogFragment<DialogStartGameBubbleBinding>() {



    init {

        setThem(R.style.Theme_Dialog2)
    }

    private var typeGame = TypeGameBubble.SOUND_ANIMAL

    private var onItemStartClickListener: ((TypeGameBubble) -> Unit)? = null
    private var onItemExitClickListener: (() -> Unit)? = null

    fun setOnItemStartClickListener(listener: (TypeGameBubble) -> Unit) {
        onItemStartClickListener = listener
    }

    fun setOnItemExitClickListener(listener: () -> Unit) {
        onItemExitClickListener = listener
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogStartGameBubbleBinding
        get() = DialogStartGameBubbleBinding::inflate

    override fun setup() {
        listener()



    }

    private fun listener() {
        binding.btnStart.setOnSafeClickListener {
            onItemStartClickListener?.invoke(typeGame)
            safeDismiss()
        }
        binding.btnExit.setOnSafeClickListener {
            onItemExitClickListener?.invoke()
            safeDismiss()
        }

        binding.radioGrp.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.farsiName -> {
                    typeGame = TypeGameBubble.FARSI_NAME
                }

                R.id.englishName -> {
                    typeGame = TypeGameBubble.ENGLISH_NAME
                }

                R.id.animalSound -> {
                    typeGame = TypeGameBubble.SOUND_ANIMAL
                }

            }
        }
    }
}