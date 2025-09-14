package com.farad.entertainment.kidsanimalenglish.base

import androidx.viewbinding.ViewBinding
import com.farad.entertainment.kidsanimalenglish.ui.activity.main.MainActivity


@Suppress("MemberVisibilityCanBePrivate")
abstract class BottomNavigationFragment<VB : ViewBinding> : BaseFragment<VB>(),
    OnBackPressed {

    fun getMainActivity() = (activity as? MainActivity)
    fun restartDestination() = getMainActivity()?.navigationManager?.restartDestination(false)
    override fun onBackPressedCompact() {
        popBackStack()
    }
}