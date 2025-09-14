package com.farad.entertainment.kidsanimalenglish.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BaseDialogFragment
import com.farad.entertainment.kidsanimalenglish.databinding.DialogLoadingBinding


class DialogLoading : BaseDialogFragment<DialogLoadingBinding>() {

    private var setOnExitClickListener: (() -> Unit)? = null

    fun setOnExitClickListener(listener: () -> Unit) {
        setOnExitClickListener = listener
    }


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogLoadingBinding
        get() = DialogLoadingBinding::inflate

    init {

        setThem(android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }

    override fun setup() {


    }



}