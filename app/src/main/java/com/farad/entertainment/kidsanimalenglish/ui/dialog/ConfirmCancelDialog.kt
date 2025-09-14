package com.farad.entertainment.kidsanimalenglish.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BaseDialogFragment
import com.farad.entertainment.kidsanimalenglish.databinding.DialogConfirmCancelBinding
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener

class ConfirmCancelDialog : BaseDialogFragment<DialogConfirmCancelBinding>() {


    private var textBtnLeft = ""
    private var textRightLeft = ""

    private var title = ""

    private var btnLeftListener     : (() -> Unit)? = null
    private var btnRightListener    : (() -> Unit)? = null


    fun setOnBtnLeftListener(listener: () -> Unit) {

        btnLeftListener = listener
    }

    fun setOnBtnRightListener(listener: () -> Unit){
        btnRightListener = listener
    }
    init {
        setThem(R.style.Theme_Dialog)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogConfirmCancelBinding
        get() = DialogConfirmCancelBinding::inflate

    override fun setup() {
        listener()
        initTitle()
    }

    private fun initTitle() {
        binding.btnLeft.text = textBtnLeft
        binding.btnRight.text = textRightLeft
        binding.tvTitle.text = title
    }

    private fun listener() {
        binding.btnLeft.setOnSafeClickListener {
            btnLeftListener?.invoke()
        }
        binding.btnRight.setOnSafeClickListener {
            btnRightListener?.invoke()
        }
    }

    fun setTitleDialog(text: String) {
        title = text
    }

    fun setBtnTextLeft(text: String) {
        textBtnLeft = text
    }

    fun setBtnTextRight(text: String) {
        textRightLeft = text
    }
}