package com.farad.entertainment.kidsanimalenglish.kids_ringtone_english.home

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.data.apiService.ApiService
import com.farad.entertainment.kidsanimalenglish.data.model.DataDialog
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentMainSoundBinding
import com.farad.entertainment.kidsanimalenglish.kids_ringtone_english.DataMainModel
import com.farad.entertainment.kidsanimalenglish.kids_ringtone_english.DialogClose
import com.farad.entertainment.kidsanimalenglish.kids_ringtone_english.getListData
import com.farad.entertainment.kidsanimalenglish.utils.checkLanguage
import com.farad.entertainment.kidsanimalenglish.utils.playSoundMediaPlayer
import com.farad.entertainment.kidsanimalenglish.utils.safeShow
import com.farad.entertainment.kidsanimalenglish.utils.setBackGround
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import org.koin.android.ext.android.inject


class MainFragmentRingTone : BottomNavigationFragment<FragmentMainSoundBinding>() {

    private var mediaPlayer: MediaPlayer? = null
    private var listStoryModel = ArrayList<DataMainModel>()

    private var storyAdapter = AnimalAdapter()
    private val apiService: ApiService by inject()
    private var countClickItem = 0
    private var serRequest = false

    //data dialog exit
    private var dataDialog: DataDialog? = null
    private var isMute = false

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainSoundBinding
        get() = FragmentMainSoundBinding::inflate


    override fun onDestroyView() {
        mediaPlayer?.pause()
        mediaPlayer?.release()
        mediaPlayer = null

        super.onDestroyView()

    }

    override fun setup() {
        listener()
        getDataCategory()
        initRecyclerview()
        getMainActivity()?.setOnBackPressedListener {
            showDialogExit()
        }
        initMediaPlayer()
        getDataDialogExit()

        if (isNullView().not()) {
            try {
                binding.root.post {
                    binding.root.setBackGround(image = R.drawable.back)
                    getMainActivity()?.setBackGroundRoot(true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


    }

    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer()
    }

    override fun onStart() {
        super.onStart()
        if (isNullView().not()) {
            try {
                binding.root.post {
                    binding.root.setBackGround(image = R.drawable.back)

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getDataDialogExit() {
        checkLanguage(farsi = {
            apiService.readData {
                dataDialog = it
            }
        }, english = {

        })

    }

    private fun showDialogExit() {
        try {
            val dialogClose = DialogClose()
            dialogClose.dataDialog = dataDialog
            dialogClose.setOnExitClickListener {
                getMainActivity()?.finish()
            }
            dialogClose.isCanceledOnTouchOutside = true


            dialogClose.safeShow(childFragmentManager)
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    private fun initRecyclerview() {
        binding.recyclerview.adapter = storyAdapter.apply {

            setOnItemClickListener {

                countClickItem++

                if (countClickItem >= 7 && serRequest.not()) {
                    getMainActivity()?.showBannerFull {
                        serRequest = false
                    }
                    countClickItem = 0
                    serRequest = true

                }
                mediaPlayer?.playSoundMediaPlayer(context, it.soundPlay, 3)

            }
        }
    }


    private fun listener() {

        binding.btnChicki.setOnSafeClickListener {

            val navigate = MainFragmentRingToneDirections.actionMainFragmentRingToneToMainFragment()
            navigate(navigate)
        }



        binding.btnSound.setOnSafeClickListener {
            if (isMute) {

                mediaPlayer?.setVolume(1f, 1f)
                binding.imageSound.setImageResource(R.drawable.ic_sound_new)
            } else {
                mediaPlayer?.setVolume(0f, 0f)
                binding.imageSound.setImageResource(R.drawable.no_sound)
            }
            isMute = !isMute
        }

        binding.edSearch.doAfterTextChanged {
            val word = it.toString().trim()
            if (word.isNotEmpty()) {
                listStoryModel.filter {
                    it.titleFarsi.uppercase()
                        .contains(word.uppercase()) || it.titleEnglish.uppercase()
                        .contains(word.uppercase())
                }.apply {
                    if (this.isNotEmpty())
                        storyAdapter.submitList(this)
                }
            } else {
                storyAdapter.submitList(listStoryModel)
            }


        }
    }

    private fun getDataCategory() {
        context?.getListData()?.let {
            listStoryModel.clear()
            listStoryModel.addAll(it)
            storyAdapter.submitList(it)
        }

    }


}