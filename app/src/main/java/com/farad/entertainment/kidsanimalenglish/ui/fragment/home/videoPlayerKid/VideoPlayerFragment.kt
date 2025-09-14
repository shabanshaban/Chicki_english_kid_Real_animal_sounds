package com.farad.entertainment.kidsanimalenglish.ui.fragment.home.videoPlayerKid

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.OptIn
import androidx.appcompat.widget.AppCompatImageView
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player.REPEAT_MODE_ONE
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.exoplayer.trackselection.AdaptiveTrackSelection
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.ui.PlayerView.SHOW_BUFFERING_WHEN_PLAYING
import androidx.navigation.fragment.navArgs
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.app.BaseApp
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.ListKindergarten
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentVideoPlayerBinding
import com.farad.entertainment.kidsanimalenglish.utils.animClickFast
import com.farad.entertainment.kidsanimalenglish.utils.changeScreenOrientation
import com.farad.entertainment.kidsanimalenglish.utils.getColorCompat
import com.farad.entertainment.kidsanimalenglish.utils.getOrientation
import com.farad.entertainment.kidsanimalenglish.utils.gone
import com.farad.entertainment.kidsanimalenglish.utils.invisible
import com.farad.entertainment.kidsanimalenglish.utils.loadImage
import com.farad.entertainment.kidsanimalenglish.utils.safeShow
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.showSnackBarMessage
import com.farad.entertainment.kidsanimalenglish.utils.visible
import com.farad.entertainment.kidsanimalenglish.utils.visibleOrGone

