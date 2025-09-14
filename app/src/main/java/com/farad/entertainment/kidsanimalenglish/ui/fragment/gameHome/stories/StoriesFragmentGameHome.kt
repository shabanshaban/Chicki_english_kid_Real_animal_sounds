package com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.stories

import android.view.LayoutInflater
import android.view.ViewGroup
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentStoriesGameHomeBinding
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener

class StoriesFragmentGameHome : BottomNavigationFragment<FragmentStoriesGameHomeBinding>()  {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentStoriesGameHomeBinding
        get() = FragmentStoriesGameHomeBinding::inflate

    override fun setup() {
        listener()
    }


    private fun listener(){

        binding.btnStories.setOnSafeClickListener {
           val navigate= StoriesFragmentGameHomeDirections.actionStoriesFragmentGameHomeToStoriesFragmentList()
            navigate(navigate)
        }
        binding.childishPoem.setOnSafeClickListener {
            val navigate= StoriesFragmentGameHomeDirections.actionStoriesFragmentGameHomeToStoriesFragmentList()
            navigate(navigate)
        }
    }

}