package com.classting.arcgraph.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import butterknife.bindView
import com.classting.arcgraph.ArcGraphView
import com.jrummyapps.android.colorpicker.ColorPickerDialog
import com.jrummyapps.android.colorpicker.ColorPickerDialogListener
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.onClick

class SampleActivity : AppCompatActivity(), ColorPickerDialogListener {

    private val COLOR_DIALOG_ID_SECTION1 = 0
    private val COLOR_DIALOG_ID_SECTION2 = 1
    private val COLOR_DIALOG_ID_SECTION3 = 2
    private val COLOR_DIALOG_ID_SECTION4 = 3

    private val graph by bindView<ArcGraphView>(R.id.graph)
    private val scorePoint by bindView<TextView>(R.id.score_point)
    private val scoreSeekBar by bindView<SeekBar>(R.id.score_controller)
    private val currentScore by bindView<EditText>(R.id.current_score)
    private val gapSeekBar by bindView<SeekBar>(R.id.gap_controller)
    private val currentGap by bindView<EditText>(R.id.current_gap)
    private val section1Weight by bindView<EditText>(R.id.section1_weight)
    private val section2Weight by bindView<EditText>(R.id.section2_weight)
    private val section3Weight by bindView<EditText>(R.id.section3_weight)
    private val section4Weight by bindView<EditText>(R.id.section4_weight)
    private val section1Color by bindView<View>(R.id.section1_color)
    private val section2Color by bindView<View>(R.id.section2_color)
    private val section3Color by bindView<View>(R.id.section3_color)
    private val section4Color by bindView<View>(R.id.section4_color)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        loadView()
    }

    private fun loadView() {
        initScoreController()
        initGraphGapController()
        initGraphWeightController()
        initColorController()
    }

    private fun initGraphWeightController() {
        section1Weight.setOnTextChanged {
            graph.setSectionWeights(section1Weight.text.toString().toInt())
        }

        section2Weight.setOnTextChanged {
            graph.setSectionWeights(section2Weight = section2Weight.text.toString().toInt())
        }

        section3Weight.setOnTextChanged {
            graph.setSectionWeights(section3Weight = section3Weight.text.toString().toInt())
        }

        section4Weight.setOnTextChanged {
            graph.setSectionWeights(section4Weight = section4Weight.text.toString().toInt())
        }

        val sectionWeights = graph.getSectionWeights()
        section1Weight.hint = (sectionWeights?.getOrNull(0)?.toString() ?: "").toString()
        section2Weight.hint = (sectionWeights?.getOrNull(1)?.toString() ?: "").toString()
        section3Weight.hint = (sectionWeights?.getOrNull(2)?.toString() ?: "").toString()
        section4Weight.hint = (sectionWeights?.getOrNull(3)?.toString() ?: "").toString()
    }

    private fun initScoreController() {
        scoreSeekBar.max = 100
        scoreSeekBar.setOnProgressChanaged { progress ->
            graph.setScore(progress)
            currentScore.setText(progress.toString())
            scorePoint.text = progress.toString()
        }

        currentScore.setOnTextChanged {
            graph.setScore(currentScore.text.toString().toInt())
            scoreSeekBar.progress = currentScore.text.toString().toInt()
            scorePoint.text = currentScore.text
        }
    }

    private fun initGraphGapController() {
        gapSeekBar.max = 60
        gapSeekBar.setOnProgressChanaged { progress ->
            graph.setGraphGapAngle(progress.toFloat())
            currentGap.setText(progress.toString())
        }

        currentGap.setOnTextChanged {
            graph.setGraphGapAngle(currentGap.text.toString().toFloat())
            gapSeekBar.progress = currentGap.text.toString().toInt()
        }

        gapSeekBar.progress = (graph.getGraphGapAngle() ?: 0f).toInt()
    }

    private fun initColorController() {
        val graphColors = graph.getSectionColors()
        val defaultColor = ContextCompat.getColor(this, R.color.black_54)
        section1Color.backgroundColor = graphColors.getOrElse(0, { defaultColor })
        section2Color.backgroundColor = graphColors.getOrElse(1, { defaultColor })
        section3Color.backgroundColor = graphColors.getOrElse(2, { defaultColor })
        section4Color.backgroundColor = graphColors.getOrElse(3, { defaultColor })

        section1Color.onClick {
            showColorPickerDialog(COLOR_DIALOG_ID_SECTION1, graphColors.getOrElse(0, { defaultColor }))
        }
        section2Color.onClick {
            showColorPickerDialog(COLOR_DIALOG_ID_SECTION2, graphColors.getOrElse(1, { defaultColor }))
        }
        section3Color.onClick {
            showColorPickerDialog(COLOR_DIALOG_ID_SECTION3, graphColors.getOrElse(2, { defaultColor }))
        }
        section4Color.onClick {
            showColorPickerDialog(COLOR_DIALOG_ID_SECTION4, graphColors.getOrElse(3, { defaultColor }))
        }
    }

    private fun showColorPickerDialog(dialogId: Int, currentColor: Int) {
        ColorPickerDialog.newBuilder()
            .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
            .setAllowPresets(false)
            .setDialogId(dialogId)
            .setColor(currentColor)
            .setShowAlphaSlider(true)
            .show(this)
    }

    override fun onDialogDismissed(dialogId: Int) {}

    override fun onColorSelected(dialogId: Int, color: Int) {
        when (dialogId) {
            COLOR_DIALOG_ID_SECTION1 -> {
                graph.setSectionColors(color1 = color)
                section1Color.backgroundColor = color
            }
            COLOR_DIALOG_ID_SECTION2 -> {
                graph.setSectionColors(color2 = color)
                section2Color.backgroundColor = color
            }
            COLOR_DIALOG_ID_SECTION3 -> {
                graph.setSectionColors(color3 = color)
                section3Color.backgroundColor = color
            }
            COLOR_DIALOG_ID_SECTION4 -> {
                graph.setSectionColors(color4 = color)
                section4Color.backgroundColor = color
            }
        }
    }
}
