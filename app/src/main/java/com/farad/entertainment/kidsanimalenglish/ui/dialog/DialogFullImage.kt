package com.farad.entertainment.kidsanimalenglish.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BaseDialogFragment
import com.farad.entertainment.kidsanimalenglish.data.manager.SharedPreferencesManager
import com.farad.entertainment.kidsanimalenglish.databinding.DialogFullImageBinding
import com.farad.entertainment.kidsanimalenglish.utils.animZoomInZoomOut
import com.farad.entertainment.kidsanimalenglish.utils.gone
import com.farad.entertainment.kidsanimalenglish.utils.initBannerStandard
import com.farad.entertainment.kidsanimalenglish.utils.loadGlide
import com.farad.entertainment.kidsanimalenglish.utils.loadImage
import com.farad.entertainment.kidsanimalenglish.utils.safeShow
import com.farad.entertainment.kidsanimalenglish.utils.setBackGround
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import org.koin.android.ext.android.inject

class DialogFullImage : BaseDialogFragment<DialogFullImageBinding>() {


    var idImage = ""
    private val sharedPreferencesManager: SharedPreferencesManager by inject()

    init {
        setThem(android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogFullImageBinding
        get() = DialogFullImageBinding::inflate

    override fun setup() {
        binding.layoutRoot.post {

                val imageUrl = idImage
                binding.imageAnimal.loadGlide(imageUrl) {
                    if (isNullView().not())
                        binding.imageLoading.gone()
                }



            binding.root.setBackGround(R.drawable.back)
            binding.imageAnimal.animZoomInZoomOut()
            binding.imageAnimal.setOnSafeClickListener {
                binding.imageAnimal.animZoomInZoomOut()
            }
        }

        //  showDialogNeedNet()

        initBanner()

    }

    private fun initBanner() {

        binding.adView.initBannerStandard()

    }

    private fun showDialogNeedNet() {
        if (sharedPreferencesManager.dialogNeedNet) {
            val dialogNeedNet = MessageDialog()
            dialogNeedNet.setTextDialog(getString(R.string.it_only_needs_internet))
            dialogNeedNet.setOnItemClickListener {
                sharedPreferencesManager.dialogNeedNet = false
            }
            dialogNeedNet.safeShow(childFragmentManager)
        }

    }
}