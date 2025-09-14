package com.farad.entertainment.kidsanimalenglish.utils

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.imageview.ShapeableImageView

class ShimmerImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ShapeableImageView(context, attrs, defStyleAttr) {

    private var shimmerPaint: Paint = Paint()
    private var shimmerWidth = 0f
    private var shimmerAnimator: ObjectAnimator? = null
    private var gradient: LinearGradient? = null

    init {
        shimmerWidth = 0.2f * width
        setupShimmer()
    }

    private fun setupShimmer() {
        gradient = LinearGradient(
            -shimmerWidth, 0f, shimmerWidth, 0f,
            intArrayOf(0x00FFFFFF, 0xFFFFFFFF.toInt(), 0x00FFFFFF),
            floatArrayOf(0f, 0.5f, 1f),
            Shader.TileMode.CLAMP
        )
        shimmerPaint.shader = gradient
        shimmerPaint.isAntiAlias = true

        shimmerAnimator = ObjectAnimator.ofFloat(this, "shimmerShift", -shimmerWidth, width.toFloat())
        shimmerAnimator?.duration = 2500
        shimmerAnimator?.repeatCount = ObjectAnimator.INFINITE
        shimmerAnimator?.repeatMode = ObjectAnimator.REVERSE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (shimmerAnimator?.isStarted == true) {
            val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
            canvas.drawRect(rect, shimmerPaint)
        }
    }

    fun startShimmer() {
        shimmerAnimator?.start()
    }

    fun stopShimmer() {
        shimmerAnimator?.cancel()
    }
}
