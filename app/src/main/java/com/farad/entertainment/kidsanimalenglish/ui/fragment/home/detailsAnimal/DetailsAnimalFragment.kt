package com.farad.entertainment.kidsanimalenglish.ui.fragment.home.detailsAnimal

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.farad.entertainment.kidsanimalenglish.MainNavGraphDirections
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.data.model.VideoPlayerModel
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.ListToolsAnimal
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.ToolsAnimalModel
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.getListTools
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentDetailsAnimal2Binding
import com.farad.entertainment.kidsanimalenglish.ui.dialog.DialogFullImage
import com.farad.entertainment.kidsanimalenglish.utils.animClickFast
import com.farad.entertainment.kidsanimalenglish.utils.getScreenHeight
import com.farad.entertainment.kidsanimalenglish.utils.playSoundMediaPlayer
import com.farad.entertainment.kidsanimalenglish.utils.safeShow
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.setStatusBarIconsColor
import com.farad.entertainment.kidsanimalenglish.utils.statusBarColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

class DetailsAnimalFragment : BottomNavigationFragment<FragmentDetailsAnimal2Binding>() {

    private var toolsAnimalAdapter = ToolsAnimalAdapter()

    private val args by navArgs<DetailsAnimalFragmentArgs>()

    private var mediaPlayer: MediaPlayer? = null

    private var isFirstRun = false

    private var isMuteSound = false

    private var timerMusic: Timer? = null


    private var isPauseMusic = false


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailsAnimal2Binding
        get() = FragmentDetailsAnimal2Binding::inflate

    override fun setup() {

            initRecyclerview()
            getArgument()
            initMediaPlayer()
            listener()
            isFirstRun = true
            setStatusBarIconsColor(true)
            statusBarColor(R.color.white)
            showBanner(false)
                //setBackGroundRoot(false)
                setSizeImage()

    }


    private fun setSizeImage() {

        context?.apply {

                withBinding {binding->
                    binding.root.post {
                        val screenHeight = ((getScreenHeight() / 3.5)).toInt()
                        val params: ViewGroup.LayoutParams = binding.imageAnimal.layoutParams
                        val materialCardView: ViewGroup.LayoutParams = binding.materialCardView.layoutParams
                        params.width = screenHeight
                        params.height = screenHeight
                        materialCardView.width = ((getScreenHeight() / 3.2f)).toInt()
                        binding.imageAnimal.layoutParams = params
                    }

                }

        }
    }

