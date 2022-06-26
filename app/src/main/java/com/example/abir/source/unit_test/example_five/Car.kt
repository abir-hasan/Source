package com.example.abir.source.unit_test.example_five

import com.example.abir.source.utils.logInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Car(val engine: EngineV2, var fuel: Double) {

    fun turnOn() {
        fuel -= 0.5
        CoroutineScope(Dispatchers.Main).launch {
            engine.turnOn().collect { temperature ->
                "Collected engine temperature -> $temperature".logInfo("Unit")
            }
        }
    }
}