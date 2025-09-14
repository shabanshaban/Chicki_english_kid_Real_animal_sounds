package com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.stories

import android.view.LayoutInflater
import android.view.ViewGroup
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.data.model.DataStoriesGameHomeModel
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentStoriesGameHomeListBinding
import com.farad.entertainment.kidsanimalenglish.utils.getImageDrawableByName
import com.farad.entertainment.kidsanimalenglish.utils.getStringByName

class StoriesFragmentList : BottomNavigationFragment<FragmentStoriesGameHomeListBinding>() {

    private val listStoriesModel = ArrayList<DataStoriesGameHomeModel>()
    private val storiesListAdapter = StoriesListAdapter()

    private val listStories = ArrayList<String>()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentStoriesGameHomeListBinding
        get() = FragmentStoriesGameHomeListBinding::inflate

    override fun setup() {
        loadListData()
        initRecyclerview()
    }

    private fun loadListData() {

        if (listStoriesModel.isEmpty()) {
            listStories.clear()
            (1..33).forEach {

                listStories.add("https://s5.uupload.ir/files/mrghooghooli/story_chicki/s_$it.mp3")
            }

            context?.let { context ->
                listStories.forEachIndexed { i, url ->
                   val model = DataStoriesGameHomeModel(
                        i,
                        context.getStringByName("english_${i+1}"),
                        context.getImageDrawableByName("pa_${i+1}"),
                        context.getStringByName("stories_${i+1}"),
                        url
                    )
                    listStoriesModel.add(model)
                }
            }


        }
    }

    private fun initRecyclerview() {
        binding.recyclerview.adapter = storiesListAdapter.apply {
            setOnItemClickListener { dataStoriesGameHomeModel, i ->
                val navigate =
                    StoriesFragmentListDirections.actionStoriesFragmentListToStoriesDetailsFragmentGameHome(
                        dataStoriesGameHomeModel
                    )
                navigate(navigate)
            }
            submitList(listStoriesModel)
        }
    }

}