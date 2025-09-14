package com.farad.entertainment.kidsanimalenglish.ui.fragment.youtube

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.util.UnstableApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.data.apiService.ApiService
import com.farad.entertainment.kidsanimalenglish.data.manager.SharedPreferencesManager
import com.farad.entertainment.kidsanimalenglish.data.model.VideoYoutubeModel
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.AnimalModel
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.getListData
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentYouTubeVideoPlayerBinding
import com.farad.entertainment.kidsanimalenglish.ui.activity.main.ViewModelMain
import com.farad.entertainment.kidsanimalenglish.ui.dialog.DialogInviteFriends
import com.farad.entertainment.kidsanimalenglish.ui.dialog.MessageDialog
import com.farad.entertainment.kidsanimalenglish.utils.InternetConnectionReceiver
import com.farad.entertainment.kidsanimalenglish.utils.changeScreenOrientation
import com.farad.entertainment.kidsanimalenglish.utils.explosionField
import com.farad.entertainment.kidsanimalenglish.utils.getAndroidIdUser
import com.farad.entertainment.kidsanimalenglish.utils.gone
import com.farad.entertainment.kidsanimalenglish.utils.lifecycleScopeDelayTryCatch
import com.farad.entertainment.kidsanimalenglish.utils.safeShow
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.subscribeToChannel
import com.farad.entertainment.kidsanimalenglish.utils.toast
import com.farad.entertainment.kidsanimalenglish.utils.visibleOrGone
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


@UnstableApi
class YouTubeVideoPlayerFragment : BottomNavigationFragment<FragmentYouTubeVideoPlayerBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentYouTubeVideoPlayerBinding
        get() = FragmentYouTubeVideoPlayerBinding::inflate

    private val args by navArgs<YouTubeVideoPlayerFragmentArgs>()

    private val viewModel by viewModel<ViewModelMain>()
    private val apiService: ApiService by inject()

    /*new Code*/
    private var youTubePlayer: YouTubePlayer? = null

    private var isFullScreen = false
    private var isNetWork = false

    private var listOldItem = ArrayList<String>()

    private val sharedPreferencesManager: SharedPreferencesManager by inject()
    private val internetConnectionReceiver by lazy { InternetConnectionReceiver(requireContext()) }

    /*new Code*/
    override fun setup() {

        checkNet()
        changeScreenOrientation(false)
        initYoutube()

    }

    override fun initObserveViewModel() {


        viewModel.getUserInfoLiveData()?.observe(this@YouTubeVideoPlayerFragment) { list ->

            list?.let {
                listOldItem.clear()
                list.listItem?.let { listOldItem.addAll(it) }

                val isOpen = listOldItem.contains((args.dataVideo.id + 1).toString())
                //binding.layoutLock.visibleOrGone(isOpen.not())



                val newList = context?.getListData()?.map {
                    it.isLock = !listOldItem.contains((it.id.numberId + 1).toString())
                    it
                }
                updateServerItem(newList)
            }

        }


    }

    override fun onBackPressedCompact() {

        if (isFullScreen) {

            youTubePlayer?.toggleFullscreen()
        } else {
            findNavController().popBackStack()

        }
    }

    private fun dialogShareInvited() {
        val dialog = DialogInviteFriends()

        dialog.safeShow(childFragmentManager)
    }


    private fun checkNet() {

        internetConnectionReceiver.setonStartTimer {
        }

        internetConnectionReceiver.observe(this) {
            isNetWork = it


        }
    }

    private fun showDialogNeedNet() {
        val dialogNeedNet = MessageDialog()
        dialogNeedNet.setTextDialog(getString(R.string.error_connect_net))

        dialogNeedNet.safeShow(childFragmentManager)

    }


    private fun initYoutube() {


      /*  binding.layoutLock.setOnSafeClickListener {

            if (isNetWork) {
                if (sharedPreferencesManager.coinCount.toInt() < 50) {
                    toast("Honey, you don't have enough coins, please invite more friends")
                    dialogShareInvited()
                } else {

                    args.dataVideo.typeKindergarten?.let { type ->
                        showDialogLoading()
                        apiService.updateCoin("50"){
                            dismissDialogLoading()
                        }
                        viewModel.saveVideoYoutube(VideoYoutubeModel(args.dataVideo.id, type))

                        val listNew = ArrayList<String>()
                        listNew.addAll(listOldItem)
                        listNew.add((args.dataVideo.id + 1).toString())
                        lifecycleScope.launch {

                            viewModel.updateList(listNew, getAndroidIdUser())
                        }
                        binding.layoutLock.post {
                            binding.layoutLock.explosionField(activity)
                            lifecycleScopeDelayTryCatch(1000) {

                                binding.layoutLock.gone()
                                youTubePlayer?.play()
                            }
                        }

                    }

                }
            } else {
                showDialogNeedNet()
            }


        }*/
        binding.btnSubscribed.setOnSafeClickListener {
            context?.subscribeToChannel()
        }
        val youtubePlayerView = binding.youtubePlayerView


        val fullScreenContainer = binding.fullScreenContainer
        lifecycle.addObserver(youtubePlayerView)
        youtubePlayerView.addFullscreenListener(object : FullscreenListener {
            override fun onEnterFullscreen(fullscreenView: View, exitFullscreen: () -> Unit) {
                isFullScreen = true
                fullScreenContainer.visibility = View.VISIBLE
                fullScreenContainer.addView(fullscreenView)

                // Full Screen remove status bar and navigation bar
                activity?.window?.let { window ->
                    WindowInsetsControllerCompat(window, binding.rootView).apply {
                        hide(WindowInsetsCompat.Type.systemBars())
                        systemBarsBehavior =
                            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                    }
                    var requestedOrientation = activity?.requestedOrientation
                    if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
                    }
                }


            }

            override fun onExitFullscreen() {
                isFullScreen = false
                fullScreenContainer.visibility = View.GONE
                fullScreenContainer.removeAllViews()

                // status bar and navigation bar
                activity?.window?.let { window ->
                    WindowInsetsControllerCompat(window, binding.rootView).apply {
                        show(WindowInsetsCompat.Type.systemBars())
                    }
                    var requestedOrientation = activity?.requestedOrientation
                    if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_SENSOR) {
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
                    }
                }


            }
        })

        val youtubePlayerListener = object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                this@YouTubeVideoPlayerFragment.youTubePlayer = youTubePlayer
                val videoId = args.dataVideo.url
                youTubePlayer.loadOrCueVideo(lifecycle, videoId, 0f)

                lifecycleScopeDelayTryCatch(2000) {

                    if (args.dataVideo.isLock)
                        youTubePlayer.pause()
                }
            }
        }

        val iFramePlayerOptions = IFramePlayerOptions.Builder()
            .controls(1)
            .fullscreen(1)
            .build()

        youtubePlayerView.enableAutomaticInitialization = false
        youtubePlayerView.initialize(youtubePlayerListener, iFramePlayerOptions)
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
                    }


                }
            }
        }

    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (!isFullScreen) {
                youTubePlayer?.toggleFullscreen()
            }
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (isFullScreen) {
                youTubePlayer?.toggleFullscreen()
            }
        }
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onDestroyView() {

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        try {

            binding.youtubePlayerView.release()
        }catch (e:Exception){
            e.fillInStackTrace()
        }
        super.onDestroyView()
    }


}