    override fun onPause() {
        super.onPause()
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
            timerMusic?.cancel()
            isPauseMusic = false
            binding.btnSound.setImageResource(R.drawable.ic_mute)
        }
    }


    override fun onDestroyView() {

        try {
            mediaPlayer?.pause()
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
            timerMusic?.cancel()
            timerMusic?.purge()
            timerMusic = null
            isPauseMusic = false
            super.onDestroyView()
        }catch (e:Exception){

           e.fillInStackTrace()
        }
    }

    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer()
        if (!isFirstRun) {
            R.raw.s1
            mediaPlayer?.playSoundMediaPlayer(context, args.animalModel.soundNameEnglish) {
                mediaPlayer?.playSoundMediaPlayer(
                    context,
                    args.animalModel.soundAnimal,
                    onPrepared = {
                        binding.voicePlayerView.maxProgress = it.duration.toFloat()
                        mediaPlayer = it
                        isPauseMusic = true
                        timerMusic(it)
                        it.setOnCompletionListener {
                            isPauseMusic = false
                            binding.voicePlayerView.progress = it.duration.toFloat()
                            timerMusic?.cancel()
                            binding.btnSound.setImageResource(R.drawable.ic_mute)

                        }
                    })
            }
        } else {
            binding.btnSound.setImageResource(R.drawable.ic_mute)
        }
    }

    private fun timerMusic(player: MediaPlayer) {
        timerMusic?.cancel()
        timerMusic?.purge()
        timerMusic = null
        timerMusic = Timer()
        timerMusic?.schedule(object : TimerTask() {
            override fun run() {
                lifecycleScope.launch {
                    if (isNullView().not()) {
                        player.currentPosition.let {
                            binding.voicePlayerView.progress = it.toFloat()
                        }
                    }
                }
            }

        }, 0, 100)
    }

    private fun listener() {


        binding.btnMusic.setOnSafeClickListener {
            it.animClickFast()
            val navigate = MainNavGraphDirections.globalSoundGifAnimalFragment(args.animalModel)
            navigate(navigate)


        }

        binding.btnVideo.setOnSafeClickListener {
            it.animClickFast()
            showDialogVideoErrorNetwork {
                val navigate = MainNavGraphDirections.globalVideoPlayer2(
                    VideoPlayerModel(
                        url = args.animalModel.video,
                        titleHeaderRight = getString(R.string.video),
                        visibilityControlView = false
                    )
                )
                navigate(navigate)
            }
        }
        binding.btnSound.setOnSafeClickListener {
            it.animClickFast()

            if (!isPauseMusic) {
                binding.btnSound.setImageResource(R.drawable.ic_sound)

                mediaPlayer?.let { mediaPlayer ->
                    if (mediaPlayer.currentPosition > 0 && mediaPlayer.currentPosition != mediaPlayer.duration) {
                        mediaPlayer.start()
                        timerMusic(mediaPlayer)
                    } else {

                        mediaPlayer.playSoundMediaPlayer(
                            context,
                            args.animalModel.soundAnimal,
                            onPrepared = { m ->

                                binding.voicePlayerView.maxProgress = m.duration.toFloat()
                                this.mediaPlayer = m
                                timerMusic(m)
                                m.setOnCompletionListener {
                                    isPauseMusic = false
                                    binding.voicePlayerView.progress = it.duration.toFloat()
                                    timerMusic?.cancel()
                                    binding.btnSound.setImageResource(R.drawable.ic_sound)
                                }

                            })
                    }
                }
            } else {
                mediaPlayer?.pause()
                timerMusic?.cancel()
                binding.btnSound.setImageResource(R.drawable.ic_mute)
            }
            isPauseMusic = !isPauseMusic


        }
        binding.imageAnimal.setOnSafeClickListener {
            it.animClickFast()
            val navigate = MainNavGraphDirections.globalSoundGifAnimalFragment(args.animalModel)
            navigate(navigate)
        }
        binding.tvNameEnglishAnimal.setOnSafeClickListener {
            it.animClickFast()
            mediaPlayer?.playSoundMediaPlayer(context, args.animalModel.soundNameEnglish)
        }
    }

    private fun getArgument() {
        args.animalModel.apply {
            binding.tvNameEnglishAnimal.text = title
            binding.imageAnimal.setImageResource(bigImage)

                args.animalModel.soundAnimal?.let {

                    lifecycleScope.launch {

                        binding.voicePlayerView.setSampleFrom(it)
                    }
                }
            binding.voicePlayerView.isEnabled = false


        }
    }

    private fun showImageFull() {
        val dialog = DialogFullImage()
        dialog.idImage = args.animalModel.fullImage
        dialog.safeShow(childFragmentManager)
    }

    private fun checkTools(toolsAnimalModel: ToolsAnimalModel) {
        when (toolsAnimalModel.id) {
            ListToolsAnimal.KINDERGARTEN -> {
                val navigate =
                    DetailsAnimalFragmentDirections.actionDetailsAnimalFragmentToKindergartenFragment(
                        args.idAnimal
                    )
                navigate(navigate)
            }

            ListToolsAnimal.VIDEO -> {
                showDialogVideoErrorNetwork {
                    val navigate = MainNavGraphDirections.globalVideoPlayer(
                        VideoPlayerModel(
                            url = args.animalModel.video,
                            titleHeaderRight = getString(R.string.video),
                            visibilityControlView = false
                        )
                    )
                    navigate(navigate)
                }


            }

            ListToolsAnimal.BIG_IMAGE -> {
                showImageFull()
            }

            ListToolsAnimal.SOUND -> {
                mediaPlayer?.playSoundMediaPlayer(
                    context,
                    args.animalModel.soundAnimal
                )
            }

            ListToolsAnimal.PAINT -> {
                showDialogChose()
            }

            ListToolsAnimal.PUZZLE -> {
                val navigate =
                    DetailsAnimalFragmentDirections.actionDetailsAnimalFragmentToPuzzleFragment(args.animalModel)
                navigate(navigate)

            }

            ListToolsAnimal.ABOUT -> {
                val navigate =
                    DetailsAnimalFragmentDirections.actionDetailsAnimalFragmentToAboutAnimalFragment(
                        args.idAnimal,
                        args.animalModel
                    )
                navigate(navigate)
            }

            ListToolsAnimal.GALLERY -> {
                val navigate =
                    DetailsAnimalFragmentDirections.actionDetailsAnimalFragmentToAlbumImageFragment(
                        args.idAnimal
                    )
                navigate(navigate)
            }
        }
    }


    private fun showDialogChose() {
        val title = getString(R.string.do_you_want_to_paint)
        val textLeft = getString(R.string.painting)
        val textRight = getString(R.string.Coloring)
        showDialogConfirmCancel(title, textLeft, textRight, leftListener = {
            val navigate =
                DetailsAnimalFragmentDirections.actionDetailsAnimalFragmentToColoringFragment(
                    args.animalModel
                )
            navigate(navigate)

        }, rightListener = {
            val navigate =
                DetailsAnimalFragmentDirections.actionDetailsAnimalFragmentToPaintingFragment(
                    args.animalModel
                )
            navigate(navigate)
        })
    }

    private fun initRecyclerview() {

        binding.recyclerview.adapter = toolsAnimalAdapter.apply {
            this.isFirstRun = this@DetailsAnimalFragment.isFirstRun
            context?.getListTools()?.let { list ->
                submitList(list)
            }

            setOnItemClickListener {
                checkTools(it)
            }
        }
    }
}