package com.example.abir.source.sample.coroutine_flow_demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

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

    /**
     * Won't fire on configuration change [Good to use with toast/snackbars]
     * is a Hot Flow
     */
    private val _toastySharedFlow = MutableSharedFlow<String>()
    val toastySharedFlow = _toastySharedFlow.asSharedFlow()

    fun incrementCounter() {
        _counterStateFlow.value += 1
    }

    fun triggerSharedFlow() {
        viewModelScope.launch {
            _toastySharedFlow.emit("Shared Flow Triggered")
        }
    }

    /**
     * Example of a simple Flow
     * Basic Flow is also a cold flow
     * Doesn't Hold Any State [So won't respect device rotation/config change]
     */
    fun triggerCountDownTimer(): Flow<Int> {
        return flow {
            val initialValue = 10
            var currentValue = initialValue
            while (currentValue > -1) {
                emit(currentValue)
                delay(1000L)
                currentValue -= 1
            }
        }
    }
}