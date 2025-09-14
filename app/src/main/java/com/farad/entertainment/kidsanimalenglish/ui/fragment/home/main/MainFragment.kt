package com.farad.entertainment.kidsanimalenglish.ui.fragment.home.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.media.MediaPlayer
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.farad.entertainment.kidsanimalenglish.MainNavGraphDirections
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.cv.showDialogLock
import com.farad.entertainment.kidsanimalenglish.data.apiService.ApiService
import com.farad.entertainment.kidsanimalenglish.data.manager.SharedPreferencesManager
import com.farad.entertainment.kidsanimalenglish.data.model.DataDialog
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.AnimalModel
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.PlaySoundAnimal
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.ZoomImageAnimal
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.getListData
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentMainBinding
import com.farad.entertainment.kidsanimalenglish.ui.activity.main.ViewModelMain
import com.farad.entertainment.kidsanimalenglish.ui.dialog.DialogClose
import com.farad.entertainment.kidsanimalenglish.ui.dialog.DialogEnterInviteFriends
import com.farad.entertainment.kidsanimalenglish.ui.dialog.DialogInviteFriends
import com.farad.entertainment.kidsanimalenglish.ui.dialog.DialogOpenItem
import com.farad.entertainment.kidsanimalenglish.ui.dialog.MessageDialog
import com.farad.entertainment.kidsanimalenglish.utils.APP_VERSION
import com.farad.entertainment.kidsanimalenglish.utils.InternetConnectionReceiver
import com.farad.entertainment.kidsanimalenglish.utils.alphaRepeat
import com.farad.entertainment.kidsanimalenglish.utils.animClick
import com.farad.entertainment.kidsanimalenglish.utils.animClickFast
import com.farad.entertainment.kidsanimalenglish.utils.checkLanguage
import com.farad.entertainment.kidsanimalenglish.utils.copyToClipboard
import com.farad.entertainment.kidsanimalenglish.utils.explosionField
import com.farad.entertainment.kidsanimalenglish.utils.getAndroidIdUser
import com.farad.entertainment.kidsanimalenglish.utils.goToMainApps
import com.farad.entertainment.kidsanimalenglish.utils.gone
import com.farad.entertainment.kidsanimalenglish.utils.invitedFriend
import com.farad.entertainment.kidsanimalenglish.utils.isNotNull
import com.farad.entertainment.kidsanimalenglish.utils.lifecycleScopeDelayTryCatch
import com.farad.entertainment.kidsanimalenglish.utils.playSoundMediaPlayer
import com.farad.entertainment.kidsanimalenglish.utils.safeShow
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.setSafeScrollListener
import com.farad.entertainment.kidsanimalenglish.utils.setStatusBarIconsColor
import com.farad.entertainment.kidsanimalenglish.utils.shakeAnimation
import com.farad.entertainment.kidsanimalenglish.utils.toast
import com.farad.entertainment.kidsanimalenglish.utils.visibleOrGone
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.regex.Pattern


class MainFragment : BottomNavigationFragment<FragmentMainBinding>() {
    private var isPlayMusic = false


    var counterImageClick: Int = 0
    private var serRequest = false
    private var mediaPlayer: MediaPlayer? = null
    private val sharedPreferencesManager: SharedPreferencesManager by inject()

    private val apiService: ApiService by inject()

    //data dialog exit
    private var dataDialog: DataDialog? = null

    private var isMotionAnimeTop = false

    private val viewModel by viewModel<ViewModelMain>()

    private val mainAnimalAdapter = MainAnimalAdapter()

    private val listItem = ArrayList<String>()
    private val listItemAnimal = ArrayList<AnimalModel>()
    private val internetConnectionReceiver by lazy { InternetConnectionReceiver(requireContext()) }

    private var isNetWork = false
    private var isDestroyView = false


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainBinding
        get() = FragmentMainBinding::inflate

