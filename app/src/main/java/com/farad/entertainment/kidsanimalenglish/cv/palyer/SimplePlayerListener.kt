package com.farad.entertainment.kidsanimalenglish.cv.palyer

import androidx.media3.common.Player
import com.farad.entertainment.kidsanimalenglish.utils.Click2

@Suppress("MemberVisibilityCanBePrivate")
class SimplePlayerListener : Player.Listener {

    var onTimeChangeListener: Click2<Long, Long>? = null
    var player: Player? = null

    private var progressTracker = ProgressTracker {
        if (player != null)
            onTimeChangeListener?.invoke(
                player!!.getElapsedTimeMilli(),
                player!!.getDurationTimeMilli()
            )
    }

    fun updateProgress() {
        progressTracker.start = player?.isPlaying == true
    }

    fun updatePlayPauseButton() {}
    fun updateRepeatModeButton() {}
    fun updateShuffleButton() {}
    fun updateNavigation() {}
    fun updateTimeline() {}
    fun updatePlaybackSpeedList() {}
    fun updateTrackLists() {}

    override fun onEvents(player: Player, events: Player.Events) {
        if (events.containsAny(
                Player.EVENT_PLAYBACK_STATE_CHANGED,
                Player.EVENT_PLAY_WHEN_READY_CHANGED,
                Player.EVENT_AVAILABLE_COMMANDS_CHANGED
            )
        ) {
            updatePlayPauseButton()
        }
        if (events.containsAny(
                Player.EVENT_PLAYBACK_STATE_CHANGED,
                Player.EVENT_PLAY_WHEN_READY_CHANGED,
                Player.EVENT_IS_PLAYING_CHANGED,
                Player.EVENT_AVAILABLE_COMMANDS_CHANGED
            )
        ) {
            updateProgress()
        }
        if (events.containsAny(
                Player.EVENT_REPEAT_MODE_CHANGED,
                Player.EVENT_AVAILABLE_COMMANDS_CHANGED
            )
        ) {
            updateRepeatModeButton()
        }
        if (events.containsAny(
                Player.EVENT_SHUFFLE_MODE_ENABLED_CHANGED,
                Player.EVENT_AVAILABLE_COMMANDS_CHANGED
            )
        ) {
            updateShuffleButton()
        }
        if (events.containsAny(
                Player.EVENT_REPEAT_MODE_CHANGED,
                Player.EVENT_SHUFFLE_MODE_ENABLED_CHANGED,
                Player.EVENT_POSITION_DISCONTINUITY,
                Player.EVENT_TIMELINE_CHANGED,
                Player.EVENT_SEEK_BACK_INCREMENT_CHANGED,
                Player.EVENT_SEEK_FORWARD_INCREMENT_CHANGED,
                Player.EVENT_AVAILABLE_COMMANDS_CHANGED
            )
        ) {
            updateNavigation()
        }
        if (events.containsAny(
                Player.EVENT_POSITION_DISCONTINUITY,
                Player.EVENT_TIMELINE_CHANGED,
                Player.EVENT_AVAILABLE_COMMANDS_CHANGED
            )
        ) {
            updateTimeline()
        }
        if (events.containsAny(
                Player.EVENT_PLAYBACK_PARAMETERS_CHANGED,
                Player.EVENT_AVAILABLE_COMMANDS_CHANGED
            )
        ) {
            updatePlaybackSpeedList()
        }
        if (events.containsAny(
                Player.EVENT_TRACKS_CHANGED,
                Player.EVENT_AVAILABLE_COMMANDS_CHANGED
            )
        ) {
            updateTrackLists()
        }
    }

}

fun Player.getElapsedTimeMilli(): Long {
    var elapsedTimeMilli = currentPosition
    var durationAudioMilli = duration

    if (elapsedTimeMilli < 0)
        elapsedTimeMilli = 0

    if (durationAudioMilli < 0)
        durationAudioMilli = 0

    if (elapsedTimeMilli > durationAudioMilli)
        elapsedTimeMilli = durationAudioMilli
    return elapsedTimeMilli
}

fun Player.getDurationTimeMilli(): Long {
    var durationAudioMilli = duration
    if (durationAudioMilli < 0)
        durationAudioMilli = 0
    return durationAudioMilli
}