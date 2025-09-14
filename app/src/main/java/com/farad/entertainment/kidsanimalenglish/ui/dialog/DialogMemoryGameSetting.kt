package com.farad.entertainment.kidsanimalenglish.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BaseDialogFragment
import com.farad.entertainment.kidsanimalenglish.data.manager.SharedPreferencesManager
import com.farad.entertainment.kidsanimalenglish.databinding.DialogMemoryGameSettingBinding
import com.farad.entertainment.kidsanimalenglish.utils.dropAnim
import com.farad.entertainment.kidsanimalenglish.utils.safeDismiss
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.setTextColorCompat
import org.koin.android.ext.android.inject

class DialogMemoryGameSetting : BaseDialogFragment<DialogMemoryGameSettingBinding>() {

    private var setOnItemClickListener: ((String) -> Unit)? = null
    private val sharedPreferencesManager: SharedPreferencesManager by inject()
    fun setOnItemClickListener(listener: (String) -> Unit) {
        setOnItemClickListener = listener
    }

    private var typeImage = "p_"
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogMemoryGameSettingBinding
        get() = DialogMemoryGameSettingBinding::inflate

    override fun setup() {
        startAnimation()

        checkSetting()

        binding.btnRealPhoto.setOnSafeClickListener {
            typeImage = "p_"
            setOnItemClickListener?.invoke(typeImage)
            sharedPreferencesManager.typeImageMemory=typeImage
            safeDismiss()
        }
        binding.btnAnimatedPhoto.setOnSafeClickListener {
            typeImage = "pa_"
            sharedPreferencesManager.typeImageMemory=typeImage
            setOnItemClickListener?.invoke(typeImage)
            safeDismiss()
        }
    }

    private fun startAnimation() {
        binding.layoutSetting.dropAnim()
    }
    private fun checkSetting(){
        when (sharedPreferencesManager.typeImageMemory) {
            "p_" -> {
                binding.btnRealPhoto    .setBackgroundResource(R.drawable.shape_gray_rounded)
                binding.btnRealPhoto.setTextColorCompat(R.color.main_color)
            }

            "pa_" -> {
                 binding.btnAnimatedPhoto    .setBackgroundResource(R.drawable.shape_gray_rounded)
                binding.btnAnimatedPhoto.setTextColorCompat(R.color.main_color)
            }
        }
    }
}