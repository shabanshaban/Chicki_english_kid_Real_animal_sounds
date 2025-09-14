package com.farad.entertainment.kidsanimalenglish.di

import com.farad.entertainment.kidsanimalenglish.ui.activity.main.ViewModelMain
import com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.scratchGame.vm.ScratchGameViewModel
import com.farad.entertainment.kidsanimalenglish.ui.fragment.gameHome.wordGame.vm.WordGameViewModel
import com.farad.entertainment.kidsanimalenglish.ui.fragment.youtube.YouTubeVideoPlayerFragment
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { WordGameViewModel(get(),androidApplication()) }
    viewModel { ViewModelMain(get(),get()) }
    viewModelOf(::ScratchGameViewModel)
    viewModelOf(::ScratchGameViewModel)
    viewModelOf(::ScratchGameViewModel)

}