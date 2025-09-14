package com.farad.entertainment.kidsanimalenglish.ui.activity.main

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BaseActivity
import com.farad.entertainment.kidsanimalenglish.base.OnBackPressed
import com.farad.entertainment.kidsanimalenglish.base.navigation.main.MainNavigator
import com.farad.entertainment.kidsanimalenglish.data.apiService.ApiService
import com.farad.entertainment.kidsanimalenglish.data.manager.NavigationManager
import com.farad.entertainment.kidsanimalenglish.data.manager.SharedPreferencesManager
import com.farad.entertainment.kidsanimalenglish.databinding.ActivityMainBinding
import com.farad.entertainment.kidsanimalenglish.ui.dialog.DialogEnterInviteFriends
import com.farad.entertainment.kidsanimalenglish.utils.BANNER_FULL_SCREEN
import com.farad.entertainment.kidsanimalenglish.utils.INVITECODE
import com.farad.entertainment.kidsanimalenglish.utils.getAndroidIdUser
import com.farad.entertainment.kidsanimalenglish.utils.getColorCompat
import com.farad.entertainment.kidsanimalenglish.utils.getNavHostFragment
import com.farad.entertainment.kidsanimalenglish.utils.initBannerStandard
import com.farad.entertainment.kidsanimalenglish.utils.isNotNull
import com.farad.entertainment.kidsanimalenglish.utils.isNull
import com.farad.entertainment.kidsanimalenglish.utils.lifecycleScopeDelayTryCatch
import com.farad.entertainment.kidsanimalenglish.utils.safeShow
import com.farad.entertainment.kidsanimalenglish.utils.setBackGround
import com.farad.entertainment.kidsanimalenglish.utils.sinaLog
import com.farad.entertainment.kidsanimalenglish.utils.visibleOrGone
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Timer
import java.util.TimerTask

class MainActivity : BaseActivity<ActivityMainBinding>(), MainNavigator {
    private var isShowBannerInterstitial = true

    private companion object {
        private const val TIME_BANNER_INTERSTITIAL = 3600000
    }

    private var timeWhenBannerInterstitial = 0L

    private var timerBannerInterstitial: Timer? = null

    private var bannerInterstitialAd: InterstitialAd? = null

    private var adRequestInterstitialAd: AdRequest? = null

    private val viewModel by viewModel<ViewModelMain>()

    private val apiService: ApiService by inject()
    private val sharedPreferencesManager: SharedPreferencesManager by inject()

    override fun getBindingView() = ActivityMainBinding.inflate(layoutInflater)


