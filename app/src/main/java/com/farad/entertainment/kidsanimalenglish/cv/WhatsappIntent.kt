package com.farad.entertainment.kidsanimalenglish.cv

import android.app.Dialog
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.databinding.DialogLockBinding
import com.farad.entertainment.kidsanimalenglish.databinding.DialogOkBinding
import com.farad.entertainment.kidsanimalenglish.databinding.ViewWhatsappBinding
import com.farad.entertainment.kidsanimalenglish.ui.dialog.IS_LOCK_FRAGMENT
import com.farad.entertainment.kidsanimalenglish.utils.intentToWhatsapp
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.toast

class WhatsappIntent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private val binding = ViewWhatsappBinding.inflate(LayoutInflater.from(context), this, true)


    init {
        listener()
    }

    private fun listener() {

        binding.lineWhatsapp.setOnSafeClickListener {
            showDialogLock()
        }
    }


}

fun ViewGroup.showDialogLock(action:(()->Unit)?=null) {
    val dialogName = Dialog(this.context, android.R.style.Theme_NoTitleBar_Fullscreen)
    val dialogBinding = DialogLockBinding.inflate(LayoutInflater.from(this.context), this, false)
    dialogName.setContentView(dialogBinding.root)


    val randomNumber: Int = (1000..9999).random()

    dialogBinding.tvPasswordNumber.text = context.getString(R.string.password_lock, randomNumber.toString())

    dialogBinding.edNumber.addTextChangedListener {
        if (it.isNullOrEmpty().not()) {
            if (it.toString().length == 4) {
                if (it.toString() != randomNumber.toString()) {
                    dialogBinding.edNumber.text = ""
                    context.toast(context.getString(R.string.the_password_is_wrong))
                } else {
                    if (IS_LOCK_FRAGMENT) {
                       // context.toast(context.getString(R.string.child_lock_is_disabled))
                    } else {
                      //  context.toast(context.getString(R.string.child_lock_is_activated))
                    }
                    IS_LOCK_FRAGMENT = !IS_LOCK_FRAGMENT
                    dialogName.dismiss()

                        if (action==null){
                            context.intentToWhatsapp()
                        }else{
                            action()
                        }

                    val dialogOk = Dialog(context, R.style.Theme_Dialog)
                    val dialogOkBinding =
                        DialogOkBinding.inflate(LayoutInflater.from(context), this, false)
                    dialogOk.setContentView(dialogOkBinding.root)
                    dialogOkBinding.btnOk.setOnSafeClickListener {
                        dialogOk.dismiss()
                    }
                    //   dialogOk.show()

                }


            }
        }
    }


    dialogBinding.btnClear.setOnSafeClickListener {
        val number = dialogBinding.edNumber.text.toString()
        if (number.isNotEmpty()) {
            dialogBinding.edNumber.text = number.substring(0, number.length - 1)
        }
    }
    dialogBinding.btnNumber1.setOnClickListener {
        dialogBinding.edNumber.setResult(dialogBinding.btnNumber1.text.toString())
    }
    dialogBinding.btnNumber2.setOnSafeClickListener {
        dialogBinding.edNumber.setResult(dialogBinding.btnNumber2.text.toString())
    }
    dialogBinding.btnNumber3.setOnSafeClickListener {
        dialogBinding.edNumber.setResult(dialogBinding.btnNumber3.text.toString())
    }
    dialogBinding.btnNumber4.setOnClickListener {
        dialogBinding.edNumber.setResult(dialogBinding.btnNumber4.text.toString())
    }
    dialogBinding.btnNumber5.setOnClickListener {
        dialogBinding.edNumber.setResult(dialogBinding.btnNumber5.text.toString())
    }
    dialogBinding.btnNumber6.setOnClickListener {
        dialogBinding.edNumber.setResult(dialogBinding.btnNumber6.text.toString())
    }
    dialogBinding.btnNumber7.setOnClickListener {
        dialogBinding.edNumber.setResult(dialogBinding.btnNumber7.text.toString())
    }
    dialogBinding.btnNumber8.setOnClickListener {
        dialogBinding.edNumber.setResult(dialogBinding.btnNumber8.text.toString())
    }
    dialogBinding.btnNumber9.setOnClickListener {
        dialogBinding.edNumber.setResult(dialogBinding.btnNumber9.text.toString())
    }
    dialogBinding.btnNumber0.setOnClickListener {
        dialogBinding.edNumber.setResult(dialogBinding.btnNumber0.text.toString())
    }



    dialogName.show()

}

private fun TextView.setResult(number: String) {
    val textResult = text

    val password = "$textResult$number"
    text = password

}