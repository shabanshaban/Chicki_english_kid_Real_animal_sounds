package com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.scratchGame.list

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.AnimalModel
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.getListData
import com.farad.entertainment.kidsanimalenglish.data.manager.SharedPreferencesManager
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.StateGameScratch
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentListGameScratchBinding
import com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.scratchGame.vm.ScratchGameViewModel
import com.farad.entertainment.kidsanimalenglish.utils.screenOn
import com.farad.entertainment.kidsanimalenglish.utils.toast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListScratchGameFragment : BottomNavigationFragment<FragmentListGameScratchBinding>() {
    private var mediaPlayer: MediaPlayer? = null
    private var musicLoop: MediaPlayer? = null

    private var listGameAnimal = ArrayList<AnimalModel>()
    private val sharedPreferencesManager: SharedPreferencesManager by inject()
    private val activityViewModel by viewModel<ScratchGameViewModel>()

    private val scratchGameListAdapter = ScratchGameListAdapter()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentListGameScratchBinding
        get() = FragmentListGameScratchBinding::inflate

    override fun setup() {
        screenOn()
        listener()
        initMediaPlayer()
        playMusic()
        initView()
        initRecyclerview()
    }


    private fun initRecyclerview() {
        binding.recyclerview.adapter = scratchGameListAdapter.apply {

            setOnItemClickListener { scratchGame, i ->

                when (scratchGame.stageGame) {

                    StateGameScratch.IS_OPEN -> {
                        val navigate =
                            ListScratchGameFragmentDirections.actionListScratchGameFragmentToScratchPlayGameFragment(
                                scratchGame
                            )
                        navigate(navigate)
                    }

                    StateGameScratch.IS_LOCK -> {
                        toast(getString(R.string.is_lock))
                    }

                    else -> {
                        toast(getString(R.string.you_have_played_this_before_my_dear))
                    }
                }

            }
        }
    }

    override fun initObserveViewModel() {
        activityViewModel.getAllScratchGameLiveData().observe(this) {
            scratchGameListAdapter.submitList(it)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause()
    }

    private fun initView() {
        listGameAnimal.clear()
        context?.getListData()?.let {
            listGameAnimal.addAll(it)
        }
        binding.tvPlayerName.text = ""
        sharedPreferencesManager.userName?.let {
            binding.tvPlayerName.text = getString(R.string.player_name, it)
        }
        sharedPreferencesManager.scoreScratch.let {
            binding.txtUserScore.text = getString(R.string.score_s, it.toString())
        }

    }

    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer()

    }

    private fun playMusic() {

    }

    private fun listener() {


    }


}