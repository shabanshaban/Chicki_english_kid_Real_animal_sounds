package com.farad.entertainment.kidsanimalenglish.base

import androidx.viewbinding.ViewBinding

abstract class BaseNestedFragment<VB : ViewBinding, T> : BaseFragment<VB>() {

    abstract fun newInstance(data: T): BaseFragment<*>

}

