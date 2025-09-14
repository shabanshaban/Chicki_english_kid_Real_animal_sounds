package com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.data.manager.SharedPreferencesManager
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.PlaySoundAnimal
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.TypeShowImageAnimal
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.ZoomImageAnimal
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentSettingBinding
import com.farad.entertainment.kidsanimalenglish.utils.checkLanguage
import com.farad.entertainment.kidsanimalenglish.utils.gone
import org.koin.android.ext.android.inject

class SettingFragment : BottomNavigationFragment<FragmentSettingBinding>() {

    private val sharedPreferencesManager: SharedPreferencesManager by inject()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSettingBinding
        get() = FragmentSettingBinding::inflate

    override fun setup() {
        listener()
        checkData()

        checkLanguage(farsi = {
            binding.btnNameFrance.gone()
            binding.btnNameEnglish.gone()
        }, english = {

        })
    }


    private fun checkData() {
        when (sharedPreferencesManager.zoomImageAnimal) {
            ZoomImageAnimal.ZOOM_IN -> {
                binding.radioImageBig.check(binding.btnImageBig.id)
            }

            ZoomImageAnimal.ZOOM_OUT -> {
                binding.radioImageBig.check(binding.btnDoNotImageZoom.id)
            }

            else -> {
                binding.radioImageBig.check(binding.btnImageBig.id)
            }
        }

        when (sharedPreferencesManager.playSoundAnimal) {
            PlaySoundAnimal.ENGLISH -> {
                binding.radioSound.check(binding.btnNameEnglish.id)
            }

            PlaySoundAnimal.ENGLISH_AND_FRANCE -> {
                binding.radioSound.check(binding.btnNameAndSound.id)
            }

            PlaySoundAnimal.FRANCE -> {
                binding.radioSound.check(binding.btnNameAndSound.id)
            }

            else -> {
                binding.radioSound.check(binding.btnNameAndSound.id)
            }

        }
        when (sharedPreferencesManager.typeShowImageAnimal) {
            TypeShowImageAnimal.REAL -> {
                binding.radioImageAnimated.check(binding.btnReal.id)
            }

            TypeShowImageAnimal.ANIMATED -> {
                binding.radioImageAnimated.check(binding.btnAnimated.id)
            }

            else -> {
                binding.radioImageAnimated.check(binding.btnAnimated.id)
            }
        }


    }

    private fun listener() {
        binding.radioSound.setOnCheckedChangeListener { radioGroup, id ->

            when (id) {
                binding.btnNameAndSound.id -> {
                    sharedPreferencesManager.playSoundAnimal = PlaySoundAnimal.ENGLISH_AND_FRANCE
                }

                binding.btnNameFrance.id -> {
                    sharedPreferencesManager.playSoundAnimal = PlaySoundAnimal.FRANCE
                }

                binding.btnNameEnglish.id -> {
                    sharedPreferencesManager.playSoundAnimal = PlaySoundAnimal.ENGLISH
                }
            }

        }
        binding.radioImageAnimated.setOnCheckedChangeListener { radioGroup, id ->

            when (id) {
                binding.btnAnimated.id -> {
                    sharedPreferencesManager.typeShowImageAnimal = TypeShowImageAnimal.ANIMATED
                }

                binding.btnReal.id -> {
                    sharedPreferencesManager.typeShowImageAnimal = TypeShowImageAnimal.REAL
                }
            }

        }
        binding.radioImageBig.setOnCheckedChangeListener { radioGroup, id ->
            when (id) {
                binding.btnImageBig.id -> {
                    sharedPreferencesManager.zoomImageAnimal = ZoomImageAnimal.ZOOM_IN
                }

                binding.btnDoNotImageZoom.id -> {
                    sharedPreferencesManager.zoomImageAnimal = ZoomImageAnimal.ZOOM_OUT
                }
            }

        }
    }


}