    private fun closeDrawer() {
        binding.drawerLayout.closeDrawer(Gravity.LEFT, true)
    }

    override fun setup() {
      context?.getListData()?.let {

            if (listItemAnimal.isEmpty()){
                listItemAnimal.clear()
                listItemAnimal.addAll(it)
            }

        }
        checkNet()
        initRecyclerview()
        listener()
        initMediaPlayer()
        showDialogNewVersion()

        getMainActivity()?.setOnBackPressedListener {
            if (binding.drawerLayout.isOpen) {

                closeDrawer()
            } else {
                showDialogExit()
            }
        }
        getDataDialogExit()
        setBackground()


        dialogOpenItem()
      //  showRewardedAd()
     //   loadAd()

    }
    private fun loadAd() {
        RewardedInterstitialAd.load(requireContext(), "ca-app-pub-3940256099942544/5354046379",
            AdRequest.Builder().build(), object : RewardedInterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedInterstitialAd) {
                  toast("onAdLoaded")
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                     toast("onAdFailedToLoad")
                }
            })
    }
    private fun showRewardedAd() {
        var rewardedAd: RewardedAd? = null
        val adRequest = AdRequest.Builder().build()

        RewardedAd.load(
            requireContext(),
            "ca-app-pub-3940256099942544/5354046379",
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    rewardedAd = null
                  //  toast("onAdFailedToLoad"+adError.message)
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    rewardedAd = ad
                }
            })

        rewardedAd?.fullScreenContentCallback = object: FullScreenContentCallback(){
            override fun onAdClicked() {
                super.onAdClicked()
            }

            override fun onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent()
                toast("onAdDismissedFullScreenContent")
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                super.onAdFailedToShowFullScreenContent(p0)
                toast("onAdFailedToShowFullScreenContent")
            }

            override fun onAdImpression() {
                super.onAdImpression()
                toast("onAdImpression")
            }

            override fun onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent()
                toast("onAdShowedFullScreenContent")
            }
        }
        activity?.let {
            rewardedAd?.show(it) {
                toast("show")
            }
        }

    }

    private fun dialogOpenItem() {
        if (sharedPreferencesManager.showOpenItem.not()) {
            val dialogOpenItem = DialogOpenItem()
            dialogOpenItem.onSaveNameListener {}
            //   dialogOpenItem.safeShow(childFragmentManager)
        }
    }

    private fun setBackground() {
        if (isNullView().not()) {
            try {

                binding.root.post {
                    setBackGroundRoot(false)
                    getMainActivity()?.setBackGroundRoot(false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        setBackground()
    }

    private fun getDataDialogExit() {
        checkLanguage(farsi = {
            apiService.readData {
                dataDialog = it
            }
        }, english = {

        })

    }

    private fun showDialogExit() {
        try {
            val dialogClose = DialogClose()
            dialogClose.dataDialog = dataDialog
            dialogClose.setOnExitClickListener {

                onBackPressedCompact()
                activity?.finish()
            }
            dialogClose.isCanceledOnTouchOutside = true


            dialogClose.safeShow(childFragmentManager)

            if (isPlayMusic) {
                binding.btnPlayMusic.setImageResource(R.drawable.icon_play_all)
                binding.playerTop.pauseMusic()
                binding.playerTop.gone()
                isPlayMusic = !isPlayMusic
                //   binding.recyclerview.updatePadding(top = 56.px)

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun checkNet() {

        internetConnectionReceiver.setonStartTimer {
            binding.imageWifi.setImageResource(R.drawable.baseline_wifi_24)
            binding.imageWifi.setColorFilter(Color.WHITE)
            binding.imageWifi.alphaRepeat()
        }

        internetConnectionReceiver.observe(this) {
            binding.imageWifi.clearAnimation()
            isNetWork = it
            if (it) {
                binding.imageWifi.setImageResource(R.drawable.baseline_wifi_24)
                binding.imageWifi.setColorFilter(Color.GREEN)
            } else {
                binding.imageWifi.setImageResource(R.drawable.baseline_wifi_off_24)
                binding.imageWifi.setColorFilter(Color.RED)
            }
        }
    }

    private fun showDialogNeedNet() {
        val dialogNeedNet = MessageDialog()
        dialogNeedNet.setTextDialog(getString(R.string.error_connect_net))

        dialogNeedNet.safeShow(childFragmentManager)

    }

    private fun showDialogNewVersion() {

        if (sharedPreferencesManager.dialogUpdate) {
            val dialog = MessageDialog()

            val text = getString(R.string.textUpdate)

            dialog.setTextDialog(
                setWordColor(
                    text,
                    getString(R.string.plant_a_tree_and_install_a_garden)
                )
            )
            dialog.setHeaderText(
                getString(
                    R.string.whats_new_on_version,
                    APP_VERSION
                )
            )

            dialog.isCancelable = true
            dialog.isCanceledOnTouchOutside = true
            sharedPreferencesManager.dialogUpdate = false
            dialog.setTextGravity(Gravity.START)
            dialog.setOnItemClickListener {

            }

            dialog.safeShow(childFragmentManager)
        }
    }

    private fun setWordColor(text: String, textForColor: String): SpannableString {
        val ss = SpannableString(text)
        val pattern = Pattern.compile(textForColor)
        val matcher = pattern.matcher(ss)
        while (matcher.find()) {
            ss.setSpan(
                ForegroundColorSpan(Color.parseColor("#D32F2F")),
                matcher.start(),
                matcher.end(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return ss
    }

    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer()
    }

    private fun dialogEnterCodeInvited() {
        val dialog = DialogEnterInviteFriends()

       /* dialog.onSaveNameListener {
            binding.tvNumberFriends.text = getString(
                R.string.number_of_invited_friends_s,
                sharedPreferencesManager.subUserCount
            )

            binding.tvCoinm.text = "$" + sharedPreferencesManager.coinCount + " "
            binding.FrameLayoutAnimationView.visible()
            binding.animationView.playAnimation()
            binding.tvCoinm.scaleAnim2()
            binding.imageCoinm.scaleAnim2()
            mediaPlayer?.playSoundMediaPlayer(context, R.raw.right_crowd)


        }*/

        dialog.safeShow(childFragmentManager)
    }

    private fun dialogShareInvited() {
        val dialog = DialogInviteFriends()

        dialog.safeShow(childFragmentManager)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun listener() {


        binding.tvMenuSetting.setOnSafeClickListener {
            closeDrawer()
            val navigate = MainFragmentDirections.actionMainFragmentToSettingFragment()
            navigate(navigate)
        }


        binding.imageMenu.shakeAnimation()


        binding.ReferCode.text = sharedPreferencesManager.inviteCode



        binding.imageMenu.setOnSafeClickListener {
            binding.drawerLayout.openDrawer(Gravity.LEFT)
        }


        binding.imageCopyInivtedCode.setOnSafeClickListener {
            closeDrawer()
            binding.imageCopyInivtedCode.animClick {
                context?.copyToClipboard(
                    sharedPreferencesManager.inviteCode
                )
            }


        }

        binding.tvMenuExit.setOnSafeClickListener {
            closeDrawer()
            showDialogExit()

        }
        binding.tvMenuWhatsApp.setOnSafeClickListener {
            closeDrawer()
            binding.root.showDialogLock()
        }
        binding.tvMenuIdentification.setOnSafeClickListener {
            closeDrawer()
            dialogEnterCodeInvited()
        }
        binding.tvMenuInviteFriends.setOnSafeClickListener {
            closeDrawer()

           context?.invitedFriend()
        }
        binding.tvMenuRate.setOnSafeClickListener {
            closeDrawer()
            context?.goToMainApps()
        }

        binding.motionLayout.addTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {

            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {

                if (binding.motionLayout.progress < 0.90) {
                    binding.dividerItem.alpha = 0.5f
                    binding.dividerItem.elevation = 0f
                } else {
                    binding.dividerItem.alpha = 1f
                    binding.dividerItem.elevation = 0f
                }
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                if (binding.playerTop.isVisible) {
                    if (binding.motionLayout.velocity > 0) {
                        binding.playerTop.setColorItem(R.color.black)
                        binding.dividerItem.elevation = 1f
                        binding.linearLayoutCompat5.alpha = 0f
                    } else {
                        binding.dividerItem.elevation = 0f
                        binding.linearLayoutCompat5.alpha = 1f
                        binding.dividerItem.alpha = 1f
                        binding.playerTop.setColorItem(R.color.pinkDark)
                    }
                }
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
            }

        })

        binding.recyclerview.setSafeScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (_binding != null && binding.playerTop.isVisible) {
                    if (dy >= 1) {
                        binding.playerTop.setColorItem(R.color.black)
                        binding.dividerItem.elevation = 1f
                        binding.linearLayoutCompat5.alpha = 0f
                        binding.dividerItem.alpha = 1f
                        isMotionAnimeTop = true
                    } else {
                        lifecycleScopeDelayTryCatch(500) {
                            isMotionAnimeTop = false
                            binding.dividerItem.alpha = 1f
                            if (isNullView().not()) {
                                binding.dividerItem.elevation = 0f
                                binding.linearLayoutCompat5.alpha = 1f
                                binding.playerTop.setColorItem(R.color.pinkDark)
                            }
                            if (binding.motionLayout.progress == 1f) {
                                binding.playerTop.setColorItem(R.color.black)
                                binding.dividerItem.elevation = 1f
                                binding.linearLayoutCompat5.alpha = 0f
                            }
                        }


                    }
                }
            }
        })

        binding.imgKindergarten.setOnSafeClickListener {
            it.animClickFast()
            val navigate = MainFragmentDirections.actionMainFragmentToKindergartenFragment()
            navigate(navigate)
        }
        binding.lineGameHome.setOnSafeClickListener {
            it.animClickFast()
            val navigate = MainFragmentDirections.actionMainFragmentToGameHomeFragment()
            navigate(navigate)
        }
        binding.btnPlayMusic.setOnSafeClickListener {
            it.animClick()
            val soundPlay = listItemAnimal.filter { m -> m.soundPlay.isNotNull() && m.isLock.not() }
            if (soundPlay.isEmpty()) {
                context.toast("First you have to unlock the animals")
            } else {

                if (isPlayMusic) {
                    binding.btnPlayMusic.setImageResource(R.drawable.icon_play_all)
                    binding.playerTop.pauseMusic()
                    binding.linearLayoutCompat5.alpha = 1f
                    //     binding.recyclerview.updatePadding(top = 56.px)
                } else {
                    binding.btnPlayMusic.setImageResource(R.drawable.icon_pause_all)
                    binding.playerTop.playMusic()
                    if (isMotionAnimeTop)
                        binding.linearLayoutCompat5.alpha = 0f
                    //    binding.recyclerview.updatePadding(top = 96.px)

                }
                binding.playerTop.visibleOrGone(!isPlayMusic, true)
                isPlayMusic = !isPlayMusic
            }


        }
    }

    private fun changeColorFragment() {
        setStatusBarIconsColor(false)
    }

    private fun showDialogLock() {
        val dialog = DialogInviteFriends()
        dialog.safeShow(childFragmentManager)
    }

    private fun checkBannerFull(){

        if (counterImageClick>=7&&serRequest.not()){
            counterImageClick=0
            serRequest=true
            getMainActivity()?.showBannerFull {
                serRequest=false
            }
        }
    }
    private fun initRecyclerview() {

        binding.recyclerview.adapter = mainAnimalAdapter.apply {


            submitList(listItemAnimal)



            setOnNameFrenchClickListener {
                mediaPlayer?.playSoundMediaPlayer(context, it.soundNameFrench)
            }

            sharedPreferencesManager.typeShowImageAnimal?.let {
                typeImage = it
            }


            setOnNameEnglishClickListener {
                counterImageClick++
                mediaPlayer?.playSoundMediaPlayer(context, it.soundNameEnglish)
                checkBannerFull()
            }
            setOnItemClickListener { model, id ->
                clickItemAnimal(model, id)
                counterImageClick++
                checkBannerFull()
            }
            setOnMusicClickListener {
                counterImageClick++
                val navigate = MainNavGraphDirections.globalSoundGifAnimalFragment(it)
                navigate(navigate)
                checkBannerFull()
            }
        }
    }

    override fun initObserveViewModel() {

    /*    viewModel.getUserInfoLiveData()?.observe(this) {
            it?.let {


                listItem.clear()
                it.listItem?.let { it1 -> listItem.addAll(it1) }

                val listItem2 = it.listItem
                val newList = context?.getListData()?.map {
                    if (listItem2 != null) {
                        it.isLock = !listItem2.contains((it.id.numberId + 1).toString())
                    }
                    it
                }

                newList?.let { it1 ->
                    listItemAnimal.clear()
                    listItemAnimal.addAll(it1)
                }

                updateServerItem(newList)

                binding.playerTop.initPlayer(listItemAnimal.filter { m -> m.soundPlay.isNotNull() && m.isLock.not() })
                if (listItem2 != null) {
                    binding.circularProgressBar.progress = listItem2.size.toFloat()
                }
                binding.tvProgress.text =
                    "%" + listItem2?.let { it1 ->
                        binding.circularProgressBar.progressPercentage(it1.size).toInt()
                            .toString()
                    }
                if (isDestroyView) {
                    mainAnimalAdapter.submitList(null)
                    isDestroyView = false
                }
                mainAnimalAdapter.submitList(newList)
            }


        }*/
    }

    private fun updateServerItem(newList: List<AnimalModel>?) {

        var items=""
        if (newList?.isNotEmpty() == true){
            newList.forEachIndexed { index, animalModel ->
                if (animalModel.isLock.not())
                items+="${index+1},"

            }

            apiService.updateItem(items){
                if (it.isNotEmpty()){
                    lifecycleScope.launch {
                        viewModel.updateList(it,getAndroidIdUser())
                            mainAnimalAdapter.notifyDataSetChanged()

                    }
                    }
            }
        }

    }

    private fun CircularProgressBar.progressPercentage(size: Int): Float {

        return ((progress / 75)) * 100
    }

    private fun clickItemAnimal(model: AnimalModel, position: Int) {

        if (sharedPreferencesManager.zoomImageAnimal == ZoomImageAnimal.ZOOM_IN) {
            val navigate = MainFragmentDirections.actionMainFragmentToDetailsAnimalFragment(position, model)
            navigate(navigate)
        } else {

            when (sharedPreferencesManager.playSoundAnimal) {
                PlaySoundAnimal.ENGLISH -> {
                    mediaPlayer?.playSoundMediaPlayer(context, model.soundNameEnglish)
                }

                PlaySoundAnimal.FRANCE -> {
                    //play name FRANCE
                }

                else -> {
                    mediaPlayer?.playSoundMediaPlayer(
                        context,
                        model.soundNameEnglish,
                        onCompletion = {
                            mediaPlayer?.playSoundMediaPlayer(
                                context,
                                model.soundAnimal
                            )
                        })
                }
            }

        }
    }

    override fun onDestroyView() {
        mediaPlayer?.pause()
        mediaPlayer?.release()
        mediaPlayer = null
        isPlayMusic = false
        isDestroyView = true
        binding.playerTop.destroyMediaPlayer()
        super.onDestroyView()

    }

}