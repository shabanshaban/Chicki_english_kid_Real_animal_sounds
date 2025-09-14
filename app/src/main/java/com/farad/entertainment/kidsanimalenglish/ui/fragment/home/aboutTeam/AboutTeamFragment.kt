package com.farad.entertainment.kidsanimalenglish.ui.fragment.home.aboutTeam

import android.view.LayoutInflater
import android.view.ViewGroup
import com.farad.entertainment.kidsanimalenglish.BuildConfig
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.cv.showDialogLock
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentAboutTeamBinding
import com.farad.entertainment.kidsanimalenglish.utils.PRIVACY_LINK
import com.farad.entertainment.kidsanimalenglish.utils.checkLanguage
import com.farad.entertainment.kidsanimalenglish.utils.invitedFriend
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.shareText
import com.farad.entertainment.kidsanimalenglish.utils.visible

class AboutTeamFragment : BottomNavigationFragment<FragmentAboutTeamBinding>() {


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAboutTeamBinding
        get() = FragmentAboutTeamBinding::inflate

    override fun setup() {

        binding.tvVersion.text = getString(R.string.version_s, BuildConfig.VERSION_CODE.toString())
        listener()




    }


    private fun listener() {
        binding.btnShare.setOnSafeClickListener {
            context?.invitedFriend()
        }

        binding.btnWhatsapp.setOnSafeClickListener {
            binding.root.showDialogLock()
        }
        binding.btnPrivacy.setOnSafeClickListener {

            showDialogWebView(PRIVACY_LINK, getString(R.string.privacy_policy))

        }

    }

}