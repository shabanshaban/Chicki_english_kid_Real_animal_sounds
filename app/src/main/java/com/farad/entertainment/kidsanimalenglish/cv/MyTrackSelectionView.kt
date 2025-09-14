package com.farad.entertainment.kidsanimalenglish.cv

import android.content.Context
import android.util.AttributeSet
import android.util.Pair
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckedTextView
import android.widget.LinearLayout
import androidx.annotation.AttrRes
import androidx.media3.common.C
import androidx.media3.common.TrackSelectionOverride
import androidx.media3.common.util.Assertions
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.RendererCapabilities
import androidx.media3.exoplayer.source.TrackGroupArray
import androidx.media3.exoplayer.trackselection.MappingTrackSelector
import androidx.media3.ui.DefaultTrackNameProvider
import androidx.media3.ui.TrackNameProvider
import com.farad.entertainment.kidsanimalenglish.R

import java.util.*

/**
 * A view for making track selections.
 */
@UnstableApi
@Suppress("unused")
class MyTrackSelectionView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {
    /**
     * Listener for changes to the selected tracks.
     */
    interface MyTrackSelectionListener {
        /**
         * Called when the selected tracks changed.
         *
         * @param isDisabled Whether the renderer is disabled.
         * @param overrides  List of selected track selection overrides for the renderer.
         */
        fun onTrackSelectionChanged(
            isDisabled: Boolean,
            overrides: List<TrackSelectionOverride>,
        )
    }

    private val selectableItemBackgroundResourceId: Int
    private val inflater: LayoutInflater
    private val disableView: CheckedTextView
    private val defaultView: CheckedTextView
    private val componentListener: ComponentListener
    private val overrides: SparseArray<TrackSelectionOverride>
    private var allowAdaptiveSelections = false
    private var allowMultipleOverrides = false
    private var trackNameProvider: TrackNameProvider
    private var trackViews: Array<Array<CheckedTextView?>?> = arrayOf()
    private var mappedTrackInfo: MappingTrackSelector.MappedTrackInfo? = null
    private var rendererIndex = 0
    private var trackGroups: TrackGroupArray

    /**
     * Returns whether the renderer is disabled.
     */
    private var isDisabled = false
    private var listener: MyTrackSelectionListener? = null

    /**
     * Sets whether adaptive selections (consisting of more than one track) can be made using this
     * selection view.
     *
     *
     * For the view to enable adaptive selection it is necessary both for this feature to be
     * enabled, and for the target renderer to support adaptation between the available tracks.
     *
     * @param allowAdaptiveSelections Whether adaptive selection is enabled.
     */
    fun setAllowAdaptiveSelections(allowAdaptiveSelections: Boolean) {
        if (this.allowAdaptiveSelections != allowAdaptiveSelections) {
            this.allowAdaptiveSelections = allowAdaptiveSelections
            updateViews()
        }
    }

    /**
     * Sets whether tracks from multiple track groups can be selected. This results in multiple [ ] to be returned by [ ][.getOverrides].
     *
     * @param allowMultipleOverrides Whether multiple track selection overrides can be selected.
     */
    fun setAllowMultipleOverrides(allowMultipleOverrides: Boolean) {
        if (this.allowMultipleOverrides != allowMultipleOverrides) {
            this.allowMultipleOverrides = allowMultipleOverrides
            if (!allowMultipleOverrides && overrides.size() > 1) {
                for (i in overrides.size() - 1 downTo 1) {
                    overrides.remove(i)
                }
            }
            updateViews()
        }
    }

    /**
     * Sets whether an option is available for disabling the renderer.
     *
     * @param showDisableOption Whether the disable option is shown.
     */
    fun setShowDisableOption(showDisableOption: Boolean) {
        disableView.visibility = if (showDisableOption) VISIBLE else GONE
    }


    fun setTrackNameProvider(trackNameProvider: TrackNameProvider?) {
        this.trackNameProvider = Assertions.checkNotNull(trackNameProvider)
        updateViews()
    }


    fun init(
        mappedTrackInfo: MappingTrackSelector.MappedTrackInfo?,
        rendererIndex: Int,
        isDisabled: Boolean,
        overrides: List<TrackSelectionOverride>?,
        listener: MyTrackSelectionListener?,
    ) {
        this.mappedTrackInfo = mappedTrackInfo
        this.rendererIndex = rendererIndex
        this.isDisabled = isDisabled
        this.listener = listener
        overrides?.let {
            val maxOverrides =
                if (allowMultipleOverrides) overrides.size else overrides.size.coerceAtMost(1)
            for (i in 0 until maxOverrides) {
                val override = overrides[i]
                this.overrides.put(i, override)
            }
        }
        updateViews()
    }

