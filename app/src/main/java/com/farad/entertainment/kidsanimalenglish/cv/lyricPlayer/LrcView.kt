package com.farad.entertainment.kidsanimalenglish.cv.lyricPlayer

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.farad.entertainment.kidsanimalenglish.R
import com.farad.entertainment.kidsanimalenglish.utils.getScreenWidth
import com.farad.entertainment.kidsanimalenglish.utils.px
import kotlin.math.abs

/**
 * LrcView can display LRC file and Seek it.
 * @author douzifly
 */
class LrcView(context: Context?, attr: AttributeSet?) : View(context, attr), ILrcView {
    private var mLrcRows: List<LrcRow> = arrayListOf() // all lrc rows of one lrc file
    private val mMinSeekFiredOffset = 10 // min offset for fire seek action, px;
    private var mHignlightRow = 0 // current singing row , should be highlighted.
    private val mHignlightRowColor = Color.RED
    private val mNormalRowColor = Color.BLACK
    private val mSeekLineColor = Color.TRANSPARENT
    private val mSeekLineTextColor = Color.TRANSPARENT
    private var mSeekLineTextSize = 5
    private val mMinSeekLineTextSize = 3
    private val mMaxSeekLineTextSize = 8
    private var mLrcFontSize = 50 // font size of lrc  فاصله ی خط ها
    private val mMinLrcFontSize = 15
    private val mMaxLrcFontSize = 35
    private val mPaddingY = 50 // padding of each row
    private val mSeekLinePaddingX = 0 // Seek line padding x
    private var mDisplayMode = DISPLAY_MODE_NORMAL
    private var mLrcViewListener: ILrcView.LrcViewListener? = null
    private var screenWidth: Int = 0

    private val testSizeSina=(16.px).toFloat()

    //	private String mLoadingLrcTip = "Downloading lrc...";
    private var mLoadingLrcTip: String? = ""
    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun setListener(l: ILrcView.LrcViewListener) {
        mLrcViewListener = l
    }

    override val currentLrc: String
        get() = mLrcRows[mHignlightRow].content

    fun setLoadingTipText(text: String?) {
        mLoadingLrcTip = text
    }

