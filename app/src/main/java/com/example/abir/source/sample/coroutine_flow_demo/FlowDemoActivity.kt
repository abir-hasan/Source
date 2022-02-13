package com.example.abir.source.sample.coroutine_flow_demo

import android.os.Bundle
import com.example.abir.source.BaseActivity
import com.example.abir.source.databinding.ActivityFlowDemoBinding


class FlowDemoActivity : BaseActivity() {

    companion object {
        private const val TAG = "FlowDemoActivity"
    }

    private lateinit var binding: ActivityFlowDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlowDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}