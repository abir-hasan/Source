package com.example.abir.source.sample.coroutine_flow_demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.abir.source.BaseActivity
import com.example.abir.source.databinding.ActivityFlowDemoBinding
import com.example.abir.source.utils.showSnackBarShort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FlowDemoActivity : BaseActivity() {

    companion object {
        private const val TAG = "FlowDemoActivity"
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[FlowDemoViewModel::class.java]
    }

    private lateinit var binding: ActivityFlowDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlowDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStateFlow.setOnClickListener {
            viewModel.incrementCounter()
        }

        binding.btnBasicFlow.setOnClickListener {
            startCountDown()
        }

        binding.btnSharedFlow.setOnClickListener {
            viewModel.triggerSharedFlow()
        }

        observeStateFlow()
        observeSharedFlow()
    }

    private fun observeSharedFlow() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.toastySharedFlow.collectLatest { value ->
                    binding.tvSharedFlowValue.text = value
                    showSnackBarShort(value)
                }
            }
        }
    }

    private fun startCountDown() {
        launchAndCollectIn(viewModel.triggerCountDownTimer()) { time ->
            binding.tvBasicFlowValue.text = "$time"
        }
    }

    private fun observeStateFlow() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.counterStateFlow.collectLatest { count ->
                    binding.tvStateFlowValue.text = "Count: $count"
                }
            }
        }

        // Code Above Can Also Be Simplified Like This
        /*launchAndCollectIn(viewModel.counterStateFlow) { count ->
            binding.tvStateFlowValue.text = "Count: $count"
        }*/
    }
}

/**
 * An extention function to simplify Flow collection with lifecycle state
 */
fun <T> AppCompatActivity.launchAndCollectIn(
    flow: Flow<T>,
    collect: suspend (T) -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }
}