package com.example.abir.source.sample.coroutine_flow_demo

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FlowDemoViewModel : ViewModel() {

    companion object {
        private const val TAG = "FlowDemoViewModel"
    }

    /**
     * State Flows are hot flows
     * Similar to Live Data but they are not aware of view lifecyle like live data
     * Multiple operators can be used
     * State Flows are type of Shared Flow
     */
    private val _counterStateFlow = MutableStateFlow(0)
    val counterStateFlow = _counterStateFlow.asStateFlow()

    fun incrementCounter() {
        _counterStateFlow.value += 1
    }
}