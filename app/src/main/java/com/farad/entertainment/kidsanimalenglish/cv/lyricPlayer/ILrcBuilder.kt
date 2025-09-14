/**
 * douzifly @Aug 10, 2013
 * github.com/douzifly
 * douzifly@gmail.com
 */
package com.farad.entertainment.kidsanimalenglish.cv.lyricPlayer

/**
 * @author douzifly
 */
interface ILrcBuilder {
    fun getLrcRows(rawLrc: String): List<LrcRow>
}
