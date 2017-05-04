package com.classting.arcgraph.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import butterknife.bindView
import com.classting.arcgraph.ArcGraphView

class SampleActivity : AppCompatActivity() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        loadView()
    }

    private fun loadView() {
        initScoreController()
        initGraphGapController()
        initGraphWeightController()
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
}
