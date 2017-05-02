package com.classting.arcgraph

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Created by BN on 2017. 4. 27..
 */
class PointerView: View {

    private var pointerStrokePaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 8f
        color = Color.rgb(3, 169, 244)
        isAntiAlias = true
    }

    private var pointerFillPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.WHITE
        isAntiAlias = true
    }

    var radius = 20f
    var color = Color.rgb(3, 169, 244)

    constructor(context: Context) : super(context) {
        initAttribute(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttribute(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttribute(attrs, defStyleAttr)
    }

    private fun initAttribute(attrs: AttributeSet?, defStyleAttr: Int) {
        val attrArray = context.obtainStyledAttributes(attrs, R.styleable.ArcGraphView, defStyleAttr, 0)
        radius = attrArray.getDimension(R.styleable.ArcGraphView_point_radius, radius)
        pointerStrokePaint.strokeWidth = attrArray.getDimension(R.styleable.ArcGraphView_point_stroke_width, pointerStrokePaint.strokeWidth)

        attrArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        pointerStrokePaint.color = color
        canvas.drawCircle((width / 2).toFloat(), (width / 2).toFloat(), radius, pointerFillPaint)
        canvas.drawCircle((width / 2).toFloat(), (width / 2).toFloat(), radius, pointerStrokePaint)
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = ((radius + pointerStrokePaint.strokeWidth) * 2).toInt()
        setMeasuredDimension(size, size)
    }

    fun setStrokeWidth(width: Float) {
        pointerStrokePaint.strokeWidth = width
    }
}