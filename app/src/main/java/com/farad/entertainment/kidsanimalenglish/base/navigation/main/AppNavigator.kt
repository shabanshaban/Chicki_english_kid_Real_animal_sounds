package com.farad.entertainment.kidsanimalenglish.base.navigation.main

import android.os.Bundle
import androidx.navigation.NavDirections
import com.farad.entertainment.kidsanimalenglish.base.navigation.base.BaseNavigator
import com.farad.entertainment.kidsanimalenglish.data.manager.NavigationManager

interface AppNavigator : BaseNavigator {


    override fun navigate(
        navDirections: NavDirections,
        pageAnimation: NavigationManager.PageAnimation
    ) {
        baseActivity.navigationManager?.navigate(navDirections, pageAnimation)
    }

    override fun navigate(id: Int, pageAnimation: NavigationManager.PageAnimation) {
        baseActivity.navigationManager?.navigate(id, pageAnimation)
    }

    override fun navigate(id: Int, bundle: Bundle, pageAnimation: NavigationManager.PageAnimation) {
        baseActivity.navigationManager?.navigate(id, bundle, pageAnimation)
    }
}