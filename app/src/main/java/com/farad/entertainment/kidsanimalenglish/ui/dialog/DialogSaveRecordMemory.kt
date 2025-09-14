package com.farad.entertainment.kidsanimalenglish.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import com.farad.entertainment.kidsanimalenglish.base.BaseDialogFragment
import com.farad.entertainment.kidsanimalenglish.databinding.DialogSaveRecourdMemoryBinding

class DialogSaveRecordMemory : BaseDialogFragment<DialogSaveRecourdMemoryBinding>()  {
    init {

        initDialog()
    }
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogSaveRecourdMemoryBinding
        get() = DialogSaveRecourdMemoryBinding::inflate

    override fun setup() {


    }

    private fun initDialog(){

        this.dialog?.apply {

            setCanceledOnTouchOutside(false)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val wlp = window?.attributes

            wlp?.gravity = Gravity.BOTTOM
            wlp?.let {
                wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
                window?.attributes = wlp
                window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            }

        }

    }

}