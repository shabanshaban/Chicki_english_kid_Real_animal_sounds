package com.farad.entertainment.kidsanimalenglish.cv.palyer

import android.content.Context
import android.util.AttributeSet
import androidx.media3.common.Player
import androidx.media3.ui.PlayerView
import com.farad.entertainment.kidsanimalenglish.utils.Click2


class SimplePlayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : PlayerView(context, attrs) {


    private val simplePlayerListener = SimplePlayerListener()
    var onTimeChangeListener: Click2<Long, Long>? = null

    init {
        simplePlayerListener.onTimeChangeListener = { elapsed, duration ->
            onTimeChangeListener?.invoke(elapsed, duration)
        }
    }

    override fun setPlayer(player: Player?) {
        simplePlayerListener.player = player
        getPlayer()?.removeListener(simplePlayerListener)
        super.setPlayer(player)
        player?.addListener(simplePlayerListener)
    }

  /*  override fun onDetachedFromWindow() {
        player?.removeListener(simplePlayerListener)
        super.onDetachedFromWindow()
    }*/
}