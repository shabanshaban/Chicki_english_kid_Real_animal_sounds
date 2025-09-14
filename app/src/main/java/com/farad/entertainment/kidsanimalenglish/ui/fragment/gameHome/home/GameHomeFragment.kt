package com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.cv.showDialogLock
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.GameHomeModel
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.ListGameHomeEnum
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.getListGameHome
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentGameHomeBinding
import com.farad.entertainment.kidsanimalenglish.utils.changeScreenOrientation
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener

class GameHomeFragment : BottomNavigationFragment<FragmentGameHomeBinding>() {


    private var gameHomeAdapter = GameHomeAdapter()

    private val listData = ArrayList<GameHomeModel>()

    private var lastPosition = 0
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGameHomeBinding
        get() = FragmentGameHomeBinding::inflate

    override fun setup() {
        initRecyclerview()
        changeScreenOrientation(false)
        listener()
        binding.root.post {
            changeScreenOrientation(false)
        }

    }

    override fun onStart() {
        super.onStart()
        changeScreenOrientation(false)
    }

    private fun listener() {
        binding.btnSetting.setOnSafeClickListener {
            binding.root.showDialogLock {
                val navigate = GameHomeFragmentDirections.actionGameHomeFragmentToSettingFragment()
                navigate(navigate)
            }


        }
    }

    private fun initRecyclerview() {
        if (listData.isEmpty()) {
            context?.getListGameHome()?.let { list ->
                listData.addAll(list)
            }
        }
        binding.recyclerview.adapter = gameHomeAdapter.apply {
            submitList(listData)

            setOnItemClickListener { gameHomeModel, i ->
                clickItem(gameHomeModel)
                lastPosition = i

            }
        }
        binding.recyclerview.post {
            binding.recyclerview.scrollToPosition(lastPosition)
        }

    }

    private fun clickItem(gameHomeModel: GameHomeModel) {

        when (gameHomeModel.id) {
            ListGameHomeEnum.WORD_GAME -> {
                val navigate =
                    GameHomeFragmentDirections.actionGameHomeFragmentToWordGameListFragment()
                navigate(navigate)
            }

            ListGameHomeEnum.BUBBLE -> {
                val navigate =
                    GameHomeFragmentDirections.actionGameHomeFragmentToBubbleGameFragment()
                navigate(navigate)
            }

            ListGameHomeEnum.BALLOON -> {
                val navigate = GameHomeFragmentDirections.actionGameHomeFragmentToBalloonsFragment()
                navigate(navigate)

            }

            ListGameHomeEnum.SCRATCH -> {
                val navigate =
                    GameHomeFragmentDirections.actionGameHomeFragmentToScratchGameFragment()
                navigate(navigate)
            }

            ListGameHomeEnum.GUESS -> {
                val navigate =
                    GameHomeFragmentDirections.actionGameHomeFragmentToGuessAnimalFragment()
                navigate(navigate)
            }

            ListGameHomeEnum.ANIMAL_FARM -> {
                val navigate =
                    GameHomeFragmentDirections.actionGameHomeFragmentToAnimalFarmFragment()
                navigate(navigate)
            }

            ListGameHomeEnum.GALLERY -> {
                val navigate =
                    GameHomeFragmentDirections.actionGameHomeFragmentToGalleryOnlineFragment()
                navigate(navigate)
            }

            ListGameHomeEnum.PAINTING -> {
                val navigate =
                    GameHomeFragmentDirections.actionGameHomeFragmentToListAnimalPaintFragment()
                navigate(navigate)
            }

            ListGameHomeEnum.POETRY_AND_STORIES -> {
                val navigate =
                    GameHomeFragmentDirections.actionGameHomeFragmentToStoriesFragmentGameHome()
                navigate(navigate)
            }

            ListGameHomeEnum.MEMORY -> {
                val navigate =
                    GameHomeFragmentDirections.actionGameHomeFragmentToMemoryHomeGameFragment()
                navigate(navigate)
            }

            ListGameHomeEnum.MOBILE -> {
                val navigate =
                    GameHomeFragmentDirections.actionGameHomeFragmentToMobileKidFragment()
                navigate(navigate)
            }

            ListGameHomeEnum.MR_ANBEH,
            ListGameHomeEnum.KIDS_MUSIC,
            ListGameHomeEnum.MR_GHOOGHOOLI,
            ListGameHomeEnum.ENGLISH_ALPHABET,
            ListGameHomeEnum.JOBS_SEASONS_COLORS,
            ListGameHomeEnum.BODY_ORGANS,
            ListGameHomeEnum.ANIMAL_SOUND,
            ListGameHomeEnum.CALM_SONGS,
            ListGameHomeEnum.HAPPY_KIDS_SONG,
            ListGameHomeEnum.BIRTHDAY_SONG,
            ListGameHomeEnum.EKA,
            ListGameHomeEnum.ANIMAL_FOREST,
            ListGameHomeEnum.AUDIO_50_STORIES,
            ListGameHomeEnum.ALIVE_PAINTING,
            ListGameHomeEnum.Alphabet -> {
            }

        }
    }


}