package com.farad.entertainment.kidsanimalenglish.ui.fragment.kindergarten

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.navArgs
import com.farad.entertainment.kidsanimalenglish.MainNavGraphDirections
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.ListKindergarten
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.getListData
import com.farad.entertainment.kidsanimalenglish.data.model.VideoPlayerModel
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.AnimalModel
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentKindergartenListVideoBinding
import com.farad.entertainment.kidsanimalenglish.ui.fragment.kindergarten.adapter.ListVideoKindergartenAdapter
import com.farad.entertainment.kidsanimalenglish.utils.setOnSafeClickListener
import com.farad.entertainment.kidsanimalenglish.utils.visibleOrGone

class ListVideoKindergartenFragment : BottomNavigationFragment<FragmentKindergartenListVideoBinding>() {


    private val listTitle = ArrayList<AnimalModel>()
    private val args by navArgs<ListVideoKindergartenFragmentArgs>()

    private val adapterVideo = ListVideoKindergartenAdapter()


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentKindergartenListVideoBinding
        get() = FragmentKindergartenListVideoBinding::inflate

    override fun setup() {
        getArgument()
        setTitleList()
        initRecyclerview()
        listener()

    }


    private fun listener() {

        binding.imageHeaderLeft.visibleOrGone(args.dataModel.id == ListKindergarten.ORIGAMI)
        binding.tvHeaderLeft.visibleOrGone(args.dataModel.id == ListKindergarten.ORIGAMI)
        binding.imageHeaderLeft.setOnSafeClickListener {
            val navigate =
                ListVideoKindergartenFragmentDirections.actionListVideoKindergartenFragmentToOrigamiParkFragment()
            navigate(navigate)
        }
        binding.tvHeaderLeft.setOnSafeClickListener {
            val navigate =
                ListVideoKindergartenFragmentDirections.actionListVideoKindergartenFragmentToOrigamiParkFragment()
            navigate(navigate)
        }
        binding.edSearch.doAfterTextChanged {
            val word = it.toString().trim()
            if (word.isNotEmpty()) {
                listTitle.filter { it.title.uppercase().contains(word.uppercase()) }.apply {
                    if (this.isNotEmpty())
                        adapterVideo.submitList(this)
                }

            } else {
                adapterVideo.submitList(listTitle)
            }
        }
    }

    private fun initRecyclerview() {

        binding.recyclerView.adapter = adapterVideo.apply {
            submitList(listTitle)
            setOnItemClickListener {url,model->
                args.dataModel.id
                showDialogVideoErrorNetwork {
                    val navigate = MainNavGraphDirections.globalVideoPlayer(
                        VideoPlayerModel(
                            url = url,
                            visibilityControlView = true,
                            imageHeader = R.drawable.top_bg_kinder,
                            titleHeaderRight = args.dataModel.title,
                            imageHeaderRight = args.dataModel.iconFilm,
                            isWhatsapp = false,
                            isDescription = args.dataModel.id != ListKindergarten.STORIES,
                            typeKindergarten = args.dataModel.id,
                            isLock = model.isLock,
                            id = model.id.numberId.toLong()

                        )
                    )
                    navigate(navigate)
                }

            }
        }
    }

    private fun setTitleList() {
        listTitle.clear()
        context?.getListData()?.forEach {
            listTitle.add(it)
        }

    }

    private fun getArgument() {
        args.dataModel.apply {
            binding.tvTitle.text = title
            adapterVideo.typeKindergarten = args.dataModel.id
        }
    }
}