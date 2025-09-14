package com.farad.entertainment.kidsanimalenglish.data.manager

import android.os.Build
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.Keep
import androidx.core.net.toUri
import androidx.core.view.forEach
import androidx.navigation.FloatingWindow
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUiSaveStateControl
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.utils.Click2
import com.google.android.material.navigation.NavigationBarView
import java.lang.ref.WeakReference


class NavigationManager(
    private val navHostFragment: NavHostFragment,
    @IdRes private val idNavGraph: Int,
    private val onAfterRestartListener: (() -> Unit)? = null
) : NavController.OnDestinationChangedListener {

    companion object {
        var logErrorListener: ((Throwable) -> Unit)? = null
        private const val DELAY_MILLIS_NAVIGATE = 250L
        private const val DELAY_MILLIS_NAVIGATE_BOTTOM_NAVIGATION = 400L
        private var previousNavigateTimeMillis = 0L

        @OptIn(NavigationUiSaveStateControl::class)
        fun setupWithNavController(
            navigationBarView: NavigationBarView,
            navController: NavController?,
            saveState: Boolean
        ) {
            navigationBarView.setOnItemSelectedListener { item ->
                if (navController?.currentDestination == null)
                    return@setOnItemSelectedListener false
                val currentTimeMillis = System.currentTimeMillis()
                if (currentTimeMillis >= previousNavigateTimeMillis + DELAY_MILLIS_NAVIGATE_BOTTOM_NAVIGATION) {
                    previousNavigateTimeMillis = currentTimeMillis
                    NavigationUI.onNavDestinationSelected(
                        item,
                        navController,
                        saveState
                    )
                }
                false
            }
            val weakReference = WeakReference(navigationBarView)
            navController?.addOnDestinationChangedListener(
                object : NavController.OnDestinationChangedListener {
                    override fun onDestinationChanged(
                        controller: NavController,
                        destination: NavDestination,
                        arguments: Bundle?
                    ) {
                        val view = weakReference.get()
                        if (view == null) {
                            navController.removeOnDestinationChangedListener(this)
                            return
                        }
                        if (destination is FloatingWindow) {
                            return
                        }

                        view.menu.forEach { item ->
                            if (destination.matchDestination(item.itemId)) {
                                item.isChecked = true
                            }
                        }
                    }
                })
        }

        private fun safeNavigate(job: () -> Unit) {
            val currentTimeMillis = System.currentTimeMillis()
            if (currentTimeMillis >= previousNavigateTimeMillis + DELAY_MILLIS_NAVIGATE) {
                previousNavigateTimeMillis = currentTimeMillis
                runCatching { job() }.onFailure { logErrorListener?.invoke(it) }
            }
        }

        private fun NavDestination.matchDestination(@IdRes destId: Int): Boolean =
            hierarchy.any { it.id == destId }
    }


    var onDestinationChangedListener: Click2<NavDestination, Bundle?>? = null


    fun start() {
        stop()
        navHostFragment.navController.addOnDestinationChangedListener(this)
    }

    fun stop() {
        navHostFragment.navController.removeOnDestinationChangedListener(this)
    }

    fun getBackStackEntryCount(): Int {
        return navHostFragment.childFragmentManager.backStackEntryCount
    }

    fun restartDestination(isAllowPopUp: Boolean) {
        runCatching {
            if (isAllowPopUp)
                navHostFragment.navController.navigate(
                    idNavGraph, null,
                    NavOptions.Builder()
                        .setPopUpTo(
                            navHostFragment.navController.graph.startDestinationId,
                            true
                        ).build()
                )
            onAfterRestartListener?.invoke()
        }
    }


    fun navigate(directions: NavDirections, pageAnimation: PageAnimation) {
        safeNavigate {
            navHostFragment.navController.navigate(
                directions,
                getDefaultNavOptions(pageAnimation)
            )
        }
    }

    fun navigate(@IdRes resId: Int, pageAnimation: PageAnimation) {
        safeNavigate {
            navHostFragment.navController.navigate(
                resId,
                null,
                getDefaultNavOptions(pageAnimation)
            )
        }
    }

    fun navigate(
        @IdRes resId: Int,
        args: Bundle?,
        pageAnimation: PageAnimation
    ) {
        safeNavigate {
            navHostFragment.navController.navigate(
                resId,
                args,
                getDefaultNavOptions(pageAnimation)
            )
        }
    }

    fun navigateToUrl(url: String, pageAnimation: PageAnimation = PageAnimation.NONE) {
        safeNavigate {
            navHostFragment.navController.navigate(
                url.toUri(),
                getDefaultNavOptions(pageAnimation)
            )
        }
    }


    fun popBackStack(): Boolean {
        return navHostFragment.navController.popBackStack()
    }

    fun popBackStack(
        @IdRes destinationId: Int,
        inclusive: Boolean = true,
        saveState: Boolean = false,
    ): Boolean {
        return navHostFragment.navController.popBackStack(destinationId, inclusive, saveState)
    }


    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?,
    ) {
        onDestinationChangedListener?.invoke(destination, arguments)
    }


    fun release() {
        stop()
        onDestinationChangedListener = null
    }

    private fun getDefaultNavOptions(pageAnimation: PageAnimation): NavOptions? {
        if (pageAnimation == PageAnimation.NONE)
            return null

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q)
            return null

        val navOptions: NavOptions?
        when (pageAnimation) {
            PageAnimation.DEFAULT -> {
                navOptions = androidx.navigation.navOptions {
                    anim {
                        enter = R.anim.slide_left
                        exit = R.anim.wait_anim
                        popEnter = R.anim.wait_anim
                        popExit = R.anim.slide_right
                    }
                }
            }

            else -> {
                navOptions = null
            }
        }
        return navOptions
    }


    @Keep
    enum class PageAnimation {
        NONE,
        DEFAULT
    }
}


