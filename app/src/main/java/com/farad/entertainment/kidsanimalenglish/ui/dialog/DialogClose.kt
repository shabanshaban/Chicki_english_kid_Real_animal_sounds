package com.farad.entertainment.kidsanimalenglish.ui.dialog

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.farad.entertainment.kidsanimalenglish.MainNavGraphDirections
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BaseDialogFragment
import com.farad.entertainment.kidsanimalenglish.cv.showDialogLock
import com.farad.entertainment.kidsanimalenglish.data.model.DataDialog
import com.farad.entertainment.kidsanimalenglish.databinding.DialogCloseBinding
import com.farad.entertainment.kidsanimalenglish.utils.animClick
import com.farad.entertainment.kidsanimalenglish.utils.checkLanguage
import com.farad.entertainment.kidsanimalenglish.utils.goMarketPage
import com.farad.entertainment.kidsanimalenglish.utils.goToMainApps
import com.farad.entertainment.kidsanimalenglish.utils.gone
import com.farad.entertainment.kidsanimalenglish.utils.implementSpringAnimationTrait
import com.farad.entertainment.kidsanimalenglish.utils.intentToTelegram
import com.farad.entertainment.kidsanimalenglish.utils.invitedFriend
import com.farad.entertainment.kidsanimalenglish.utils.loadImage
import com.farad.entertainment.kidsanimalenglish.utils.safeDismiss
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.shakeAnimation2
import com.farad.entertainment.kidsanimalenglish.utils.subscribeToChannel
import com.farad.entertainment.kidsanimalenglish.utils.visible
import com.farad.entertainment.kidsanimalenglish.utils.visibleOrGone


class DialogClose : BaseDialogFragment<DialogCloseBinding>() {
    private var text = ""
    private var imageAddress = ""
    private var link = ""
    private var imageAdVersion = ""
    private var bgColor = ""
    private var textColor = ""
    var dataDialog: DataDialog? = null
    private var setOnExitClickListener: (() -> Unit)? = null

    fun setOnExitClickListener(listener: () -> Unit) {
        setOnExitClickListener = listener
    }


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogCloseBinding
        get() = DialogCloseBinding::inflate

    init {

        setThem(R.style.Theme_Dialog_close)
    }

    override fun setup() {

        // val animDrawable = binding.animatedGradient.background as AnimationDrawable

        //  animDrawable.start()

        isCancelable = true
        initDialog()
        initData()

        listener()
        checkLanguage(farsi = {}, english = {
            binding.imgLogo.gone()
        })
    }


    private fun listener() {

        binding.imgLogo.shakeAnimation2()
        binding.imgLogo.setOnSafeClickListener {
            context?.goMarketPage()
        }
        binding.btnComment.shakeAnimation2()
        binding.btnAboutMe.setOnSafeClickListener {
            val navigate = MainNavGraphDirections.actionGlobalToAboutTeamFragment()
            findNavController().navigate(navigate)
        }
        binding.btnWhatsapp.setOnSafeClickListener {
            binding.root.showDialogLock()


        }
        binding.animatedGradient.setOnSafeClickListener {
            binding.animatedGradient.animClick()
            context?.subscribeToChannel()
        }
        binding.imageAdd.implementSpringAnimationTrait()
        binding.imageAdd.setOnSafeClickListener {
            binding.imageAdd.animClick()
            context?.subscribeToChannel()


        }
        binding.btnComment.setOnSafeClickListener {
            binding.btnComment.animClick()
            context?.goToMainApps()
        }
        binding.btnShare.setOnSafeClickListener {
            context?.invitedFriend()
        }

        binding.btnTelegram.setOnSafeClickListener {
            context?.intentToTelegram()
        }

        binding.btnExit.setOnSafeClickListener {
            safeDismiss()
            setOnExitClickListener?.invoke()
        }
    }


    private fun initData() {


        if ((imageAddress.contains("http") && link.contains("http")) || (imageAddress.contains("http") && link.contains(
                "package_name:"
            ))
        ) {
            binding.txtAd.visible()
            //   binding.txtAd.text = text
            binding.txtAd.visibleOrGone(text.isNotEmpty())
            if (textColor.contains("#")) {
                binding.txtAd.setTextColor(Color.parseColor(textColor))
            }
            if (bgColor.contains("#")) {
                binding.txtAd.setBackgroundColor(Color.parseColor(bgColor))
            }
        } else {
        }

    }


    private fun initDialog() {
        dataDialog?.let {
            text = it.text
            imageAddress = it.imageAddress
            link = it.link
            imageAdVersion = it.imageAdVersion.toString()
            bgColor = it.bgColor
            textColor = it.textColor

        }
        binding.imageAdd.loadImage(R.drawable.banner_subscribe_youtube)

    }
}