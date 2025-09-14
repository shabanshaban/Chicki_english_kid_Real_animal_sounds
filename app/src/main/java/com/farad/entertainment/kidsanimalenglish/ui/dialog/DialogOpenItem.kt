package com.farad.entertainment.kidsanimalenglish.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BaseDialogFragment
import com.farad.entertainment.kidsanimalenglish.data.manager.SharedPreferencesManager
import com.farad.entertainment.kidsanimalenglish.databinding.DialogOpenItemBinding
import com.farad.entertainment.kidsanimalenglish.utils.gone
import com.farad.entertainment.kidsanimalenglish.utils.lifecycleScopeDelayTryCatch
import com.farad.entertainment.kidsanimalenglish.utils.loadGif
import com.farad.entertainment.kidsanimalenglish.utils.safeDismiss
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.visible
import org.koin.android.ext.android.inject

class DialogOpenItem : BaseDialogFragment<DialogOpenItemBinding>() {



    init {
        setThem(R.style.Theme_Dialog)
    }

    private var onSaveNameListener: (() -> Unit)? = null
    fun onSaveNameListener(listener: () -> Unit) {
        onSaveNameListener = listener
    }

    private val sharedP: SharedPreferencesManager by inject()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogOpenItemBinding
        get() = DialogOpenItemBinding::inflate

    override fun setup() {
        listener()
    }

    private fun listener() {
        binding.tvDescription.text = getString(R.string.your_number_of_coins_s, sharedP.coinCount)
        binding.doNotShow.isChecked = sharedP.showOpenItem
        binding.doNotShow.setOnCheckedChangeListener { _, isChecked ->
            sharedP.showOpenItem = isChecked
        }
        binding.imageGif.loadGif(R.drawable.gif_lock)
        binding.imageGif.gone()
        binding.btnEasy.setOnSafeClickListener {
            binding.imageGif.visible()
            binding.rootDialog.gone()
            lifecycleScopeDelayTryCatch(2000) {
                binding.imageGif.gone()
                safeDismiss()
                onSaveNameListener?.invoke()
            }

        }
    }
}