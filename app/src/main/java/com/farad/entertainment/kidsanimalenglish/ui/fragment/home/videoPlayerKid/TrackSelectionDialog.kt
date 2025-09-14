package com.farad.entertainment.kidsanimalenglish.ui.fragment.home.videoPlayerKid

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialog
import androidx.media3.common.C
import androidx.media3.common.TrackSelectionOverride
import androidx.media3.common.TrackSelectionParameters
import androidx.media3.common.util.Assertions
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.exoplayer.trackselection.MappingTrackSelector
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.base.BaseDialogFragment
import com.farad.entertainment.kidsanimalenglish.base.BaseNestedFragment
import com.farad.entertainment.kidsanimalenglish.databinding.DialogTrackSelectionParentBinding


@UnstableApi
class TrackSelectionDialog(
    private val titleId: Int,
    private val trackSelector: DefaultTrackSelector,
    private val onDismissListener: DialogInterface.OnDismissListener,
) : BaseDialogFragment<DialogTrackSelectionParentBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogTrackSelectionParentBinding
        get() = DialogTrackSelectionParentBinding::inflate

    private val tabFragments = SparseArray<BaseNestedFragment<*, TrackSelectionModel>>()
    private val fragmentArrays = ArrayList<BaseNestedFragment<*, TrackSelectionModel>>()
    private val tabTrackTypes = ArrayList<Int>()
    private var mappedTrackInfo = Assertions.checkNotNull(trackSelector.currentMappedTrackInfo)
    private var initialParameters = trackSelector.parameters

    override fun setup() {
        setupViewPager()
        setupListener()
    }

    private fun setupListener() {
        binding.apply {
            trackSelectionDialogCancelButton.setOnClickListener { dismiss() }
            trackSelectionDialogOkButton.setOnClickListener {
                val builder = TrackSelectionParameters.Builder(requireContext())
                builder.let {
                    for (i in 0 until mappedTrackInfo.rendererCount) {
                        val overrides = getOverrides(i)
                        if (overrides.isNotEmpty()) {
                            it.clearOverridesOfType(i)
                            it.setTrackTypeDisabled(i, getIsDisabled(i))

                            it.addOverride(
                                overrides[i]
                            )
                        }
                    }
                    trackSelector.setParameters(it.build())
                }
                dismiss()
            }
        }
    }

    private fun setupViewPager() {
        for (i in 0 until mappedTrackInfo.rendererCount) {
            if (showTabForRenderer(mappedTrackInfo, i)) {
                val tabFragment = TrackSelectionViewFragment()
                tabFragment.newInstance(
                    TrackSelectionModel(
                        mappedTrackInfo,
                        i,
                        initialParameters.getRendererDisabled(i),
                        initialParameters.overrides[mappedTrackInfo.getTrackGroups(i).get(i)]
                    )
                )
                tabFragments.put(i, tabFragment)
                fragmentArrays.add(tabFragment)
                tabTrackTypes.add(mappedTrackInfo.getRendererType(i))
            }
        }

        binding.apply {
            trackSelectionDialogViewPager.adapter = TrackSelectionViewPagerAdapter(
                requireContext(),
                fragmentArrays, this@TrackSelectionDialog
            )
        }
    }


    private fun getIsDisabled(rendererIndex: Int): Boolean {
        val renderView = tabFragments[rendererIndex]
        return if (renderView != null) {
            (renderView as TrackSelectionViewFragment).isDisabled
        } else false
    }

    private fun getOverrides(rendererIndex: Int): List<TrackSelectionOverride> {
        val renderView = tabFragments[rendererIndex]
        return if (renderView != null)
            (renderView as TrackSelectionViewFragment).overrides!!
        else emptyList()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AppCompatDialog(requireActivity(), R.style.TrackSelectionDialogThemeOverlay)
        dialog.setTitle(titleId)
        return dialog
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissListener.onDismiss(dialog)
    }


    companion object {
        fun willHaveContent(trackSelector: DefaultTrackSelector?): Boolean {
            val mappedTrackInfo = trackSelector?.currentMappedTrackInfo
            return mappedTrackInfo != null && willHaveContent(mappedTrackInfo)
        }

        private fun willHaveContent(mappedTrackInfo: MappingTrackSelector.MappedTrackInfo): Boolean {
            for (i in 0 until mappedTrackInfo.rendererCount) {
                if (showTabForRenderer(mappedTrackInfo, i)) {
                    return true
                }
            }
            return false
        }

        private fun showTabForRenderer(
            mappedTrackInfo: MappingTrackSelector.MappedTrackInfo,
            rendererIndex: Int,
        ): Boolean {
            val trackGroupArray = mappedTrackInfo.getTrackGroups(rendererIndex)
            if (trackGroupArray.length == 0) {
                return false
            }
            val trackType = mappedTrackInfo.getRendererType(rendererIndex)
            return isSupportedTrackType(trackType)
        }

        private fun isSupportedTrackType(trackType: Int): Boolean {
            return trackType == C.TRACK_TYPE_VIDEO
        }
    }

}