package com.farad.entertainment.kidsanimalenglish.ui.fragment.kindergarten

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.farad.entertainment.kidsanimalenglish.MainNavGraphDirections
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.data.model.VideoPlayerModel
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.KindergartenModel
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.ListKindergarten
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.getKindergarten
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.getLinkVideo
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentKindergartenBinding
import com.farad.entertainment.kidsanimalenglish.ui.fragment.kindergarten.adapter.ListKindergartenAdapter
import com.farad.entertainment.kidsanimalenglish.utils.animClickFast
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener

class KindergartenFragment : BottomNavigationFragment<FragmentKindergartenBinding>() {


    private val args by navArgs<KindergartenFragmentArgs>()

    private val listKindergartenAdapter = ListKindergartenAdapter()

    private val listData = ArrayList<KindergartenModel>()

    private var nestedScroll = 0
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentKindergartenBinding
        get() = FragmentKindergartenBinding::inflate

    override fun setup() {
        listener()
        if (listData.isEmpty()) {
            context?.getKindergarten()?.let { list ->
                listData.addAll(list)
                listKindergartenAdapter.submitList(listData)
            }

        }
        initRecyclerview()
        binding.nestedScrollView.scrollY = nestedScroll


    }

    override fun onPause() {
        super.onPause()
        nestedScroll = binding.nestedScrollView.scrollY
    }

    private fun clickItemKindergarten(model: KindergartenModel) {

        if (args.idAnimal == -1) {
            val navigate =
                KindergartenFragmentDirections.actionKindergartenFragmentToListVideoKindergartenFragment(
                    model
                )
            navigate(navigate)
        } else {

            showDialogVideoErrorNetwork {

                goPlayer(model)
            }
        }


    }

    private fun goPlayer(model: KindergartenModel) {
            val navigate = MainNavGraphDirections.globalVideoPlayer(
                VideoPlayerModel(
                    url = model.id.getLinkVideo(args.idAnimal),
                    visibilityControlView = true,
                    imageHeader = R.drawable.top_bg_kinder,
                    titleHeaderRight = model.title,
                    imageHeaderRight = model.iconFilm,
                    isWhatsapp = false,
                    isDescription = model.id != ListKindergarten.STORIES,
                    typeKindergarten = model.id
                )
            )
            navigate(navigate)
    }


    private fun initRecyclerview() {
        binding.recyclerView.adapter = listKindergartenAdapter.apply {
            setOnItemClickListener {
                clickItemKindergarten(it)
            }
        }
    }

    private fun listener() {

        binding.lineWhatsapp.setOnSafeClickListener {
            it.animClickFast()
        }


    }
}