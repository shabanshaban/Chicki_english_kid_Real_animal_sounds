package com.farad.entertainment.kidsanimalenglish.cv.colorImageView.photoview

import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Stack
import kotlin.math.sqrt

class ColourImageView(context: Context?, attrs: AttributeSet?) : ImageView(context, attrs) {
    fun clearPoints() {
        undopoints!!.clear()
        redopoints!!.clear()
    }

    enum class Model {
        FILLCOLOR,
        FILLGRADUALCOLOR,
        PICKCOLOR,
        DRAW_LINE
    }

    private var mBitmap: Bitmap? = null

    private val mBorderColor = -1
    private val mStacks = Stack<Point>()
    private var mColor = -0xff432c
    private var stacksize = 10
    private var bmstackundo: Stack<Bitmap?>? = null
    private var bmstackredo: Stack<Bitmap?>? = null
    private var undopoints: Stack<Point>? = null
    private var redopoints: Stack<Point>? = null
    var onRedoUndoListener: OnRedoUndoListener? = null
    private var loaderTask: Job? = null
    @JvmField
    var model = Model.FILLCOLOR
    private var onColorPickListener: OnColorPickListener? = null
    private var onDrawLineListener: OnDrawLineListener? = null

    init {
        initStack()


    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }



    fun createBitMap(bt: Bitmap) {
        mBitmap = bt.config?.let { bt.copy(it, true) }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x.toInt()
        val y = event.y.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {}
        }
        return super.onTouchEvent(event)
    }

    fun pickColor(x: Int, y: Int) {
        var color = 0
        var status: Boolean
        try {
            if (!isBorderColor(mBitmap!!.getPixel(x, y)) && mBitmap!!.getPixel(
                    x,
                    y
                ) != Color.TRANSPARENT
            ) {
                color = mBitmap!!.getPixel(x, y)
                status = true
            } else {
                status = false
            }
        } catch (e: Exception) {
            status = false
        }
        if (onColorPickListener != null) {
            onColorPickListener!!.onColorPick(status, color)
        }
    }


    fun fillColorToSameArea(x: Int, y: Int) {
        try {

            if ((mBitmap!!.getPixel(x, y) != mColor) && !isBorderColor(
                    mBitmap!!.getPixel(
                        x,
                        y
                    )
                ) && (mBitmap!!.getPixel(x, y) != Color.TRANSPARENT)
            ) {
                ProgressLoading.show(
                    context, true
                )
                ProgressLoading.setOndismissListener(
                    DialogInterface.OnDismissListener {
                            loaderTask?.cancel()
                    })
                loaderTask=  CoroutineScope(Dispatchers.IO).launch {
                    val bm = mBitmap
                    try {
                        bm?.config?.let {
                            bm.copy(it, true)?.let { bitmap->
                                pushUndoStack(bitmap)
                            }
                        }

                        val pixel = bm?.getPixel(x, y)
                        val w = requireNotNull(bm?.width)
                        val h = requireNotNull(bm?.height)
                        val pixels = IntArray(w * h)
                        bm?.getPixels(pixels, 0, w, 0, 0, w, h)
                        fillColor(pixels, w, h, requireNotNull(pixel), mColor, x, y)
                        bm.setPixels(pixels, 0, w, 0, 0, w, h)
                        withContext(Dispatchers.Main) {
                            ProgressLoading.DismissDialog()
                            setImageDrawable(BitmapDrawable(resources, bm))
                            onRedoUndoListener?.onRedoUndo(requireNotNull(bmstackundo?.size), requireNotNull(bmstackredo?.size))
                        }
                    } catch (e: Exception) {
                        bmstackundo?.pop()
                    }
                }
            }
        } catch (e: Exception) {
          e.fillInStackTrace()
        }
    }

    private fun isBorderColor(color: Int): Boolean {
        return (Color.red(color) < 0x10) && (Color.green(color) < 0x10) && (Color.blue(
                color
            ) < 0x10)
    }

    private fun pushUndoStack(bm: Bitmap) {
        bmstackundo!!.push(bm)
        bmstackredo!!.clear()
    }


    private fun fillColor(
        pixels: IntArray,
        w: Int,
        h: Int,
        pixel: Int,
        newColor: Int,
        i: Int,
        j: Int
    ) {
        val orginalX = i
        val orginalY = j
        mStacks.clear()
        mStacks.push(Point(i, j))
        while (!mStacks.isEmpty()) {
            if (loaderTask!!.isCancelled) {
                break
            }
            val seed = mStacks.pop()
            //L.e("seed = " + seed.x + " , seed = " + seed.y);
            var count =
                fillLineLeft(pixels, pixel, w, h, newColor, seed.x, seed.y, orginalX, orginalY)
            val left = seed.x - count + 1
            count =
                fillLineRight(pixels, pixel, w, h, newColor, seed.x + 1, seed.y, orginalX, orginalY)
            val right = seed.x + count
            if (seed.y - 1 >= 0) findSeedInNewLine2(pixels, pixel, w, h, seed.y - 1, left, right)
            if (seed.y + 1 < h) findSeedInNewLine2(pixels, pixel, w, h, seed.y + 1, left, right)
        }
    }


    private fun findSeedInNewLine(
        pixels: IntArray,
        pixel: Int,
        w: Int,
        h: Int,
        i: Int,
        left: Int,
        right: Int
    ) {
        val begin = i * w + left
        var end = i * w + right
        var hasSeed = false
        var rx = -1
        var ry = -1
        ry = i
        while (end >= begin) {
            if (needFillPixel(pixels, pixel, end)) {
                if (!hasSeed) {
                    rx = end % w
                    mStacks.push(Point(rx, ry))
                    hasSeed = true
                }
            } else {
                hasSeed = false
            }
            end--
        }
    }

    private fun findSeedInNewLine2(
        pixels: IntArray,
        pixel: Int,
        w: Int,
        h: Int,
        i: Int,
        left: Int,
        right: Int
    ) {

        try {
            val begin = i * w + left
            var end = i * w + right
            var hasSeed = false
            val ry = i

            // Loop through the line from right to left
            while (end >= begin) {
                // Check if the current pixel needs filling
                if (needFillPixel(pixels, pixel, end)) {
                    if (!hasSeed) {
                        // Push the seed point onto the stack
                        val rx = end % w
                        mStacks.push(Point(rx, ry))
                        hasSeed = true
                    }
                } else {
                    // Reset seed status when a non-fillable pixel is found
                    hasSeed = false
                }
                end--
            }
        }catch (e:Exception){
            e.fillInStackTrace()
        }

    }



    private fun fillLineLeft(
        pixels: IntArray,
        pixel: Int,
        w: Int,
        h: Int,
        newColor: Int,
        x: Int,
        y: Int,
        orginalX: Int,
        orginalY: Int
    ): Int {
        var x = x
        var count = 0
        while (x >= 0) {
            //?????????
            val index = y * w + x
            if (needFillPixel(pixels, pixel, index)) {
                if (model == Model.FILLCOLOR) {
                    pixels[index] = newColor
                } else if (model == Model.FILLGRADUALCOLOR) {
                    val colorHSV = floatArrayOf(0f, 0f, 1f)
                    Color.colorToHSV(newColor, colorHSV)
                    val dis =
                        sqrt(((x - orginalX) * (x - orginalX) + (y - orginalY) * (y - orginalY)).toDouble())
                            .toFloat()
                    colorHSV[1] =
                        if ((colorHSV[1] - dis * 0.006) < 0.2) 0.2f else (colorHSV[1] - dis * 0.006f)
                    pixels[index] = Color.HSVToColor(colorHSV)
                }
                count++
                x--
            } else {
                break
            }
        }
        return count
    }

    private fun fillLineRight(
        pixels: IntArray,
        pixel: Int,
        w: Int,
        h: Int,
        newColor: Int,
        x: Int,
        y: Int,
        orginalX: Int,
        orginalY: Int
    ): Int {
        var x = x
        var count = 0
        while (x < w) {
            //???????
            val index = y * w + x
            if (needFillPixel(pixels, pixel, index)) {
                if (model == Model.FILLCOLOR) {
                    pixels[index] = newColor
                } else if (model == Model.FILLGRADUALCOLOR) {
                    val colorHSV = floatArrayOf(0f, 0f, 1f)
                    Color.colorToHSV(newColor, colorHSV)
                    val dis =
                        Math.sqrt(((x - orginalX) * (x - orginalX) + (y - orginalY) * (y - orginalY)).toDouble())
                            .toFloat()
                    colorHSV[1] =
                        if ((colorHSV[1] - dis * 0.006) < 0.2) 0.2f else (colorHSV[1] - dis * 0.006f)
                    pixels[index] = Color.HSVToColor(colorHSV)
                }
                count++
                x++
            } else {
                break
            }
        }
        return count
    }

    private fun needFillPixel(pixels: IntArray, pixel: Int, index: Int): Boolean {
        return if (model == Model.FILLGRADUALCOLOR) {
            pixels.get(index) == pixel
        } else pixels.get(index) == pixel
    }



    fun update() {
        setMeasuredDimension(
            measuredWidth,
            drawable.intrinsicHeight * measuredWidth / drawable.intrinsicWidth
        )
    }

    fun setColor(color: Int) {
        mColor = color
    }


    fun undo(): Boolean {
        try {
            if (bmstackundo?.peek() != null) {
                bmstackredo?.push(mBitmap?.config?.let { mBitmap?.copy(it, true) })
                mBitmap = bmstackundo?.pop()
                setImageDrawable(BitmapDrawable(resources, mBitmap))
                if (onRedoUndoListener != null) {
                    bmstackundo?.size?.let {unstackedSize->
                        bmstackredo?.size?.let {browserStackSize->

                            onRedoUndoListener?.onRedoUndo(unstackedSize, browserStackSize)
                        }
                    }
                }
                if (undopoints != null && undopoints?.empty()?.not() == true) {
                    redopoints?.push(undopoints?.pop())
                }
                return bmstackundo?.empty()?.not() == true
            }
        } catch (e: Exception) {
            e.fillInStackTrace()
        }
        return false
    }


    fun redo(): Boolean {
        try {
            if (bmstackredo?.peek() != null) {
                bmstackundo?.push(mBitmap?.config?.let { mBitmap?.copy(it, true) })
                mBitmap = bmstackredo?.pop()
                setImageDrawable(BitmapDrawable(resources, mBitmap))
                if (onRedoUndoListener != null) {
                    bmstackundo?.let {
                        bmstackredo?.let {bmstackredo->

                            onRedoUndoListener?.onRedoUndo(it.size, bmstackredo.size)
                        }
                    }
                }
                if (redopoints != null && redopoints?.empty()?.not() == true) {
                    undopoints?.push(redopoints?.pop())
                }
                return bmstackredo?.empty()?.not() == true
            }
        } catch (e: Exception) {
        }
        return false
    }

    //clear stack and the current image
    fun clearStack() {
        bmstackredo!!.clear()
        bmstackundo!!.clear()
        onRedoUndoListener!!.onRedoUndo(bmstackundo!!.size, bmstackredo!!.size)
        mBitmap = null
    }

    fun setOnColorPickListener(onColorPickListener: OnColorPickListener?) {
        this.onColorPickListener = onColorPickListener
    }

    interface OnRedoUndoListener {
        fun onRedoUndo(undoSize: Int, redoSize: Int)
    }

    fun getmBitmap(): Bitmap? {
        return mBitmap
    }

    private fun initStack() {
        val sharedPreferences = context.getSharedPreferences("Cache", Context.MODE_PRIVATE)
        stacksize = sharedPreferences.getInt("stack_max_size", 1000)
        bmstackundo =
            SizedStack(
                stacksize
            )
        bmstackredo =
            SizedStack(
                stacksize
            )
        undopoints = Stack()
        redopoints = Stack()
    }

    interface OnColorPickListener {
        fun onColorPick(status: Boolean, color: Int)
    }


    fun onRecycleBitmaps() {
        while (bmstackundo != null && !bmstackundo!!.empty()) {
            bmstackundo!!.pop()!!.recycle()
            bmstackundo!!.clear()
        }
        while (bmstackredo != null && !bmstackredo!!.empty()) {
            bmstackredo!!.pop()!!.recycle()
            bmstackredo!!.clear()
        }
        if (mBitmap != null) {
            mBitmap!!.recycle()
        }
    }

    fun drawLine(x: Int, y: Int) {
        if (undopoints != null && !undopoints!!.empty()) {
            drawBlackLine(undopoints!!.peek().x, undopoints!!.peek().y, x, y)
            undopoints!!.push(Point(x, y))
            if (onDrawLineListener != null) onDrawLineListener!!.OnGivenNextPointListener(x, y)
        } else {
            undopoints!!.push(Point(x, y))
            if (onDrawLineListener != null) onDrawLineListener!!.OnGivenFirstPointListener(x, y)
        }
    }

    private fun drawBlackLine(startX: Int, startY: Int, endX: Int, endY: Int) {
        var startX = startX
        var startY = startY
        var endX = endX
        var endY = endY
        try {
            Log.e("draw", "$startX,$startY,$endX,$endY")
            val bm = mBitmap
            //format points
            startX = if (startX >= bm!!.width) bm.width - 1 else startX
            startX = if (startX < 0) 0 else startX
            startY = if (startY >= bm.height) bm.height - 1 else startY
            startY = if (startY < 0) 0 else startY
            endX = if (endX >= bm.width) bm.width - 1 else endX
            endX = if (endX < 0) 0 else endX
            endY = if (endY >= bm.height) bm.height - 1 else endY
            endY = if (endY < 0) 0 else endY
            //test points
            bm.getPixel(endX, endY)
            bm.getPixel(startX, startY)

            bm.config?.let {config->
                pushUndoStack(bm.copy(config, true))
            }

            doingDrawLine(bm, startX, startY, endX, endY)
            setImageDrawable(BitmapDrawable(resources, bm))
            if (onRedoUndoListener != null) {
                onRedoUndoListener!!.onRedoUndo(bmstackundo!!.size, bmstackredo!!.size)
            }
            if (onDrawLineListener != null) onDrawLineListener!!.OnDrawFinishedListener(
                true,
                startX,
                startY,
                endX,
                endY
            )
        } catch (e: Exception) {
            Log.e("drawline", e.toString())
            bmstackundo!!.pop()
            if (onDrawLineListener != null) onDrawLineListener!!.OnDrawFinishedListener(
                false,
                startX,
                startY,
                endX,
                endY
            )
        }
    }

    private fun doingDrawLine(bm: Bitmap?, startX: Int, startY: Int, endX: Int, endY: Int) {
        val canvas = Canvas((bm)!!)
        val paint = Paint()
        paint.color = -0x1000000
        paint.strokeWidth = 2f
        canvas.drawLine(startX.toFloat(), startY.toFloat(), endX.toFloat(), endY.toFloat(), paint)
    }

    private fun doingDrawLineAsyn(bm: Bitmap, startX: Int, startY: Int, endX: Int, endY: Int) {
        //if two point same reture
        if (startX == endX && startY == endY) {
            bm.setPixel(startX, startY, -0x1000000)
            return
        }
        //if shuxian
        if (startX == endX) {
            if (endY > startY) {
                for (i in startY until endY) {
                    bm.setPixel(startX, i, -0x1000000)
                }
            } else {
                for (i in endY until startY) {
                    bm.setPixel(startX, i, -0x1000000)
                }
            }
            return
        }
        //if henxian
        if (startY == endY) {
            if (endX > startX) {
                for (i in startX until endX) {
                    bm.setPixel(i, startY, -0x1000000)
                }
            } else {
                for (i in endX until startX) {
                    bm.setPixel(i, startY, -0x1000000)
                }
            }
            return
        }
        //if xiexian
        if (Math.abs(endY - startY) > Math.abs(endX - startX)) {
            val radio = Math.abs((endY - startY).toFloat() / (endX - startX))
            var offset = 0
            val bushu = if (radio % 1 == 0f) 0 else (1 / (radio % 1)).toInt()
            var tempY: Int
            if (endY > startY && endX > startX) {
                tempY = startY
                for (i in startX until endX) {
                    var j = 0
                    while (j <= radio) {
                        bm.setPixel(i, tempY + j, -0x1000000)
                        j++
                    }
                    tempY += radio.toInt()
                    if (bushu != 0) {
                        if (offset == bushu) {
                            bm.setPixel(i, tempY++, -0x1000000)
                            offset = 0
                        } else {
                            offset++
                        }
                    }
                }
            } else if (endY < startY && endX > startX) {
                tempY = startY
                for (i in startX until endX) {
                    var j = 0
                    while (j <= radio) {
                        bm.setPixel(i, tempY - j, -0x1000000)
                        j++
                    }
                    tempY -= radio.toInt()
                    if (bushu != 0) {
                        if (offset == bushu) {
                            bm.setPixel(i, tempY--, -0x1000000)
                            offset = 0
                        } else {
                            offset++
                        }
                    }
                }
            } else if (endY > startY && endX < startX) {
                tempY = endY
                for (i in endX until startX) {
                    var j = 0
                    while (j <= radio) {
                        bm.setPixel(i, tempY - j, -0x1000000)
                        j++
                    }
                    tempY -= radio.toInt()
                    if (bushu != 0) {
                        if (offset == bushu) {
                            bm.setPixel(i, tempY--, -0x1000000)
                            offset = 0
                        } else {
                            offset++
                        }
                    }
                }
            } else if (endY < startY && endX < startX) {
                tempY = endY
                for (i in endX until startX) {
                    var j = 0
                    while (j <= radio) {
                        bm.setPixel(i, tempY + j, -0x1000000)
                        j++
                    }
                    tempY += radio.toInt()
                    if (bushu != 0) {
                        if (offset == bushu) {
                            bm.setPixel(i, tempY++, -0x1000000)
                            offset = 0
                        } else {
                            offset++
                        }
                    }
                }
            }
        } else {
            val radio = Math.abs((endX - startX).toFloat() / (endY - startY))
            var offset = 0
            val bushu = if (radio % 1 == 0f) 0 else (1 / (radio % 1)).toInt()
            var tempX: Int
            if (endY > startY && endX > startX) {
                tempX = startX //select small one
                for (i in startY until endY) { //loop start at small one end at large one
                    var j = 0
                    while (j <= radio) {
                        bm.setPixel(tempX + j, i, -0x1000000)
                        j++
                    }
                    tempX += radio.toInt()
                    if (bushu != 0) {
                        if (offset == bushu) {
                            bm.setPixel(++tempX, i, -0x1000000)
                            offset = 0
                        } else {
                            offset++
                        }
                    }
                }
            } else if (endY < startY && endX > startX) {
                tempX = endX
                for (i in endY until startY) {
                    var j = 0
                    while (j <= radio) {
                        bm.setPixel(tempX - j, i, -0x1000000)
                        j++
                    }
                    tempX -= radio.toInt()
                    if (bushu != 0) {
                        if (offset == bushu) {
                            bm.setPixel(--tempX, i, -0x1000000)
                            offset = 0
                        } else {
                            offset++
                        }
                    }
                }
            } else if (endY > startY && endX < startX) {
                tempX = startX
                for (i in startY until endY) {
                    var j = 0
                    while (j <= radio) {
                        bm.setPixel(tempX - j, i, -0x1000000)
                        j++
                    }
                    tempX -= radio.toInt()
                    if (bushu != 0) {
                        if (offset == bushu) {
                            bm.setPixel(--tempX, i, -0x1000000)
                            offset = 0
                        } else {
                            offset++
                        }
                    }
                }
            } else if (endY < startY && endX < startX) {
                tempX = endX
                for (i in endY until startY) {
                    var j = 0
                    while (j < radio) {
                        bm.setPixel(tempX + j, i, -0x1000000)
                        j++
                    }
                    tempX += radio.toInt()
                    if (bushu != 0) {
                        if (offset == bushu) {
                            bm.setPixel(++tempX, i, -0x1000000)
                            offset = 0
                        } else {
                            offset++
                        }
                    }
                }
            }
        }
    }

    fun setOnDrawLineListener(onDrawLineListener: OnDrawLineListener?) {
        this.onDrawLineListener = onDrawLineListener
    }
}