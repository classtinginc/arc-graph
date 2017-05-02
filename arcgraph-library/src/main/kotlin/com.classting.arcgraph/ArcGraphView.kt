package com.classting.arcgraph

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout

class ArcGraphView : RelativeLayout {

    private val DEFAULT_COLOR = Color.rgb(3, 169, 244)

    private var pointerView: PointerView? = null
    private var graphView: GraphView? = null
    private var score = 0

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

        score = attrArray.getInteger(R.styleable.ArcGraphView_point_score, score)
        pointerView = PointerView(context, attrs, defStyleAttr)
        graphView = GraphView(context, attrs, defStyleAttr).apply {
            boundOffset = pointerView?.radius ?: 0f
        }

        addView(graphView)
        addView(pointerView)

        attrArray.recycle()
    }

    init {
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        this.layoutParams = params
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setScore(score)
    }

    private fun setPointer(score: Int) {
        val position = graphView?.getScorePosition(score)
        pointerView?.x = (position?.x ?: 0f) - (pointerView?.measuredWidth ?: 0) / 2
        pointerView?.y = (position?.y ?: 0f) - (pointerView?.measuredHeight ?: 0) / 2

        pointerView?.color = graphView?.getSectionColor(score) ?: DEFAULT_COLOR
    }

    fun getPointerView() = pointerView

    fun setScore(score: Int) {
        this.score = score

        setPointer(score)
    }

    fun setSectionColor(colors: Array<Int>) {
        graphView?.setColors(colors)
    }

    fun setGraphGapAngle(gap: Float) {
        graphView?.setGap(gap)
    }

    fun getGraphGapAngle() = graphView?.gapAngle

    fun getSectionWeights() = graphView?.getWeights()

    fun setSectionWeights(section1Weight: Int? = null, section2Weight: Int? = null, section3Weight: Int? = null, section4Weight: Int? = null) {
        graphView?.setWeights(section1Weight, section2Weight, section3Weight, section4Weight)
    }

    fun setGraphStrokeWidth(width: Float) {
        graphView?.setStrokeWidth(width)
    }

    fun seGraphRadius(radius: Float) {
        graphView?.radius = radius
    }

    fun setPointerRadius(radius: Float) {
        pointerView?.radius = radius
    }

    fun setPointerStrokeWidth(width: Float) {
        pointerView?.setStrokeWidth(width)
    }
}