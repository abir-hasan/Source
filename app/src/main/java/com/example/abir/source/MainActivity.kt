package com.example.abir.source

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.abir.source.sample.file_download_retrofit.FileDownloadActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setButton1()
    }

    private fun setButton1() {
        button1.setOnClickListener {
            Intent(this, FileDownloadActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}