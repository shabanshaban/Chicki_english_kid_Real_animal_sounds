package com.farad.entertainment.kidsanimalenglish.ui.dialog

import android.text.SpannableString
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.GravityInt
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BaseDialogFragment
import com.farad.entertainment.kidsanimalenglish.databinding.DialogMessageBinding
import com.farad.entertainment.kidsanimalenglish.utils.isNull
import com.farad.entertainment.kidsanimalenglish.utils.safeDismiss
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.visibleOrGone

class MessageDialog : BaseDialogFragment<DialogMessageBinding>() {


    private var text = ""
    private var spannableString :SpannableString ?= null
    private var headerText = ""
    private var isVisibleBtnOk = true
      var isCancelableDialog = true
      var isCanceledOnTouchOutsideDialog = true
    private var textGravity=Gravity.CENTER

    init {
        setThem(R.style.Theme_Dialog)
        this.isCancelable = isCancelableDialog
        this.isCanceledOnTouchOutside = isCanceledOnTouchOutsideDialog
    }

    private var onItemOkClickListener: (() -> Unit)? = null
    fun setOnItemClickListener(listener: () -> Unit) {
        onItemOkClickListener = listener
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogMessageBinding
        get() = DialogMessageBinding::inflate

    override fun setup() {

        binding.tvTitle.gravity=textGravity
        binding.iconMessage     .visibleOrGone(headerText.isEmpty())
        binding.tvHeader        .visibleOrGone(headerText.isNotEmpty())
        binding.tvTitle.text = if (spannableString.isNull()) text else spannableString
        binding.tvHeader.text   = headerText
        binding.btnOk           .visibleOrGone(isVisibleBtnOk)
        binding.btnOk           .setOnSafeClickListener {
            safeDismiss()
            onItemOkClickListener?.invoke()
        }
    }



      fun setTextGravity(@GravityInt gravity: Int){
        textGravity=gravity
    }

    fun setHeaderText(message: String) {
        headerText = message
    }

    fun setTextDialog(message: String) {
        text = message
    }
    fun setTextDialog(message: SpannableString) {
        spannableString = message
    }

    fun setVisibleBtnOk(isVisibility: Boolean) {
        isVisibleBtnOk = isVisibility

    }
}