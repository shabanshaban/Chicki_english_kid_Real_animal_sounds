package com.farad.entertainment.kidsanimalenglish.cv.canvasRain

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CanvasView : AppCompatImageView {
    /** handler to post UI thread actions  */
    private var handler: Handler? = null

    /** update drops physics thread  */
    private var physicThread: Job? = null

    /** scene renderer thread  */
    private var renderThread: Job? = null

    /** raining thread  */
    private var rainThread: Job? = null

    /** simple textview to show drops count that can inject using setter method  */
    private var informationHolder: TextView? = null

    /** paint to draw drops  */
    private var paint: Paint? = null

    /** last time physics updated  */
    private var lastPhysicUpdateTime: Long = 0

    /** rain speed percent ( from 0 to 1 )  */
    private var rainSpeedPercent = 1f

    /** total live drops on the scene  */
    private var dropsCount = 0

    /** detect custom view size initialized or not  */
    private var isSizeInitialized = false

    /** array2DAparatPic of drops  */
    private val drops = ArrayList<Drop>()

    /** constructor form 1  */
    constructor(context: Context) : super(context) {
        initialize()
    }

    /** constructor form 2  */
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize()
    }

    /** constructor form 3  */
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initialize()
    }

    /** initialize custom view that called from constructors  */
    private fun initialize() {

        // set paint attributes
        paint = Paint()
        paint?.color = Color.argb(255, 127, 127, 255)
        paint?.style = Paint.Style.STROKE
        paint?.strokeWidth = 2f
        paint?.isAntiAlias = true

        // define physic thread

        physicThread = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                try {
                    // compute elapsed time from last physic update
                    val now = System.currentTimeMillis()
                    val elapsed = now - lastPhysicUpdateTime

                    // sync dynamic array2DAparatPic to prevent concurrent modification
                    synchronized(drops) {

                        // iterate through dynamic array2DAparatPic to calculate drop size base on elapsed time
                        for (drop: Drop in drops) {
                            drop.size += (elapsed * 0.05 * drop.speed).toFloat()
                        }

                        // compute drops count
                        dropsCount = drops.size
                    }

                    // store last physic update time
                    lastPhysicUpdateTime = System.currentTimeMillis()

                    // do physic thread every 10ms
                    delay(10)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }

        }

        renderThread = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                try {
                    // invalidate current custom view from activity_draw thread
                    postInvalidate()

                    // command to handler to show drops count on information holder
                    handler?.post {
                        if (informationHolder != null) {
                            val text = "Drops Count: $dropsCount"
                            informationHolder?.text = text
                        }
                    }

                    // draw scene every 25ms
                    delay(25)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }


        rainThread = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                try {
                    // calculate random interval between rain drops
                    val randomDelay =
                        (Math.random() * 100 / rainSpeedPercent + 100 / rainSpeedPercent).toInt()

                    // generate random drops
                    randomDrops()

                    // sleep some time between random drop waves
                    delay(randomDelay.toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            while (!isSizeInitialized) {
                try {
                    // check is initialized every 100ms
                    delay(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }


        }


    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        isSizeInitialized = true
    }

    override fun onDraw(canvas: Canvas) {
        // do default super class render canvas
        super.onDraw(canvas)

        // safe drops read
        synchronized(drops) {
            for (i in drops.indices.reversed()) {
                val drop: Drop = drops[i]

                // calculate alpha of drop
                var alpha: Float = 255 - drop.size * 2.55f
                if (alpha < 0) {
                    // if alpha drop is 0, no need to exist in array2DAparatPic and must remove
                    drops.removeAt(i)
                    alpha = 0f
                }

                // safe value for alpha
                if (alpha > 255) {
                    alpha = 255f
                }

                // set paint alpha on the fly
                paint?.alpha = alpha.toInt()

                // draw drop
                canvas.drawCircle(drop.px, drop.py, drop.size, (paint)!!)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // read touch position
        val x = event.x
        val y = event.y

        // instantiate new drop and set attributes
        val drop = Drop()
        drop.px = x
        drop.py = y
        drop.size = (Math.random() * 10).toFloat()
        drop.speed = (Math.random() * 2 + 1).toFloat()

        // safe add drop to dynamic array2DAparatPic
        synchronized(drops) { drops.add(drop) }

        // do default super class touch event
        return super.onTouchEvent(event)
    }

    /** generate random drops  */
    private fun randomDrops() {
        // calculate count of drops
        val randomCount = (Math.random() * 2 * rainSpeedPercent * MAX_RAIN_SPEED).toInt()

        // generate drops and add to dynamic array2DAparatPic
        for (i in 0 until randomCount) {
            val drop = Drop()
            drop.px = (Math.random() * width).toFloat()
            drop.py = (Math.random() * height).toFloat()
            drop.size = (Math.random() * 10).toFloat()
            drop.speed = (Math.random() * 2 + 1).toFloat()
            synchronized(drops) { drops.add(drop) }
        }
    }

    /** rain speed setter  */
    fun setRainSpeed(percent: Int) {
        var percent = percent
        if (percent < 1) {
            percent = 1
        }

        // convert it to float ( between 0 to 1 )
        rainSpeedPercent = percent.toFloat() / 100f

        // interrupt raining sleep to awake him and continue processing raining interval
    }

    /** information holder setter  */
    fun setInformationHolder(textview: TextView) {
        informationHolder = textview
    }

    companion object {
        /** define maximum speed of rain  */
        private val MAX_RAIN_SPEED = 20
    }
}
