package com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.scratchGame.home

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.data.manager.SharedPreferencesManager
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentGameScratchBinding
import com.farad.entertainment.kidsanimalenglish.ui.dialog.DialogName
import com.farad.entertainment.kidsanimalenglish.ui.dialog.DialogSelectLevel
import com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.scratchGame.vm.ScratchGameViewModel
import com.farad.entertainment.kidsanimalenglish.utils.animClickFast
import com.farad.entertainment.kidsanimalenglish.utils.animZoomInZoomOut
import com.farad.entertainment.kidsanimalenglish.utils.isNull
import com.farad.entertainment.kidsanimalenglish.utils.playSoundMediaPlayer
import com.farad.entertainment.kidsanimalenglish.utils.safeShow
import com.farad.entertainment.kidsanimalenglish.utils.screenOn
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScratchGameFragment : BottomNavigationFragment<FragmentGameScratchBinding>() {
    private var mediaPlayer: MediaPlayer? = null
    private var musicLoop: MediaPlayer? = null

    private val sharedPreferencesManager: SharedPreferencesManager by inject()
    private val viewModel by viewModel<ScratchGameViewModel>()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGameScratchBinding
        get() = FragmentGameScratchBinding::inflate

    override fun setup() {
        screenOn()
        listener()
        initMediaPlayer()
        playMusic()
        initView()

    }


    private fun showDialogSelectLevel() {
        val dialog = DialogSelectLevel()
        dialog.onSelectItemClickListener {

            sharedPreferencesManager.levelScratch = it
        }

        dialog.safeShow(childFragmentManager)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onPause() {
        super.onPause()
        musicLoop?.pause()
    }

    override fun onStart() {
        super.onStart()
        musicLoop?.start()
    }

    private fun initView() {
        binding.tvTitle.animZoomInZoomOut()

        if (sharedPreferencesManager.isScratchGame) {
            binding.btnResume.isEnabled = true
            binding.btnResume.alpha = 1f
        } else {
            binding.btnResume.isEnabled = false
            binding.btnResume.alpha = 0.2f
        }

    }

    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer()

    }

    private fun showDialogName() {
        val dialog = DialogName()
        dialog.userName =
            if (sharedPreferencesManager.userName.isNull()) "" else sharedPreferencesManager.userName.toString()
        dialog.isCancelable = false
        dialog.isCanceledOnTouchOutside = false
        dialog.onSaveNameListener {
            viewModel.nukeTable()
            sharedPreferencesManager.scoreScratch = 0
            sharedPreferencesManager.isScratchGame = true
            sharedPreferencesManager.valueSoundGameScratch = 0.5f
            sharedPreferencesManager.userName = it
            val navigate =
                ScratchGameFragmentDirections.actionScratchGameFragmentToListScratchGameFragment()
            navigate(navigate)
        }
        dialog.safeShow(childFragmentManager)
    }

    private fun playMusic() {
        musicLoop = mediaPlayer?.playSoundMediaPlayer(
            context,
            R.raw.loop,
            isLoop = true
        )
        val volume = sharedPreferencesManager.valueSoundGameScratch
        binding.soundVolumeView.setFistValue(volume)
        musicLoop?.setVolume(volume, volume)
    }

    private fun listener() {


        binding.btnResume.setOnSafeClickListener {
            val navigate =
                ScratchGameFragmentDirections.actionScratchGameFragmentToListScratchGameFragment()
            navigate(navigate)
            it.animClickFast()
        }

        binding.btnNewGame.setOnSafeClickListener {
            showDialogName()
            it.animClickFast()
        }


        binding.soundVolumeView.setOnChangeListener {
            sharedPreferencesManager.valueSoundGameScratch = it
            musicLoop?.setVolume(it, it)
        }


        binding.btnGameLevel.setOnSafeClickListener {
            it.animClickFast()
            showDialogSelectLevel()
        }


    }


}