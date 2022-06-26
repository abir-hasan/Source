package com.example.abir.source.unit_test.example_five

import com.example.abir.source.utils.logDebug
import kotlinx.coroutines.delay

class EngineV2(
    var isTurnedOn: Boolean = false,
    var temperature: Int = 15
) {

    suspend fun turnOn() {
        "Starting Engine".logDebug("Unit")
        isTurnedOn = true
        delay(6000)
        temperature = 95
        "Engine has turned on".logDebug("Unit")
    }

}