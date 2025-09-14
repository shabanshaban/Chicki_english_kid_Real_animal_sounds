package com.farad.entertainment.kidsanimalenglish.ui.fragment.home.painting.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.farad.entertainment.kidsanimalenglish.base.BaseBottomSheetDialogFragment
import com.farad.entertainment.kidsanimalenglish.databinding.DialogSizePickerBinding
import com.farad.entertainment.kidsanimalenglish.utils.safeDismiss
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener

class BottomSheetPaintingSizePicker : BaseBottomSheetDialogFragment<DialogSizePickerBinding>() {

    var brushSize = 0

    private var onChangeSizeListener: ((Int) -> Unit)? = null

    fun setOnChangeSizeListener(listener: (Int) -> Unit) {
        onChangeSizeListener = listener
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogSizePickerBinding
        get() = DialogSizePickerBinding::inflate

    override fun setup() {
        listener()
    }


    private fun listener() {

        binding.sliderBrush.value = brushSize.toFloat()

        binding.sliderBrush.addOnChangeListener { slider, value, fromUser ->
            brushSize = value.toInt()
            onChangeSizeListener?.invoke(brushSize)
        }

        binding.btnClose.setOnSafeClickListener {
            safeDismiss()
        }
    }

}