package com.farad.entertainment.kidsanimalenglish.ui.fragment.home.videoPlayerKid

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.farad.entertainment.kidsanimalenglish.base.BaseNestedFragment
import com.farad.entertainment.kidsanimalenglish.base.BaseViewPagerAdapter

class TrackSelectionViewPagerAdapter(
    val context: Context,
    listFragment: ArrayList<BaseNestedFragment<out ViewBinding, TrackSelectionModel>>,
    fm: Fragment,
) : BaseViewPagerAdapter<BaseNestedFragment<*, TrackSelectionModel>>(listFragment , fm)