@UnstableApi
class VideoPlayerFragment : BottomNavigationFragment<FragmentVideoPlayerBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentVideoPlayerBinding
        get() = FragmentVideoPlayerBinding::inflate

    private val args by navArgs<VideoPlayerFragmentArgs>()

    private var mExoPlayerFullscreen = false
    private var mResumeWindow = 0
    private var mResumePosition: Long = 0
    private var isShowingTrackSelectionDialog = false
    private var mFullScreenButton: FrameLayout? = null
    private var mFullScreenIcon: AppCompatImageView? = null
    private var ivBackToPortrait: AppCompatImageView? = null
    private var ivSettingPlayer: AppCompatImageView? = null
    private var mFullScreenDialog: Dialog? = null
    private var player: ExoPlayer? = null
    private var trackSelector: DefaultTrackSelector? = null
    private var trackSelectorParameters: DefaultTrackSelector.Parameters? = null

    private var colorBack = -1

    @OptIn(UnstableApi::class)
    override fun setup() {





        startPlayer()
        setupView()
        setupListeners()
        changeScreenOrientation(false)
        // checkOrientation(getOrientation())

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            trackSelectorParameters =
                savedInstanceState.getBundle(KEY_TRACK_SELECTOR_PARAMETERS) as? DefaultTrackSelector.Parameters?
            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW)
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION)
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN)
        } else {
            val builder = DefaultTrackSelector.Parameters.Builder(
                activity?.applicationContext ?: requireContext()
            )
            trackSelectorParameters = builder.build()
        }
    }

    private fun checkType(): Int {
        var shapeDescription = R.drawable.shape_dash_black_rounded_light_green
        when (args.dataVideo.typeKindergarten) {
            ListKindergarten.ORIGAMI -> {
                shapeDescription = R.drawable.shape_dash_black_rounded_light_yellow
                colorBack = R.color.back_main_origami
            }

            ListKindergarten.CREATIVITY -> {
                colorBack = R.color.back_main_creativity
                shapeDescription = R.drawable.shape_dash_black_rounded_light_green
            }

            ListKindergarten.PAINTING -> {
                shapeDescription = R.drawable.shape_dash_black_rounded_light_pink
                colorBack = R.color.back_main_draw
            }

            ListKindergarten.HAND_PRINT -> {
                shapeDescription = R.drawable.shape_dash_black_rounded_light_blue
                colorBack = R.color.back_main_hand_print
            }

            ListKindergarten.STATUE -> {
                shapeDescription = R.drawable.shape_dash_black_rounded_light_orange
                colorBack = R.color.back_main_statue
            }

            ListKindergarten.STORIES -> {
                shapeDescription = R.drawable.shape_dash_black_rounded_light_orange
                binding.space.gone()
                binding.lineWhatsapp.gone()
                context?.let { context ->
                    binding.mainMediaFrame.setBackgroundColor(context.getColorCompat(R.color.orange_text))

                }
            }

            else -> {

            }
        }

        return shapeDescription
    }

    private fun setupView() {


        args.dataVideo.apply {
            binding.imageHeader.setImageResource(imageHeader)
            binding.imgHeaderLeft.setImageResource(imageHeaderLeft)
            binding.imgHeaderRight.setImageResource(imageHeaderRight)
            binding.txtHeaderLeft.text = titleHeaderLeft
            binding.txtHeaderRight.text = titleHeaderRight
            binding.lineWhatsapp.visibleOrGone(isWhatsapp)
            //    binding.txtMessage.visibleOrGone(isDescription)
            binding.txtMessage.setBackgroundResource(checkType())
            if (colorBack != -1) {
                binding.imageBack.loadImage(R.drawable.back)
                /*   context?.let {context ->
                       binding.imageBack.setBackgroundColor(
                           context.getColorCompat(
                               colorBack
                           )
                       )
                   }*/

            } else {
                binding.imageBack.loadImage(R.drawable.back)
            }
        }


    }

    private fun showWebView() {
        if (getString(R.string.the_gift_of_the_popak_storyteller_application) == binding.txtHeaderLeft.text) {

            showDialogWebView("https://poopakapp.com/", "")

            onPause()
        }
    }

    private fun setupListeners() {
        player?.repeatMode = REPEAT_MODE_ONE

        binding.txtHeaderLeft.setOnSafeClickListener {
            it.animClickFast()
            showWebView()
        }
        binding.imgHeaderLeft.setOnSafeClickListener {
            it.animClickFast()
            showWebView()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(STATE_RESUME_WINDOW, mResumeWindow)
        outState.putLong(STATE_RESUME_POSITION, mResumePosition)
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen)
        outState.putBundle(KEY_TRACK_SELECTOR_PARAMETERS, trackSelectorParameters?.toBundle())
        super.onSaveInstanceState(outState)
    }


    private fun checkOrientation(newConfig: Int?) {
        if (newConfig == Configuration.ORIENTATION_LANDSCAPE) {
            openFullscreenDialog()
        } else {
            closeFullscreenDialog()
        }
    }

    private fun initFullscreenDialog() {
        mFullScreenDialog =
            object : Dialog(
                context ?: requireContext(),
                android.R.style.Theme_Black_NoTitleBar_Fullscreen
            ) {}

        mFullScreenDialog?.setOnDismissListener {
            if (mExoPlayerFullscreen)
                closeFullscreenDialog()
        }

    }

    private fun openFullscreenDialog() {
        //   activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        (binding.exoplayer.parent as ViewGroup).removeView(binding.exoplayer)
        ivBackToPortrait?.visible()
        ivSettingPlayer?.invisible()
        mFullScreenDialog?.addContentView(
            binding.exoplayer,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        binding.exoplayer.setShowBuffering(SHOW_BUFFERING_WHEN_PLAYING)
        mFullScreenIcon?.setImageResource(R.drawable.ic_fullscreen_skrink)
        mExoPlayerFullscreen = true
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
        mFullScreenDialog?.show()
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private fun closeFullscreenDialog() {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        (binding.exoplayer.parent as? ViewGroup)?.removeView(binding.exoplayer)
        ivBackToPortrait?.gone()
        ivSettingPlayer?.gone()
        binding.mainMediaFrame.addView(binding.exoplayer)
        mExoPlayerFullscreen = false
        mFullScreenDialog?.dismiss()
        mFullScreenIcon?.setImageResource(R.drawable.ic_fullscreen_expand)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR

    }

    private fun showSetting() {
        trackSelector?.let {
            if (!isShowingTrackSelectionDialog &&
                TrackSelectionDialog.willHaveContent(trackSelector)
            ) {
                isShowingTrackSelectionDialog = true

                val trackSelectionDialog = TrackSelectionDialog(
                    R.string.select_video_quality,
                    it,
                ) { isShowingTrackSelectionDialog = false }
                trackSelectionDialog.safeShow(childFragmentManager)
            } else {
                showSnackBarMessage(getString(R.string.no_quality_found))
            }
        }
    }

    private fun initFullscreenButton() {
        val controlView =
            binding.exoplayer.findViewById<ViewGroup>(androidx.media3.ui.R.id.exo_controller)
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon)
        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button)
        ivSettingPlayer = controlView.findViewById(R.id.ivSettingPlayer)
        ivBackToPortrait = controlView.findViewById(R.id.ivBackToPortrait)
        ivBackToPortrait?.setOnClickListener { closeFullscreenDialog() }
        ivSettingPlayer?.invisible()
        mFullScreenIcon?.gone()
        ivSettingPlayer?.setOnClickListener { showSetting() }
        val ivPause =
            controlView.findViewById<ImageView>(androidx.media3.ui.R.id.exo_pause)
        val ivPlay =
            controlView.findViewById<ImageView>(androidx.media3.ui.R.id.exo_play)
        ivPause.setOnClickListener {
            player?.playWhenReady = false
            ivPlay.visible()
            it.gone()
        }
        ivPlay.setOnClickListener {
            player?.playWhenReady = true
            ivPause.visible()
            it.gone()
        }
        mFullScreenButton?.setOnClickListener {
            val configuration = getOrientation()
            if (configuration == Configuration.ORIENTATION_PORTRAIT) {
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                openFullscreenDialog()
            } else {
                closeFullscreenDialog()
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //checkOrientation(newConfig.orientation)
    }

    private fun initExoPlayer() {
        val urlVideo =   args.dataVideo.url

        if (urlVideo.isNotEmpty()) {
            val mediaItem = MediaItem.fromUri(urlVideo)
            player?.setMediaItem(mediaItem)

            val mediaSource = ProgressiveMediaSource.Factory(
                CacheDataSource.Factory()
                    .setCache(BaseApp.simpleCache)
                    .setUpstreamDataSourceFactory(
                        DefaultHttpDataSource.Factory()
                            .setUserAgent("ExoPlayer")
                    )
                    .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
            ).createMediaSource(MediaItem.fromUri(urlVideo))


            val trackSelectionFactory = AdaptiveTrackSelection.Factory()
            trackSelector = DefaultTrackSelector(context ?: requireContext(), trackSelectionFactory)
            trackSelectorParameters?.let {
                trackSelector?.parameters = it
            }
            player =
                ExoPlayer.Builder(context ?: requireContext()).setTrackSelector(trackSelector!!)
                    .build()
            binding.exoplayer.player = player
            binding.exoplayer.let {
                it.setShowBuffering(SHOW_BUFFERING_WHEN_PLAYING)
                it.player = player
            }
            player?.let {
                val haveResumePosition = mResumeWindow != C.INDEX_UNSET
                it.setMediaSource(mediaSource)
                it.prepare()
                it.play()
                it.playWhenReady = true
                if (haveResumePosition) it.seekTo(mResumeWindow, mResumePosition)
            }

        } else {
            showSnackBarMessage(getString(R.string.can_not_open_video))
        }
    }

    private fun checkVisibilityControlView() {
        val controlView: ViewGroup =
            binding.exoplayer.findViewById(androidx.media3.ui.R.id.exo_controller)
        controlView.visibleOrGone(args.dataVideo.visibilityControlView)
        binding.exoplayer.useController = args.dataVideo.visibilityControlView

        if (args.dataVideo.visibilityControlView.not()) {
            player?.play()
        }
    }

    private fun startPlayer() {

        initFullscreenDialog()
        initFullscreenButton()
        initExoPlayer()

        if (mExoPlayerFullscreen) {
            (binding.exoplayer.parent as? ViewGroup)?.removeView(binding.exoplayer)
            mFullScreenDialog?.addContentView(
                binding.exoplayer,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
            mFullScreenIcon?.setImageResource(R.drawable.ic_fullscreen_skrink)
            mFullScreenDialog?.show()
        }
        if (!mExoPlayerFullscreen)
            closeFullscreenDialog()
    }

    override fun onResume() {
        super.onResume()
        checkVisibilityControlView()

    }


    override fun onPause() {
        super.onPause()
        player?.let {

            checkVisibilityControlView()
            val controlView =
                binding.exoplayer.findViewById<ViewGroup>(androidx.media3.ui.R.id.exo_controller)
            it.pause()
            val ivPause =
                controlView.findViewById<ImageView>(androidx.media3.ui.R.id.exo_pause)
            val ivPlay =
                controlView.findViewById<ImageView>(androidx.media3.ui.R.id.exo_play)
            ivPlay.visible()
            ivPause.gone()

        }

    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onDestroyView() {
        binding.exoplayer.player = null
        mFullScreenIcon = null
        mFullScreenButton = null
        ivBackToPortrait = null
        ivSettingPlayer = null
        mFullScreenDialog = null
        trackSelectorParameters = null
        trackSelector?.release()
        trackSelector = null
        player?.release()
        player = null
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onDestroyView()
    }

    companion object {
        //region variables
        private const val STATE_RESUME_WINDOW = "resumeWindow"
        private const val STATE_RESUME_POSITION = "resumePosition"
        private const val STATE_PLAYER_FULLSCREEN = "playerFullscreen"
        private const val KEY_TRACK_SELECTOR_PARAMETERS = "track_selector_parameters"
    }

}