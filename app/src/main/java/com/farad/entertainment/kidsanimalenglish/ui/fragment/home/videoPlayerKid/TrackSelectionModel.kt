package com.farad.entertainment.kidsanimalenglish.ui.fragment.home.videoPlayerKid

import androidx.media3.common.TrackSelectionOverride
import androidx.media3.exoplayer.trackselection.MappingTrackSelector

data class TrackSelectionModel(
    var mappedTrackInfo: MappingTrackSelector.MappedTrackInfo? = null,
    var rendererIndex: Int = 0,
    var isDisabled : Boolean = false,
    var overrides: TrackSelectionOverride? = null
)
