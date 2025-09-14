package com.farad.entertainment.kidsanimalenglish.ui.fragment.home.aboutAnimal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentAboutAnimalBinding
import com.farad.entertainment.kidsanimalenglish.utils.animClickFast
import com.farad.entertainment.kidsanimalenglish.utils.getStringByName
import com.farad.entertainment.kidsanimalenglish.utils.invitedFriend
import com.farad.entertainment.kidsanimalenglish.utils.loadImage
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.shareText

class AboutAnimalFragment : BottomNavigationFragment<FragmentAboutAnimalBinding>() {


    private val args by navArgs<AboutAnimalFragmentArgs>()
    private var textInfoAnimal = ""
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAboutAnimalBinding
        get() = FragmentAboutAnimalBinding::inflate

    override fun setup() {
        getArgument()
        listener()
    }

    private fun listener() {
        binding.tvNameAnimal.text=args.animalModel.title
        binding.imageAnimal.loadImage(args.animalModel.image)
        binding.btnShareText.setOnSafeClickListener {
            it.animClickFast()
          context?.shareText(textInfoAnimal, getString(R.string.title_share_s, args.animalModel.title))
        }
        binding.btnShare.setOnSafeClickListener {
            it.animClickFast()
           context?.invitedFriend()
        }
    }

    private fun getArgument() {
        val idAnimal = args.idAnimal + 1
        context?.getStringByName("t_information$idAnimal")?.let {
            textInfoAnimal =it
        }

        binding.tvInfoAnimal.text = textInfoAnimal.replace("-","\n")

       /* try {
            when ((idAnimal - 1) % 4) {
                0 -> {
                    binding.linTextInfo.setBackgroundResource(R.drawable.shape_shadow_red)
                }

                1 -> {
                    binding.linTextInfo.setBackgroundResource(R.drawable.shape_shadow_blue)
                }

                2 -> {
                    binding.linTextInfo.setBackgroundResource(R.drawable.shape_shadow_green_more)
                }

                3 -> {
                    binding.linTextInfo.setBackgroundResource(R.drawable.shape_shadow_orange)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }*/

    }
}