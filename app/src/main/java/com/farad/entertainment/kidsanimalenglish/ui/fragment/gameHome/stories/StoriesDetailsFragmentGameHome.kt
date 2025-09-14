package com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.stories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentStoriesDetailBinding
import com.farad.entertainment.kidsanimalenglish.utils.loadImage
import com.farad.entertainment.kidsanimalenglish.utils.screenOn
import com.farad.entertainment.kidsanimalenglish.utils.shareText

class StoriesDetailsFragmentGameHome : BottomNavigationFragment<FragmentStoriesDetailBinding>() {

    private val args by navArgs<StoriesDetailsFragmentGameHomeArgs>()


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentStoriesDetailBinding
        get() = FragmentStoriesDetailBinding::inflate

    override fun setup() {
        listener()
        setData()
        screenOn()
    }

    override fun onPause() {
        super.onPause()
        binding.seekbarPlayer.pause()
    }

    private fun setData() {
        val text = args.dataStoreis.stories.replace(".", ".\n")
        binding.tvStories.text = text.replace(". ", "").replace("\n ", "\n")
        binding.tvTitle.text = args.dataStoreis.title
        binding.imageStories.loadImage(args.dataStoreis.image)
        binding.seekbarPlayer.setUrlSound(args.dataStoreis.soundUrl)
    }

    private fun listener() {
        binding.seekbarPlayer.setonItemShareClickListener {
            context?.shareText(args.dataStoreis.stories, getString(R.string.share_stories_title))
        }
    }

    override fun onDestroyView() {
        binding.seekbarPlayer.destroyPlayer()
        super.onDestroyView()
    }

    override fun onStop() {
        binding.seekbarPlayer.pause()
        super.onStop()
    }
}