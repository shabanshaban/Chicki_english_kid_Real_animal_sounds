package com.farad.entertainment.kidsanimalenglish.ui.fragment.kindergarten.parkOrigami

import android.view.LayoutInflater
import android.view.ViewGroup
import com.farad.entertainment.kidsanimalenglish.MainNavGraphDirections
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.ListKindergarten
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.getOrigamiParkList
import com.farad.entertainment.kidsanimalenglish.data.model.VideoPlayerModel
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentOrigamiParkBinding
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class OrigamiParkFragment : BottomNavigationFragment<FragmentOrigamiParkBinding>() {


    private val origamiParkAdapter = OrigamiParkAdapter()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOrigamiParkBinding
        get() = FragmentOrigamiParkBinding::inflate

    override fun setup() {
        initRecyclerview()
    }

    private fun initRecyclerview() {
        val flexboxLayoutManager = FlexboxLayoutManager(context)
        flexboxLayoutManager.justifyContent = JustifyContent.SPACE_BETWEEN
        origamiParkAdapter.apply {
            submitList(getOrigamiParkList())
            setOnItemClickListener {
                val model = VideoPlayerModel(
                    url = it.urlVideo,
                    visibilityControlView = true,
                    imageHeader = R.drawable.top_bg_kinder,
                    titleHeaderRight = getString(R.string.origami_text),
                    imageHeaderRight = R.drawable.icon_origami,
                    isWhatsapp = false,
                    isDescription = false,
                    typeKindergarten = ListKindergarten.ORIGAMI
                )
                val navigate = MainNavGraphDirections.globalVideoPlayer(model)
                navigate(navigate)
            }

        }
        binding.recyclerView.apply {
            layoutManager = flexboxLayoutManager
            adapter = origamiParkAdapter
        }
    }
}