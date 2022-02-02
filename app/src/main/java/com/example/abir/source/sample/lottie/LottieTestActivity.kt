package com.example.abir.source.sample.lottie

import android.os.Bundle
import com.example.abir.source.BaseActivity
import com.example.abir.source.databinding.ActivityLottieTestBinding

class LottieTestActivity : BaseActivity() {

    private lateinit var binding: ActivityLottieTestBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLottieTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}