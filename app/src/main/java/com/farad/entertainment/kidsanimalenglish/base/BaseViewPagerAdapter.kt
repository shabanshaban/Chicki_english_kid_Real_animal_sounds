package com.farad.entertainment.kidsanimalenglish.base

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

abstract class BaseViewPagerAdapter<T: BaseNestedFragment<*, *>>(
    private val listFragment: ArrayList<T>,
    fm: Fragment
) : FragmentStateAdapter(fm) {

    override fun createFragment(position: Int): Fragment {
        return listFragment[position]
    }

    override fun getItemCount(): Int = listFragment.size

    fun getFragmentAt(position: Int): BaseNestedFragment<*, *> {
        return listFragment[position]
    }

}