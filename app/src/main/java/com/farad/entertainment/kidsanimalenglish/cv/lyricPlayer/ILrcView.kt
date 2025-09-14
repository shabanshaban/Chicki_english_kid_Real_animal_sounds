/**
 * douzifly @Aug 10, 2013
 * github.com/douzifly
 * douzifly@gmail.com
 */
package com.farad.entertainment.kidsanimalenglish.cv.lyricPlayer

/**
 * use ILrcView to display lyric, seek and scale.
 * @author douzifly
 */
interface ILrcView {
    /**
     * set the lyric rows to display
     */
    fun setLrc(lrcRows: List<LrcRow>)

    /**
     * seek lyric row to special time
     * @time time to be seek
     */
    fun seekLrcToTime(time: Long)
    fun setListener(l: LrcViewListener)
    val currentLrc: String

    interface LrcViewListener {
        /**
         * when lyric line was seeked by user
         */
        fun onLrcSeeked(newPosition: Int, row: LrcRow)
    }
}