    override fun afterCreateView() {
        super.afterCreateView()
        INVITECODE = sharedPreferencesManager.inviteCode
        viewModel.saveUserInfo(getAndroidIdUser())
        setBackGroundRoot(true)
        setupNavigationManager()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
        initBanner()
        signUser()





    }
    private var rewardedInterstitialAd:RewardedInterstitialAd? = null
    private final var TAG = "MainActivity"
    private fun reward(){


        RewardedInterstitialAd.load(this, "ca-app-pub-3940256099942544/5354046379",
            AdRequest.Builder().build(), object : RewardedInterstitialAdLoadCallback(),
                OnUserEarnedRewardListener {
                override fun onAdLoaded(ad: RewardedInterstitialAd) {
                    sinaLog("onAdLoaded")
                    rewardedInterstitialAd = ad
                    rewardedInterstitialAd?.show( this@MainActivity, /* OnUserEarnedRewardListener */ this)
                    rewardedInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                        override fun onAdClicked() {
                            // Called when a click is recorded for an ad.
                        }

                        override fun onAdDismissedFullScreenContent() {
                            // Called when ad is dismissed.
                            // Set the ad reference to null so you don't show the ad a second time.
                            rewardedInterstitialAd = null
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            // Called when ad fails to show.
                            rewardedInterstitialAd = null
                        }

                        override fun onAdImpression() {
                            // Called when an impression is recorded for an ad.
                        }

                        override fun onAdShowedFullScreenContent() {
                            // Called when ad is shown.
                        }
                }}

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    sinaLog("onAdFailedToLoad"+adError?.toString())
                    rewardedInterstitialAd = null
                }

                override fun onUserEarnedReward(p0: RewardItem) {

                }
            })

        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(this,"ca-app-pub-3940256099942544/5224354917", adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                val  rewardedAd = null
                sinaLog("onAdFailedToLoad"+adError.message)
            }

            override fun onAdLoaded(ad: RewardedAd) {
                val  rewardedAd = ad
                sinaLog("onAdLoaded")
            }
        })
    }



    private fun signUser() {

        apiService.signUp()

    }


    fun showBannerFull(onAdLoaded: (() -> Unit)? = null) {


        loadInterstitialAd {
            onAdLoaded?.let { it() }
        }


    }

    private fun showInterstitialAd() {
        try {
            bannerInterstitialAd?.apply {

                  show(
                    this@MainActivity
                )


            }
        } catch (e: Exception) {
            e.fillInStackTrace()
        }

    }

    private fun loadInterstitialAd(onAdLoaded: (() -> Unit)? = null) {
        try {
            adRequestInterstitialAd = AdRequest.Builder().build()
            adRequestInterstitialAd?.let { adRequest ->
                InterstitialAd.load(this,
                    BANNER_FULL_SCREEN,
                    adRequest,
                    object : InterstitialAdLoadCallback() {
                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            bannerInterstitialAd = null
                            onAdLoaded?.let { it1 -> it1() }
                        }

                        override fun onAdLoaded(interstitialAd: InterstitialAd) {
                            bannerInterstitialAd = interstitialAd
                            bannerInterstitialAd?.setImmersiveMode(true)
                            onAdLoaded?.let { it1 -> it1() }
                            showInterstitialAd()


                        }
                    })
            }

            bannerInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdClicked() {

                }

                override fun onAdDismissedFullScreenContent() {
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                }

                override fun onAdImpression() {
                }

                override fun onAdShowedFullScreenContent() {
                }
            }
        } catch (e: Exception) {
            e.fillInStackTrace()
        }

    }

    private fun setupNavigationManager() {
        navHostFragment = getNavHostFragment(R.id.nav_host_container)!!

        navigationManager =
            NavigationManager(navHostFragment!!, R.id.main_nav_graph, onAfterRestartListener = {

            }).apply {

                onDestinationChangedListener = { destination, _ ->

                    when (destination.id) {
                        R.id.kindergartenFragment,
                        R.id.gameHomeFragment -> {

                        }

                        else -> {

                        }
                    }

                    if (destination.id == R.id.mainFragment) {
                        showInterstitialAd()

                    }

                }

            }

        navigationManager?.start()
    }


    fun setBackGroundRoot(isSet: Boolean = true) {

        try {
            lifecycleScopeDelayTryCatch(200) {
                if (isSet) {
                    binding.root.setBackGround(R.drawable.back)
                } else {
                    binding.root.setBackGround(0)
                    getColorCompat(R.color.color_background).let {
                        binding.root.setBackgroundColor(
                            it
                        )
                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun showBanner(isShow: Boolean) {
        binding.adView.visibleOrGone(isShow)


    }


    private fun initBanner() {
        binding.adView.initBannerStandard {}
    }


    override fun onBackPressedCompact() {

        val fragment = navHostFragment?.childFragmentManager?.fragments?.firstOrNull()
        val stackSize = navigationManager?.getBackStackEntryCount() ?: return
        if (stackSize == 0) {
            if (onItemBackPressedListener.isNull())
                super.onBackPressedCompact()
            else onItemBackPressedListener?.invoke()
        } else if (fragment is OnBackPressed)
            fragment.onBackPressedCompact()
        else
            navigationManager?.popBackStack()
    }


    override fun onDestroy() {
        timerBannerInterstitial?.cancel()
        timerBannerInterstitial?.purge()
        timerBannerInterstitial = null
        bannerInterstitialAd = null
        navigationManager?.release()
        navigationManager = null
        super.onDestroy()
    }


}