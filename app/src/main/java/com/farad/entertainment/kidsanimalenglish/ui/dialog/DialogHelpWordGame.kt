package com.farad.entertainment.kidsanimalenglish.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BaseDialogFragment
import com.farad.entertainment.kidsanimalenglish.databinding.DialogHelpWordGameBinding
import com.farad.entertainment.kidsanimalenglish.utils.loadGif

class DialogHelpWordGame : BaseDialogFragment<DialogHelpWordGameBinding>()  {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogHelpWordGameBinding
        get() = DialogHelpWordGameBinding::inflate

    override fun setup() {

        binding.imageHelpGif.loadGif(R.drawable.word_game_guide)
    }
}