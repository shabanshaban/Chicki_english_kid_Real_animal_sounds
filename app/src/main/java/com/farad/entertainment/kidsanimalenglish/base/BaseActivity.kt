package com.farad.entertainment.kidsanimalenglish.base

import android.content.Context
import android.os.Bundle
import android.view.Window
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.farad.entertainment.kidsanimalenglish.base.navigation.base.BaseNavigator
import com.farad.entertainment.kidsanimalenglish.data.manager.DialogManager
import com.farad.entertainment.kidsanimalenglish.data.manager.NavigationManager
import com.farad.entertainment.kidsanimalenglish.data.manager.SharedPreferencesManager
import com.farad.entertainment.kidsanimalenglish.utils.checkLanguage
import com.farad.entertainment.kidsanimalenglish.utils.setLocaleApp
import org.koin.android.ext.android.inject
import java.util.Locale


abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(), ViewCreatedActivity,
    OnBackPressed, BaseNavigator {
    protected var onItemBackPressedListener: (() -> Unit)? = null
    private var showBannerListener: (() -> Unit)? = null
    fun setOnBackPressedListener(listener: () -> Unit) {
        onItemBackPressedListener = listener
    }

    fun setShowBannerListener(listener: () -> Unit) {
        onItemBackPressedListener = listener
    }

    protected var navHostFragment: NavHostFragment? = null

    lateinit var binding: VB

    private val sharedPreferencesManager: SharedPreferencesManager by inject()
    var dialogManager: DialogManager? = null
    var navigationManager: NavigationManager? = null


    abstract fun getBindingView(): VB
    fun  setupApplyWindowInsetsListener() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { view, insets ->
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                systemBarsInsets.left,
                systemBarsInsets.top,
                systemBarsInsets.right,
                maxOf(systemBarsInsets.bottom, imeInsets.bottom)
            )
            WindowInsetsCompat.CONSUMED
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR)
        beforeCreateView()

        dialogManager = DialogManager(this, supportFragmentManager)

        super.onCreate(savedInstanceState)
        binding = getBindingView()

        setContentView(binding.root)
        setupApplyWindowInsetsListener()
        supportActionBar?.hide()
        applicationContext.setLocaleApp( )
        initProgressManager()
        initMessageManager()
        initBackPressListener()
        afterCreateView()


    }


    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase.setLocaleApp())
    }


    private fun initProgressManager() {

    }

    private fun initMessageManager() {

    }

    private fun initBackPressListener() {
        onBackPressedDispatcher.addCallback(this, true) {
            onBackPressedCompact()
        }
    }

    override fun afterCreateView() {
    }

    override fun beforeCreateView() {
    }

    override fun onBackPressedCompact() {
        finish()
    }


    @Suppress("LeakingThis")
    override val baseActivity = this

    override fun onStart() {
        super.onStart()
        navigationManager?.start()
    }

    override fun onStop() {
        navigationManager?.stop()
        super.onStop()
    }
    override fun onDestroy() {

        dialogManager = null

        super.onDestroy()
    }

}


interface ViewCreatedActivity {

    fun afterCreateView()

    fun beforeCreateView()

}

interface OnBackPressed {
    fun onBackPressedCompact()
}