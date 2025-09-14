package com.farad.entertainment.kidsanimalenglish.ui.dialog

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BaseDialogFragment
import com.farad.entertainment.kidsanimalenglish.data.manager.SharedPreferencesManager
import com.farad.entertainment.kidsanimalenglish.databinding.DialogInviteFriendsBinding
import com.farad.entertainment.kidsanimalenglish.utils.animClick
import com.farad.entertainment.kidsanimalenglish.utils.animFadeInfinite
import com.farad.entertainment.kidsanimalenglish.utils.animVibrate
import com.farad.entertainment.kidsanimalenglish.utils.gone
import com.farad.entertainment.kidsanimalenglish.utils.invitedFriend
import com.farad.entertainment.kidsanimalenglish.utils.lifecycleScopeDelayTryCatch
import com.farad.entertainment.kidsanimalenglish.utils.loadGif
import com.farad.entertainment.kidsanimalenglish.utils.playSoundMediaPlayer
import com.farad.entertainment.kidsanimalenglish.utils.safeDismiss
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.visible
import org.koin.android.ext.android.inject

class DialogInviteFriends : BaseDialogFragment<DialogInviteFriendsBinding>() {



    init {
        setThem(R.style.Theme_Dialog3)
    }

    private var onSaveNameListener: ((String) -> Unit)? = null
    fun onSaveNameListener(listener: (String) -> Unit) {
        onSaveNameListener = listener
    }

    private var mediaParser: MediaPlayer? = null
    private val sharedP: SharedPreferencesManager by inject()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogInviteFriendsBinding
        get() = DialogInviteFriendsBinding::inflate

    override fun setup() {
        listener()
    }

    private fun listener() {

        mediaParser = MediaPlayer()
        mediaParser?.playSoundMediaPlayer(context, R.raw.invite, onCompletion = {

            binding.btnSound.visible(true)
        })

        binding.btnSound.setOnSafeClickListener {
            it.animClick { binding.btnSound.gone() }
            mediaParser?.start()
        }

        binding.btnEasy.animFadeInfinite()
        binding.tvCoinNumber.text = getString(R.string.your_number_of_coins_s, "\n"+ sharedP.coinCount)
        binding.btnEasy.setOnSafeClickListener {
            lifecycleScopeDelayTryCatch(500) {
                safeDismiss()
                context?.invitedFriend()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaParser?.release()
        mediaParser=null
    }
}