    override fun onDraw(canvas: Canvas) {
        try {
            val height = height // height of this view
            val width = width // width of this view
            if (mLrcRows.isEmpty()) {
                if (mLoadingLrcTip != null) {
                    // draw tip when no lrc.
                    mPaint.color = mHignlightRowColor
                    mPaint.textSize = testSizeSina
                    mPaint.textAlign = Paint.Align.CENTER
                    canvas.drawText(
                        mLoadingLrcTip!!,
                        (width / 2).toFloat(),
                        (height / 2 - mLrcFontSize).toFloat(),
                        mPaint
                    )
                }
                return
            }
            var rowY: Int  // vertical point of each row.
            val rowX = width / 2

            // 1, draw highlight row at center.
            // 2, draw rows above highlight row.
            // 3, draw rows below highlight row.

            // 1 highlight row
            val highlightText = mLrcRows[mHignlightRow].content
            val highlightRowY = height / 2 - mLrcFontSize
            mPaint.color = mHignlightRowColor
            mPaint.textSize = testSizeSina
            mPaint.textAlign = Paint.Align.CENTER
            canvas.drawText(highlightText, rowX.toFloat(), highlightRowY.toFloat(), mPaint)
            if (mDisplayMode == DISPLAY_MODE_SEEK) {
                // draw Seek line and current time when moving.
                mPaint.color = mSeekLineColor
                canvas.drawLine(
                    mSeekLinePaddingX.toFloat(),
                    highlightRowY.toFloat(),
                    (width - mSeekLinePaddingX).toFloat(),
                    highlightRowY.toFloat(),
                    mPaint
                )
                mPaint.color = mSeekLineTextColor
                mPaint.textSize = testSizeSina
                mPaint.textAlign = Paint.Align.LEFT
                canvas.drawText(
                    mLrcRows[mHignlightRow].strTime,
                    0f,
                    highlightRowY.toFloat(),
                    mPaint
                )
            }

            // 2 above rows
            mPaint.color = mNormalRowColor
            mPaint.textSize = testSizeSina
            mPaint.textAlign = Paint.Align.CENTER
            var rowNum: Int = mHignlightRow - 1
            rowY = highlightRowY - mPaddingY - mLrcFontSize
            while (rowY > -mLrcFontSize && rowNum >= 0) {
                val text = mLrcRows[rowNum].content
                canvas.drawText(text, rowX.toFloat(), rowY.toFloat(), mPaint)
                rowY -= mPaddingY + mLrcFontSize
                rowNum--
            }

            // 3 below rows
            rowNum = mHignlightRow + 1
            rowY = highlightRowY + mPaddingY + mLrcFontSize
            while (rowY < height && rowNum < mLrcRows.size) {
                val text = mLrcRows[rowNum].content
                canvas.drawText(text, rowX.toFloat(), rowY.toFloat(), mPaint)
                rowY += mPaddingY + mLrcFontSize
                rowNum++
            }
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    private fun seekLrc(position: Int, cb: Boolean) {
        if (position < 0 || position > mLrcRows.size) {
            return
        }
        val lrcRow = mLrcRows[position]
        mHignlightRow = position
        invalidate()
        if (mLrcViewListener != null && cb) {
            mLrcViewListener?.onLrcSeeked(position, lrcRow)
        }
    }

    private var mLastMotionY = 0f
    private val mPointerOneLastMotion: PointF = PointF()
    private val mPointerTwoLastMotion: PointF = PointF()
    private var mIsFirstMove =
        false // whether is first move , some events can't not detected in touch down,

    init {

        context?.let { contextNotNull ->
            screenWidth = contextNotNull.getScreenWidth()  // deprecated
            mPaint.textSize = testSizeSina
            val font = ResourcesCompat.getFont(contextNotNull, R.font.gothic_b)
            mPaint.setTypeface(font)
        }

    }

    // such as two pointer touch, so it's good place to detect it in first move
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (mLrcRows.isEmpty()) {
            return super.onTouchEvent(event)
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(TAG, "down,mLastMotionY:$mLastMotionY")
                mLastMotionY = event.getY()
                mIsFirstMove = true
                invalidate()
            }

            MotionEvent.ACTION_MOVE -> {
                if (event.getPointerCount() == 2) {
                    Log.d(TAG, "two move")
                    doScale(event)
                    return true
                }
                Log.d(TAG, "one move")
                // single pointer mode ,seek
                if (mDisplayMode == DISPLAY_MODE_SCALE) {
                    //if scaling but pointer become not two ,do nothing.
                    return true
                }
                doSeek(event)
            }

            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                if (mDisplayMode == DISPLAY_MODE_SEEK) {
                    seekLrc(mHignlightRow, true)
                }
                mDisplayMode = DISPLAY_MODE_NORMAL
                invalidate()
            }
        }
        return true
    }

    private fun doScale(event: MotionEvent) {
        if (mDisplayMode == DISPLAY_MODE_SEEK) {
            // if Seeking but pointer become two, become to scale mode
            mDisplayMode = DISPLAY_MODE_SCALE
            Log.d(TAG, "two move but teaking ...change mode")
            return
        }
        // two pointer mode , scale font
        if (mIsFirstMove) {
            mDisplayMode = DISPLAY_MODE_SCALE
            invalidate()
            mIsFirstMove = false
            setTwoPointerLocation(event)
        }
        val scaleSize = getScale(event)
        Log.d(TAG, "scaleSize:$scaleSize")
        if (scaleSize != 0) {
            setNewFontSize(scaleSize)
            invalidate()
        }
        setTwoPointerLocation(event)
    }

    private fun doSeek(event: MotionEvent) {
        val y: Float = event.getY()
        val offsetY = y - mLastMotionY // touch offset.
        if (Math.abs(offsetY) < mMinSeekFiredOffset) {
            // move to short ,do not fire seek action
            return
        }
        mDisplayMode = DISPLAY_MODE_SEEK
        val rowOffset = Math.abs(offsetY.toInt() / mLrcFontSize) // highlight row offset.
        Log.d(TAG, "move new hightlightrow : $mHignlightRow offsetY: $offsetY rowOffset:$rowOffset")
        if (offsetY < 0) {
            // finger move up
            mHignlightRow += rowOffset
        } else if (offsetY > 0) {
            // finger move down
            mHignlightRow -= rowOffset
        }
        mHignlightRow = 0.coerceAtLeast(mHignlightRow)
        mHignlightRow = mHignlightRow.coerceAtMost(mLrcRows.size - 1)
        if (rowOffset > 0) {
            mLastMotionY = y
            invalidate()
        }
    }

    private fun setTwoPointerLocation(event: MotionEvent) {
        // mPointerOneLastMotion.x = event.getX(0)
        // mPointerOneLastMotion.y = event.getY(0)
        // mPointerTwoLastMotion.x = event.getX(1)
        // mPointerTwoLastMotion.y = event.getY(1)
    }

    private fun setNewFontSize(scaleSize: Int) {
        mLrcFontSize += scaleSize
        mSeekLineTextSize += scaleSize
        mLrcFontSize = Math.max(mLrcFontSize, mMinLrcFontSize)
        mLrcFontSize = Math.min(mLrcFontSize, mMaxLrcFontSize)
        mSeekLineTextSize = Math.max(mSeekLineTextSize, mMinSeekLineTextSize)
        mSeekLineTextSize = Math.min(mSeekLineTextSize, mMaxSeekLineTextSize)
    }

    // get font scale offset
    private fun getScale(event: MotionEvent): Int {
        Log.d(TAG, "scaleSize getScale")
        val x0: Float = event.getX(0)
        val y0: Float = event.getY(0)
        val x1: Float = event.getX(1)
        val y1: Float = event.getY(1)
        val maxOffset: Float  // max offset between x or y axis,used to decide scale size
        val zoomin: Boolean
        val oldXOffset: Float = abs(mPointerOneLastMotion.x - mPointerTwoLastMotion.x)
        val newXoffset = abs(x1 - x0)
        val oldYOffset: Float = abs(mPointerOneLastMotion.y - mPointerTwoLastMotion.y)
        val newYoffset = abs(y1 - y0)
        maxOffset =
            Math.abs(newXoffset - oldXOffset).coerceAtLeast(abs(newYoffset - oldYOffset))
        zoomin = if (maxOffset == abs(x = newXoffset - oldXOffset)) {
            newXoffset > oldXOffset
        } else {
            newYoffset > oldYOffset
        }
        Log.d(TAG, "scaleSize maxOffset:$maxOffset")
        return if (zoomin) (maxOffset / 10).toInt() else -(maxOffset / 10).toInt()
    }

    override fun setLrc(lrcRows: List<LrcRow>) {
        mLrcRows = lrcRows
        invalidate()
    }


    override fun seekLrcToTime(time: Long) {
        if (mLrcRows.isEmpty()) {
            return
        }
        if (mDisplayMode != DISPLAY_MODE_NORMAL) {
            // touching
            return
        }
        // find row

        mLrcRows.let {
            for (i in mLrcRows.indices) {
                val current = mLrcRows[i]
                val next = if (i + 1 == mLrcRows.size) null else mLrcRows[i + 1]
                if (time >= current.time && next != null && time < next.time || time > current.time && next == null) {
                    seekLrc(i, false)
                    return
                }
            }
        }


    }


    companion object {
        const val TAG = "LrcView"

        /** normal display mode */
        const val DISPLAY_MODE_NORMAL = 0

        /** seek display mode  */
        const val DISPLAY_MODE_SEEK = 1

        /** scale display mode ,scale font size */
        const val DISPLAY_MODE_SCALE = 2
    }
}
