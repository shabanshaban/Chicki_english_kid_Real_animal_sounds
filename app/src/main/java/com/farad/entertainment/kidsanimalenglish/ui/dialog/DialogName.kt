package com.farad.entertainment.kidsanimalenglish.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BaseDialogFragment
import com.farad.entertainment.kidsanimalenglish.databinding.DialogNameBinding
import com.farad.entertainment.kidsanimalenglish.utils.safeDismiss
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener

class DialogName : BaseDialogFragment<DialogNameBinding>() {


    var userName = ""

    init {
        setThem(R.style.Theme_Dialog)
    }

    private var onSaveNameListener: ((String) -> Unit)? = null
    fun onSaveNameListener(listener: (String) -> Unit) {
        onSaveNameListener = listener
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogNameBinding
        get() = DialogNameBinding::inflate

    override fun setup() {
        listener()
    }

    private fun listener() {

        binding.edName.setText(userName)

        binding.btnEasy.setOnSafeClickListener {
            if (binding.edName.text.toString().trim().isNotEmpty()) {
                onSaveNameListener?.invoke(binding.edName.text.toString().trim())
                safeDismiss()
            }

        }
    }
}