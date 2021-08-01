package com.example.abir.source.sample.range_seek_bar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.abir.source.databinding.ActivityRangeSeekBarTestBinding
import com.google.android.material.slider.LabelFormatter
import com.google.android.material.slider.RangeSlider

class RangeSeekBarTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRangeSeekBarTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRangeSeekBarTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRangeSlider()

    }

    private fun setUpRangeSlider() {
        binding.rsPrice.setValues(0f,10000f)
        binding.rsPrice.stepSize = 1f
        binding.rsPrice.setLabelFormatter { value->
            return@setLabelFormatter "$ $value"
        }
        //binding.rsPrice.stepSize
    }


}