    /**
     * Returns the list of selected track selection overrides. There will be at most one override for
     * each track group.
     */
    private fun getOverrides(): List<TrackSelectionOverride> {
        val overrideList: MutableList<TrackSelectionOverride> = ArrayList(overrides.size())
        for (i in 0 until overrides.size()) {
            overrideList.add(overrides.valueAt(i))
        }
        return overrideList
    }

    // Private methods.
    private fun updateViews() {
        // Remove previous per-track views.
        for (i in childCount - 1 downTo 3) {
            removeViewAt(i)
        }
        if (mappedTrackInfo == null) {
            // The view is not initialized.
            disableView.isEnabled = false
            defaultView.isEnabled = false
            return
        }
        disableView.isEnabled = true
        defaultView.isEnabled = true
        trackGroups = mappedTrackInfo!!.getTrackGroups(rendererIndex)

        // Add per-track views.

        // Add per-track views.
        val a = arrayOfNulls<CheckedTextView>(trackGroups.length)
        trackViews = arrayOfNulls(a.size)
        val enableMultipleChoiceForMultipleOverrides = shouldEnableMultiGroupSelection()
        for (groupIndex in 0 until trackGroups.length) {
            val group = trackGroups[groupIndex]
            val enableMultipleChoiceForAdaptiveSelections =
                shouldEnableAdaptiveSelection(groupIndex)
            trackViews[groupIndex] = arrayOfNulls(group.length)
            for (trackIndex in 0 until group.length) {
                if (trackIndex == 0) {
                    addView(inflater
                        .inflate(  androidx.media3.ui.R.layout.exo_list_divider,
                            this,
                            false))
                }
                val trackViewLayoutId =
                    if (enableMultipleChoiceForAdaptiveSelections || enableMultipleChoiceForMultipleOverrides)
                        R.layout.item_simple_checkbox_new
                    else R.layout.item_simple_radiobutton_new
                val trackView = inflater.inflate(trackViewLayoutId, this, false) as CheckedTextView
                trackView.setBackgroundResource(selectableItemBackgroundResourceId)
                trackView.text = trackNameProvider.getTrackName(group.getFormat(trackIndex))
                    .split(",").toTypedArray()[0]
                if (mappedTrackInfo!!.getTrackSupport(rendererIndex, groupIndex, trackIndex)
                    == C.FORMAT_HANDLED
                ) {
                    trackView.isFocusable = true
                    trackView.tag = Pair.create(groupIndex, trackIndex)
                    trackView.setOnClickListener(componentListener)
                } else {
                    trackView.isFocusable = false
                    trackView.isEnabled = false
                }
                trackViews[groupIndex]?.set(trackIndex, trackView)
                addView(trackView)
            }
        }
        updateViewStates()
    }

    private fun updateViewStates() {
        disableView.isChecked = isDisabled
        defaultView.isChecked = !isDisabled && overrides.size() == 0
        for (i in trackViews.indices) {
            val override = overrides[i]
            for (j in 0 until trackViews[i]!!.size) {
                trackViews[i]?.get(j)?.isChecked =
                    override != null && override.trackIndices.contains(j)
            }
        }
    }

    private fun onClick(view: View) {
        if (view === disableView) {
            onDisableViewClicked()
        } else if (view === defaultView) {
            onDefaultViewClicked()
        } else {
            onTrackViewClicked(view)
        }
        updateViewStates()
        if (listener != null) {
            listener!!.onTrackSelectionChanged(isDisabled, getOverrides())
        }
    }

    private fun onDisableViewClicked() {
        isDisabled = true
        overrides.clear()
    }

    private fun onDefaultViewClicked() {
        isDisabled = false
        overrides.clear()
    }

