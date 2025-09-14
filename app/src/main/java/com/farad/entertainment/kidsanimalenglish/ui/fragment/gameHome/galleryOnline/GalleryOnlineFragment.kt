package com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.galleryOnline

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.getListData
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.getListImageBigAnimal
import com.farad.entertainment.kidsanimalenglish.data.model.ModelGalleryOnline
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentOnlineGalleryBinding
import com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.galleryOnline.adapter.AdapterBigImageGallery
import com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.galleryOnline.adapter.AdapterImageGallery
import com.farad.entertainment.kidsanimalenglish.utils.debounce
import com.farad.entertainment.kidsanimalenglish.utils.playSoundMediaPlayer


class GalleryOnlineFragment : BottomNavigationFragment<FragmentOnlineGalleryBinding>() {

    private val listImageOnline = ArrayList<String>()
    private val listImageGallery = ArrayList<ModelGalleryOnline>()

    private val adapterImageGallery = AdapterImageGallery()
    private val adapterBigImageGallery = AdapterBigImageGallery()
    private var endAnimPlayer: ((() -> Unit) -> Unit)? = null

    private var mediaPlayer: MediaPlayer? = null
    private val pagerSnapHelper by lazy { PagerSnapHelper() }
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOnlineGalleryBinding
        get() = FragmentOnlineGalleryBinding::inflate

    override fun setup() {
        initMediaPlayer()
        initImageGallery()
        initRecyclerview()
        initDebounce()
    }

    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer()
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun initDebounce() {
        endAnimPlayer = debounce(50, coroutineScope = lifecycleScope) { action ->
            if (_binding != null)
                action()
        }
    }

    private fun initRecyclerview() {
        binding.recyclerview.adapter = adapterImageGallery.apply {
            submitList(listImageGallery)
            setOnItemClickListener { position, model ->
                binding.recyclerviewBig.smoothScrollToPosition(position)

                if (model.isSelected) {
                    mediaPlayer?.playSoundMediaPlayer(context, model.soundAnimal)
                } else {
                    mediaPlayer?.playSoundMediaPlayer(context, model.soundName, onCompletion = {
                        mediaPlayer?.playSoundMediaPlayer(context, model.soundAnimal)
                    })
                }
            }
            setOnItemNameClickListener { position, model ->
                binding.recyclerviewBig.smoothScrollToPosition(position)
                if (model.isSelected) {
                    mediaPlayer?.playSoundMediaPlayer(context, model.soundName)
                } else {
                    mediaPlayer?.playSoundMediaPlayer(context, model.soundName, onCompletion = {
                        mediaPlayer?.playSoundMediaPlayer(context, model.soundAnimal)
                    })
                }

            }
        }
        binding.recyclerviewBig.adapter = adapterBigImageGallery.apply {
            submitList(listImageOnline)

        }
        val linearLayoutManager = (binding.recyclerviewBig.layoutManager as LinearLayoutManager)

        binding.recyclerviewBig.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val position = linearLayoutManager.findFirstVisibleItemPosition()
                endAnimPlayer?.invoke {
                    binding.recyclerview.smoothScrollToPosition(position)
                    adapterImageGallery.selectLevelId = listImageGallery[position].id

                    if (adapterImageGallery.selectedPosition >= 0)
                        adapterImageGallery.notifyItemChanged(adapterImageGallery.selectedPosition)
                    adapterImageGallery.selectedPosition = position
                    adapterImageGallery.notifyItemChanged(adapterImageGallery.selectedPosition)
                }

            }
        })
        pagerSnapHelper.attachToRecyclerView(binding.recyclerviewBig)
    }

    private fun initImageGallery() {

       /* (1..75).forEach {
            listImageOnline.add("https://s9.uupload.ir/files/mrghooghooli/mrghooghooli/picfull_vertical/pfull_$it.jpg")
        }*/


        if (listImageOnline.isEmpty()) {
            listImageOnline.clear()
            listImageOnline.addAll(getListImageBigAnimal())
        }



        context?.getListData()?.forEachIndexed { index, animalModel ->
            listImageGallery.add(
                ModelGalleryOnline(
                    index,
                    animalModel.title,
                    listImageOnline[index],
                    animalModel.soundNameEnglish,
                    animalModel.soundAnimal
                )
            )
        }
        adapterImageGallery.submitList(listImageGallery)
    }
}