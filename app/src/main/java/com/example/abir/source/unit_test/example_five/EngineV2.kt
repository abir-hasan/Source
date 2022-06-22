package com.example.abir.source.unit_test.example_five

class EngineV2(
    var isTurnedOn: Boolean = false,
    var temperature: Int = 15
) {

    fun turnOn() {
        isTurnedOn = true
        temperature = 95
    }

}