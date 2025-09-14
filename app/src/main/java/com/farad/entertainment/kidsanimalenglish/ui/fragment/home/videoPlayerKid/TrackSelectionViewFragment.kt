package com.farad.entertainment.kidsanimalenglish.ui.fragment.home.videoPlayerKid

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.media3.common.TrackSelectionOverride
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.trackselection.MappingTrackSelector
import com.farad.entertainment.kidsanimalenglish.base.BaseFragment
import com.farad.entertainment.kidsanimalenglish.base.BaseNestedFragment
import com.farad.entertainment.kidsanimalenglish.databinding.DialogTrackSelectionBinding
import com.farad.entertainment.kidsanimalenglish.cv.MyTrackSelectionView


@UnstableApi
class TrackSelectionViewFragment :
    BaseNestedFragment<DialogTrackSelectionBinding, TrackSelectionModel>(),
    MyTrackSelectionView.MyTrackSelectionListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogTrackSelectionBinding
        get() = DialogTrackSelectionBinding::inflate

    private var mappedTrackInfo: MappingTrackSelector.MappedTrackInfo? = null
    private var rendererIndex = 0
    var isDisabled = false
    var overrides: List<TrackSelectionOverride>? = null

    override fun newInstance(data: TrackSelectionModel): BaseFragment<*> {
        mappedTrackInfo = data.mappedTrackInfo
        rendererIndex = data.rendererIndex
        isDisabled = data.isDisabled
        overrides = data.overrides?.let { listOf(it) } ?: listOf()
        return this
    }

    override fun setup() {
        binding.exoMyTrackSelectionView.apply {
            setShowDisableOption(false)
            setAllowMultipleOverrides(false)
            setAllowAdaptiveSelections(false)
            init(mappedTrackInfo,
                rendererIndex,
                isDisabled,
                overrides,
                this@TrackSelectionViewFragment)
        }
    }

    override fun onTrackSelectionChanged(
        isDisabled: Boolean,
        overrides: List<TrackSelectionOverride>,
    ) {
        this.isDisabled = isDisabled
        this.overrides = overrides
    }
}