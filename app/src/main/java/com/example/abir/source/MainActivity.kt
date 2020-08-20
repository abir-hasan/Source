package com.example.abir.source

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.abir.source.sample.custom_text.CustomTextSampleActivity
import com.example.abir.source.sample.file_download_retrofit.FileDownloadActivity
import com.example.abir.source.sample.guided_tutorial.GuideActivity
import com.example.abir.source.sample.property_animation.PropertyAnimationActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setButton1()
        setButton2()
        setButton3()
        setButton4()
    }

    private fun setButton4() {
        button4.setOnClickListener {
            Intent(this, CustomTextSampleActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun setButton1() {
        button1.setOnClickListener {
            Intent(this, FileDownloadActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    /**
     *  Exploring TourGuide Library for Showcasing
     *  @link - https://github.com/worker8/TourGuide
     *  Also Check out below which supports custom view and has good API
     *  @link - https://github.com/faruktoptas/FancyShowCaseView
     */
    private fun setButton2() {
        button2.setOnClickListener {
            Intent(this, GuideActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun setButton3() {
        button3.setOnClickListener {
            Intent(this, PropertyAnimationActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}