    @Suppress("UNCHECKED_CAST")
    private fun onTrackViewClicked(view: View) {
        isDisabled = false
        val tag = view.tag as Pair<Int, Int>
        val groupIndex = tag.first
        val trackIndex = tag.second
        val override = overrides[groupIndex]
        Assertions.checkNotNull(mappedTrackInfo)
        if (override == null) {
            // Start new override.
            if (!allowMultipleOverrides && overrides.size() > 0) {
                // Removed other overrides if we don't allow multiple overrides.
                overrides.clear()
            }
            overrides.put(groupIndex, TrackSelectionOverride(trackGroups[0], trackIndex))
        } else {
            // An existing override is being modified.
            val overrideLength = override.mediaTrackGroup.length
            //int[] overrideTracks = override.trackIndices;
            val isCurrentlySelected = (view as CheckedTextView).isChecked
            val isAdaptiveAllowed = shouldEnableAdaptiveSelection(groupIndex)
            val isUsingCheckBox = isAdaptiveAllowed || shouldEnableMultiGroupSelection()
            if (isCurrentlySelected && isUsingCheckBox) {
                // Remove the track from the override.
                if (overrideLength == 1) {
                    // The last track is being removed, so the override becomes empty.
                    overrides.remove(groupIndex)
                } else {
                    //int[] tracks = getTracksRemoving(overrideTracks, trackIndex);
                    overrides.put(groupIndex,
                        TrackSelectionOverride(override.mediaTrackGroup, trackIndex))
                }
            } else if (!isCurrentlySelected) {
                if (isAdaptiveAllowed) {
                    // Add new track to adaptive override.
                    //int[] tracks = getTracksAdding(overrideTracks, trackIndex);
                    overrides.put(groupIndex,
                        TrackSelectionOverride(override.mediaTrackGroup, trackIndex))
                } else {
                    // Replace existing track in override.
                    overrides
                        .put(groupIndex, TrackSelectionOverride(trackGroups[0], trackIndex))
                }
            }
        }
    }

    private fun shouldEnableAdaptiveSelection(groupIndex: Int): Boolean {
        return (allowAdaptiveSelections
                && trackGroups[groupIndex].length > 1 && (mappedTrackInfo!!.getAdaptiveSupport(
            rendererIndex, groupIndex,  /* includeCapabilitiesExceededTracks= */false)
                != RendererCapabilities.ADAPTIVE_NOT_SUPPORTED))
    }

    private fun shouldEnableMultiGroupSelection(): Boolean {
        return allowMultipleOverrides && trackGroups.length > 1
    }

    // Internal classes.
    private inner class ComponentListener : OnClickListener {
        override fun onClick(view: View) {
            this@MyTrackSelectionView.onClick(view)
        }
    }

    companion object {
        private fun getTracksAdding(tracks: IntArray, addedTrack: Int): IntArray {
            var mTracks = tracks
            mTracks = mTracks.copyOf(mTracks.size + 1)
            mTracks[mTracks.size - 1] = addedTrack
            return mTracks
        }

        private fun getTracksRemoving(tracks: IntArray, removedTrack: Int): IntArray {
            val newTracks = IntArray(tracks.size - 1)
            var trackCount = 0
            for (track in tracks) {
                if (track != removedTrack) {
                    newTracks[trackCount++] = track
                }
            }
            return newTracks
        }
    }

    /**
     * Creates a track selection view.
     */

    init {
        orientation = VERTICAL
        overrides = SparseArray()

        // Don't save view hierarchy as it needs to be reinitialized with a call to init.
        isSaveFromParentEnabled = false
        val attributeArray = context
            .theme
            .obtainStyledAttributes(intArrayOf(android.R.attr.selectableItemBackground))
        selectableItemBackgroundResourceId = attributeArray.getResourceId(0, 0)
        attributeArray.recycle()
        inflater = LayoutInflater.from(context)
        componentListener = ComponentListener()
        trackNameProvider = DefaultTrackNameProvider(resources)
        trackGroups = TrackGroupArray.EMPTY

        // View for disabling the renderer.
        disableView =
            inflater.inflate(R.layout.item_simple_radiobutton_new, this, false) as CheckedTextView
        disableView.setBackgroundResource(selectableItemBackgroundResourceId)
        disableView.setText(R.string.none)
        disableView.isEnabled = false
        disableView.isFocusable = true
        disableView.setOnClickListener(componentListener)
        disableView.visibility = VISIBLE
        addView(disableView)
        // Divider view.
        addView(
            inflater.inflate(   androidx.media3.ui.R.layout.exo_list_divider,
                this,
                false))
        // View for clearing the override to allow the selector to use its default selection logic.
        defaultView =
            inflater.inflate(R.layout.item_simple_radiobutton_new, this, false) as CheckedTextView
        defaultView.setBackgroundResource(selectableItemBackgroundResourceId)
        defaultView.setText(R.string.automatic)
        defaultView.isEnabled = false
        defaultView.isFocusable = true
        defaultView.setOnClickListener(componentListener)
        addView(defaultView)
    }
}