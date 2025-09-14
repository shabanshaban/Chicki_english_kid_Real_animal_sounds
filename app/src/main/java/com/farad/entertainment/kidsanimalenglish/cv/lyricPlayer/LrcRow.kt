/**
 * douzifly @Aug 10, 2013
 * github.com/douzifly
 * douzifly@gmail.com
 */
package com.farad.entertainment.kidsanimalenglish.cv.lyricPlayer

import android.util.Log

/**
 *
 * describe the lyric line
 * @author douzifly
 */
class LrcRow : Comparable<LrcRow> {
    /** begin time of this lrc row  */
    @JvmField
    var time: Long = 0

    /** content of this lrc  */
    @JvmField
    var content: String = ""

    @JvmField
    var strTime: String = ""

    constructor()
    constructor(strTime: String, time: Long, content: String) {
        this.strTime = strTime
        this.time = time
        this.content = content
        Log.d(TAG, "strTime:$strTime time:$time content:$content")
    }

    override fun compareTo(other: LrcRow): Int {
        return (time - other.time).toInt()
    }

    companion object {
        const val TAG = "LrcRow"

        /**
         * create LrcRows by standard Lrc Line , if not standard lrc line,
         * return false<br></br>
         * [00:00:20] balabalabalabala
         */
        @JvmStatic
        fun createRows(standardLrcLine: String): List<LrcRow>? {
            return try {
                if (standardLrcLine.indexOf("[") != 0 || standardLrcLine.indexOf("]") != 9) {
                    return null
                }
                val lastIndexOfRightBracket = standardLrcLine.lastIndexOf("]")
                val content =
                    standardLrcLine.substring(lastIndexOfRightBracket + 1, standardLrcLine.length)

                // times [mm:ss.SS][mm:ss.SS] -> *mm:ss.SS**mm:ss.SS*
                val times =
                    standardLrcLine.substring(0, lastIndexOfRightBracket + 1).replace("[", "-")
                        .replace("]", "-")
                val arrTimes =
                    times.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val listTimes: MutableList<LrcRow> = ArrayList()
                for (temp in arrTimes) {
                    if (temp.trim { it <= ' ' }.isEmpty()) {
                        continue
                    }
                    val lrcRow = LrcRow(temp, timeConvert(temp), content)
                    listTimes.add(lrcRow)
                }
                listTimes
            } catch (e: Exception) {
                Log.e(TAG, "createRows exception:" + e.message)
                null
            }
        }

        private fun timeConvert(timeString: String): Long {
            val timeStringNew = timeString.replace('.', ':')
            val times =
                timeStringNew.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            // mm:ss:SS
            return (Integer.valueOf(times[0]) * 60 * 1000 + Integer.valueOf(times[1]) * 1000 +
                    Integer.valueOf(times[2])).toLong()
        }
    }
}