package com.classting.arcgraph.sample

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar

/**
 * Created by BN on 2017. 5. 4..
 */

fun EditText.setOnTextChanged(l: () -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (!s.isNullOrEmpty()) {
                l()
                setSelection(length())
            }
        }
    })
}

fun SeekBar.setOnProgressChanaged(l: (progress: Int) -> Unit) {
    setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            l(progress)
        }
    })
}