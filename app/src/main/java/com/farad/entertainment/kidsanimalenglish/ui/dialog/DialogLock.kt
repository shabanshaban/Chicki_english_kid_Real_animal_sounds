package com.farad.entertainment.kidsanimalenglish.ui.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BaseDialogFragment
import com.farad.entertainment.kidsanimalenglish.databinding.DialogLockBinding
import com.farad.entertainment.kidsanimalenglish.databinding.DialogOkBinding
import com.farad.entertainment.kidsanimalenglish.utils.safeDismiss
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.toast


var IS_LOCK_FRAGMENT = false




class DialogLock(  newContext: Context) : Dialog(newContext,android.R.style.Theme_NoTitleBar_Fullscreen) {
    val binding =  DialogLockBinding.inflate(LayoutInflater.from(context), null, true)
    private var onOpenLockListener: (() -> Unit)? = null

    private var onChangeLightListener: ((Float) -> Unit)? = null


    fun setOnOpenLockListener(listener: () -> Unit) {
        onOpenLockListener = listener
    }

    fun setOnChangeLightListener(listener: (Float) -> Unit) {
        onChangeLightListener = listener
    }

    private var randomNumber = 12345




      fun setup() {
          setContentView(binding.root)
        listener()
        randomNumber = (1000..9999).random()
        binding.tvPasswordNumber.text = context.getString(R.string.password_lock, randomNumber.toString())

    }


    private fun setResult(number: String) {
        val textResult = binding.edNumber.text

        val password = "$textResult$number"
        binding.edNumber.text = password

    }

    override fun dismiss() {
        super.dismiss()
    }


    private fun listener() {

        binding.edNumber.addTextChangedListener {
            if (it.isNullOrEmpty().not()) {
                if (it.toString().length == 4) {
                    if (it.toString() != randomNumber.toString()) {
                        binding.edNumber.text = ""
                        context.toast(context.getString(R.string.the_password_is_wrong))
                    } else {
                        if (IS_LOCK_FRAGMENT) {
                          //  context. toast(context.getString(R.string.child_lock_is_disabled))
                        } else {
                            context.toast(context.getString(R.string.child_lock_is_activated  ))
                        }
                        IS_LOCK_FRAGMENT = !IS_LOCK_FRAGMENT
                        dismiss()
                        onOpenLockListener?.invoke()
                    }


                }
            }
        }


        binding.btnClear.setOnSafeClickListener {
            val number = binding.edNumber.text.toString()
            if (number.isNotEmpty()) {
                binding.edNumber.text = number.substring(0, number.length - 1)
            }
        }
        binding.btnNumber1.setOnClickListener {
            setResult(binding.btnNumber1.text.toString())
        }
        binding.btnNumber2.setOnSafeClickListener {
            setResult(binding.btnNumber2.text.toString())
        }
        binding.btnNumber3.setOnSafeClickListener {
            setResult(binding.btnNumber3.text.toString())
        }
        binding.btnNumber4.setOnClickListener {
            setResult(binding.btnNumber4.text.toString())
        }
        binding.btnNumber5.setOnClickListener {
            setResult(binding.btnNumber5.text.toString())
        }
        binding.btnNumber6.setOnClickListener {
            setResult(binding.btnNumber6.text.toString())
        }
        binding.btnNumber7.setOnClickListener {
            setResult(binding.btnNumber7.text.toString())
        }
        binding.btnNumber8.setOnClickListener {
            setResult(binding.btnNumber8.text.toString())
        }
        binding.btnNumber9.setOnClickListener {
            setResult(binding.btnNumber9.text.toString())
        }
        binding.btnNumber0.setOnClickListener {
            setResult(binding.btnNumber0.text.toString())
        }
    }


}

class DialogOk : BaseDialogFragment<DialogOkBinding>() {
    init {
        setThem(R.style.Theme_Dialog)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogOkBinding
        get() = DialogOkBinding::inflate

    override fun setup() {

        binding.btnOk.setOnSafeClickListener {
            safeDismiss()
        }
    }

}


