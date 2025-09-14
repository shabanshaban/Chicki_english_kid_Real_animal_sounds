/**
 * douzifly @Aug 10, 2013
 * github.com/douzifly
 * douzifly@gmail.com
 */
package com.farad.entertainment.kidsanimalenglish.cv.lyricPlayer

import android.util.Log
import com.farad.entertainment.kidsanimalenglish.cv.lyricPlayer.LrcRow.Companion.createRows
import java.io.BufferedReader
import java.io.IOException
import java.io.StringReader

/** default lrc builder,convert raw lrc string to lrc rows  */
class DefaultLrcBuilder : ILrcBuilder {
    override fun getLrcRows(rawLrc: String): List<LrcRow> {
        Log.d(TAG, "getLrcRows by rawString")

        val reader = StringReader(rawLrc)
        val br = BufferedReader(reader)
        var line: String?
        val rows: MutableList<LrcRow> = ArrayList()
        try {
            do {
                line = br.readLine()
                if (!line.isNullOrEmpty()) {
                    val lrcRows = createRows(line)
                    if (!lrcRows.isNullOrEmpty()) {
                        for (row in lrcRows) {
                            rows.add(row)
                        }
                    }
                }
            } while (line != null)
            if (rows.size > 0) {
                // sort by time:
                rows.sortBy { it.time }
               // Collections.sort(rows)
            }
        } catch (e: Exception) {
            Log.e(TAG, "parse exceptioned:" + e.message)
            return arrayListOf()
        } finally {
            try {
                br.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            reader.close()
        }
        return rows
    }

    companion object {
        const val TAG = "DefaultLrcBuilder"
    }
}
