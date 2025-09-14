package com.farad.entertainment.kidsanimalenglish.base.navigation.base

import android.os.Bundle
import androidx.navigation.NavDirections
import com.farad.entertainment.kidsanimalenglish.base.BaseActivity
import com.farad.entertainment.kidsanimalenglish.data.manager.NavigationManager


interface BaseNavigator {

    val baseActivity: BaseActivity<*>

    fun navigate(
        pageAnimation: NavigationManager.PageAnimation = NavigationManager.PageAnimation.NONE
    )


    fun navigate(
        navDirections: NavDirections,
        pageAnimation: NavigationManager.PageAnimation = NavigationManager.PageAnimation.DEFAULT
    )

    fun navigate(
        id: Int,
        pageAnimation: NavigationManager.PageAnimation = NavigationManager.PageAnimation.DEFAULT
    )

    fun navigate(
        id: Int,
        bundle: Bundle,
        pageAnimation: NavigationManager.PageAnimation = NavigationManager.PageAnimation.DEFAULT
    )
}

