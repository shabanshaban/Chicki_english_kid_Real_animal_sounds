package com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.listAnimalPaint

import android.view.LayoutInflater
import android.view.ViewGroup
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.AnimalModel
import com.farad.entertainment.kidsanimalenglish.data.model.enumModel.getListData
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentListPaintBinding

class ListAnimalPaintFragment : BottomNavigationFragment<FragmentListPaintBinding>()  {

    private val listAnimalPaintAdapter = ListAnimalPaintAdapter()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentListPaintBinding
        get() = FragmentListPaintBinding::inflate

    override fun setup() {
        initRecyclerview()
        setData()
    }

    private fun setData(){
        context?.getListData()?.let {
            listAnimalPaintAdapter.submitList(it)
        }
    }
    private fun initRecyclerview(){
        binding.recyclerview.adapter = listAnimalPaintAdapter.apply {
            setOnItemClickListener {
                showDialogChose(it)
            }
        }
    }

    private fun showDialogChose(animalModel: AnimalModel) {
        val title       = getString(R.string.do_you_want_to_paint)
        val textLeft    = getString(R.string.painting)
        val textRight   = getString(R.string.Coloring)
        showDialogConfirmCancel(title, textLeft, textRight
            , leftListener = {
                val navigate= ListAnimalPaintFragmentDirections.actionListAnimalPaintFragmentToColoringFragment(animalModel)
                navigate(navigate)

            }, rightListener = {
                val navigate= ListAnimalPaintFragmentDirections.actionListAnimalPaintFragmentToPaintingFragment(animalModel)
                navigate(navigate)
            })
    }
}