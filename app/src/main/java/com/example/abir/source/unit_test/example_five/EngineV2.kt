package com.example.abir.source.unit_test.example_five

import com.example.abir.source.utils.logDebug
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EngineV2(
    var isTurnedOn: Boolean = false,
    var temperature: Int = 15
) {

    suspend fun turnOn(): Flow<Int> {
        "Starting Engine".logDebug("Unit")
        isTurnedOn = true
        return flow {
            delay(2000)
            temperature = 25
            emit(temperature)

            delay(2000)
            temperature = 50
            emit(temperature)

            delay(2000)
            temperature = 95
            emit(temperature)
            "Engine has turned on".logDebug("Unit")
        }
    }

}