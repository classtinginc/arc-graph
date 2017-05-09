package com.classting.arcgraph

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * Created by BN on 2017. 4. 27..
 */

class GraphView: View {
    private val SECTION1_SCORE = 14
    private val SECTION2_SCORE = 49
    private val SECTION3_SCORE = 84
    private val SECTION4_SCORE = 100

    private val SECTION1_WEIGHT = 1
    private val SECTION2_WEIGHT = 2
    private val SECTION3_WEIGHT = 2
    private val SECTION4_WEIGHT = 1

    var graphPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 10f
        color = Color.rgb(255, 220, 21)
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND
    }

    var graphBound = RectF()
    var radius = 200f
    var sections: MutableList<Section> = mutableListOf()
    var gapAngle = 4f
    var standardAngle = (180f - gapAngle * 3) / 6f
    var boundOffset = 0f
    var isCustomRadius = false
    var section1Weight = SECTION1_WEIGHT
    var section2Weight = SECTION2_WEIGHT
    var section3Weight = SECTION3_WEIGHT
    var section4Weight = SECTION4_WEIGHT

    data class Section(var color: Int = Color.rgb(255, 220, 21), var startAngle: Float = 0f, var sweepAngle: Float = 30f, var score: Int = 0)

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

        graphPaint.strokeWidth = attrArray.getDimension(R.styleable.ArcGraphView_graph_stroke_width, graphPaint.strokeWidth)
        radius = attrArray.getDimension(R.styleable.ArcGraphView_graph_radius, radius)
        isCustomRadius = attrArray.hasValue(R.styleable.ArcGraphView_graph_radius)

        gapAngle = attrArray.getFloat(R.styleable.ArcGraphView_graph_gap_angle, gapAngle)

        section1Weight = attrArray.getInteger(R.styleable.ArcGraphView_section1_weight, SECTION1_WEIGHT)
        section2Weight = attrArray.getInteger(R.styleable.ArcGraphView_section2_weight, SECTION2_WEIGHT)
        section3Weight = attrArray.getInteger(R.styleable.ArcGraphView_section3_weight, SECTION3_WEIGHT)
        section4Weight = attrArray.getInteger(R.styleable.ArcGraphView_section4_weight, SECTION4_WEIGHT)

        standardAngle = (180f - gapAngle * 3) / (section1Weight + section2Weight + section3Weight + section4Weight).toFloat()

        var currentAngle = 0f
        sections.add(Section().apply {
            color = attrArray.getColor(R.styleable.ArcGraphView_section1_color, graphPaint.color)
            score = attrArray.getInteger(R.styleable.ArcGraphView_section1_score, SECTION1_SCORE)
            startAngle = 0f
            sweepAngle = standardAngle * section1Weight
            currentAngle = startAngle + sweepAngle
        })
        sections.add(Section().apply {
            color = attrArray.getColor(R.styleable.ArcGraphView_section2_color, graphPaint.color)
            score = attrArray.getInteger(R.styleable.ArcGraphView_section2_score, SECTION2_SCORE)
            startAngle = currentAngle + gapAngle
            sweepAngle = standardAngle * section2Weight
            currentAngle = startAngle + sweepAngle
        })
        sections.add(Section().apply {
            color = attrArray.getColor(R.styleable.ArcGraphView_section3_color, graphPaint.color)
            score = attrArray.getInteger(R.styleable.ArcGraphView_section3_score, SECTION3_SCORE)
            startAngle = currentAngle + gapAngle
            sweepAngle = standardAngle * section3Weight
            currentAngle = startAngle + sweepAngle
        })
        sections.add(Section().apply {
            color = attrArray.getColor(R.styleable.ArcGraphView_section4_color, graphPaint.color)
            score = attrArray.getInteger(R.styleable.ArcGraphView_section4_score, SECTION4_SCORE)
            startAngle = currentAngle + gapAngle
            sweepAngle = standardAngle * section4Weight
        })

        attrArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        drawGraph(canvas)
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = View.getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        if (!isCustomRadius || (isCustomRadius && width < radius * 2)) {
            radius = (width.toFloat() - (graphPaint.strokeWidth + boundOffset) * 2) / 2
        } else {
            width = (radius + graphPaint.strokeWidth + boundOffset).toInt() * 2
        }

        val height = (width / 2 + graphPaint.strokeWidth + boundOffset).toInt()
        setMeasuredDimension(width, height)

        val diameter = if (width < this.radius * 2) width.toFloat() else this.radius * 2

        graphBound.apply {
            left = graphPaint.strokeWidth + boundOffset
            top = graphPaint.strokeWidth + boundOffset
            right = graphPaint.strokeWidth + boundOffset + diameter
            bottom = graphPaint.strokeWidth + boundOffset + diameter
        }
    }

    private fun drawGraph(canvas: Canvas) {
        sections.forEach {
            graphPaint.color = it.color
            canvas.drawArc(graphBound, it.startAngle - 180f, it.sweepAngle, false, graphPaint)
        }
    }

    fun getSectionColor(score: Int): Int {
        if (score >= 0 && score <= sections[0].score) {
            return sections[0].color
        } else if (score > sections[0].score && score <= sections[1].score) {
            return sections[1].color
        } else if (score > sections[1].score && score <= sections[2].score) {
            return sections[2].color
        } else if (score > sections[2].score && score <= sections[3].score) {
            return sections[3].color
        }
        return sections[3].color
    }

    fun getScorePosition(score: Int): PointF {
        var angle = 0f

        if (score >= 0 && score <= sections[0].score) {
            angle = getScoreAngle(0 , score)
        } else if (score > sections[0].score && score <= sections[1].score) {
            angle = getScoreAngle(1 , score)
        } else if (score > sections[1].score && score <= sections[2].score) {
            angle = getScoreAngle(2 , score)
        } else if (score > sections[2].score && score <= sections[3].score) {
            angle = getScoreAngle(3 , score)
        }

        angle -= 180

        return PointF().apply {
            x = (Math.cos(angle * Math.PI / 180) * radius).toFloat() + graphBound.centerX()
            y = (Math.sin(angle * Math.PI / 180) * radius).toFloat() + graphBound.centerY()
        }
    }

    private fun getScoreAngle(graphIndex: Int, score: Int): Float {
        val angle = if (graphIndex in 1..2) standardAngle * 2 else standardAngle
        val amount = if (graphIndex > 0) score - sections[graphIndex - 1].score - 1 else score
        val divide = if (graphIndex > 0) sections[graphIndex].score - sections[graphIndex - 1].score - 1 else sections[graphIndex].score
        return angle * amount / divide + sections[graphIndex].startAngle
    }

    private fun resetGraphAngle() {
        standardAngle = (180f - gapAngle * 3) / (section1Weight + section2Weight + section3Weight + section4Weight).toFloat()

        var currentAngle = 0f
        sections[0].apply {
            startAngle = 0f
            sweepAngle = standardAngle * section1Weight
            currentAngle = startAngle + sweepAngle
        }
        sections[1].apply {
            startAngle = currentAngle + gapAngle
            sweepAngle = standardAngle * section2Weight
            currentAngle = startAngle + sweepAngle
        }
        sections[2].apply {
            startAngle = currentAngle + gapAngle
            sweepAngle = standardAngle * section3Weight
            currentAngle = startAngle + sweepAngle
        }
        sections[3].apply {
            startAngle = currentAngle + gapAngle
            sweepAngle = standardAngle * section4Weight
        }
    }

    fun setColors(section1Color: Int? = null, section2Color: Int? = null, section3Color: Int? = null, section4Color: Int? = null) {
        section1Color?.let { color -> sections.getOrNull(0)?.let { it.color = color } }
        section2Color?.let { color -> sections.getOrNull(1)?.let { it.color = color } }
        section3Color?.let { color -> sections.getOrNull(2)?.let { it.color = color } }
        section4Color?.let { color -> sections.getOrNull(3)?.let { it.color = color } }
    }

    fun getColors() = sections.map { it.color }

    fun setGap(angle: Float) {
        gapAngle = angle

        resetGraphAngle()
    }

    fun setWeights(section1Weight: Int? = null, section2Weight: Int? = null, section3Weight: Int? = null, section4Weight: Int? = null) {
        section1Weight?.let { this.section1Weight = it }
        section2Weight?.let { this.section2Weight = it }
        section3Weight?.let { this.section3Weight = it }
        section4Weight?.let { this.section4Weight = it }

        resetGraphAngle()
    }

    fun getWeights() = mutableListOf(section1Weight, section2Weight, section3Weight, section4Weight)

    fun setStrokeWidth(width: Float) {
        graphPaint.strokeWidth = width
    }
}