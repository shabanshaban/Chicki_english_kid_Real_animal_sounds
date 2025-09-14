package com.farad.entertainment.kidsanimalenglish.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BaseDialogFragment
import com.farad.entertainment.kidsanimalenglish.databinding.DialogInfoScratchBinding
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.setTintColorResource

class DialogInfoScratch : BaseDialogFragment<DialogInfoScratchBinding>() {


    var rating = 0f

    var isSuccess = false

    init {
        setThem(R.style.Theme_Dialog)
    }

    private var onNextListener: (() -> Unit)? = null
    fun setOnNextBtnListener(listener: () -> Unit) {
        onNextListener = listener
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogInfoScratchBinding
        get() = DialogInfoScratchBinding::inflate

    override fun setup() {
        listener()
        initView()
    }

    private fun initView() {
        binding.ratingStar.rating = rating
        if (isSuccess) {
            binding.tvTitle.text = getString(R.string.bravooooooooo)

            binding.imageHeader.setTintColorResource(R.color.green_color_picker)
            binding.btnNext.setBackgroundResource(R.drawable.bg_ripple_shape_chose_3)
        } else {

            if (rating==5f){
                binding.ratingStar.rating=0f
            }
            binding.imageHeader.setTintColorResource(R.color.chose2)
            binding.btnNext.setBackgroundResource(R.drawable.bg_ripple_shape_chose_2)
            binding.tvTitle.text = getString(R.string.your_answer_was_wrong)

        }

    }

    private fun listener() {


        binding.btnNext.setOnSafeClickListener {
            onNextListener?.invoke()
        }

    }
}