package com.farad.entertainment.kidsanimalenglish.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BaseDialogFragment
import com.farad.entertainment.kidsanimalenglish.data.apiService.ApiService
import com.farad.entertainment.kidsanimalenglish.databinding.DialogEnterInviteFriendsBinding
import com.farad.entertainment.kidsanimalenglish.utils.isLetterOrDigit
import com.farad.entertainment.kidsanimalenglish.utils.lifecycleScopeDelayTryCatch
import com.farad.entertainment.kidsanimalenglish.utils.safeDismiss
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import org.koin.android.ext.android.inject

class DialogEnterInviteFriends : BaseDialogFragment<DialogEnterInviteFriendsBinding>() {



    init {
        setThem(R.style.Theme_Dialog)
    }

    private val apiService: ApiService by inject()
    private var onSaveNameListener: (() -> Unit)? = null
    fun onSaveNameListener(listener: () -> Unit) {
        onSaveNameListener = listener
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogEnterInviteFriendsBinding
        get() = DialogEnterInviteFriendsBinding::inflate

    override fun setup() {
        listener()
    }

    private fun listener() {

        binding.edNameReferral.isLetterOrDigit()
        // binding.edNameReferral.setFilters(arrayOf<InputFilter>(AllCaps()))
        binding.btnSave.setOnSafeClickListener {
            if (binding.edNameReferral.text.toString().trim().isEmpty()) {
                binding.edNameReferral.error = "The Refer code is wrong."
            } else {
                apiService.inviteFriend(
                    binding.edNameReferral.text.toString(), error = {

                        binding.edNameReferral.error = "The Refer code is wrong."
                        binding.edNameReferral.setText("")

                    }, success = {


                        lifecycleScopeDelayTryCatch(500) {
                            safeDismiss()
                            onSaveNameListener?.invoke()
                        }
                    })

            }


        }
    }
}