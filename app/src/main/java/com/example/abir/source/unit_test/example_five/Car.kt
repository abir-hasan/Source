package com.example.abir.source.unit_test.example_five

class Car(val engine: EngineV2, var fuel: Double) {

    fun turnOn() {
        fuel -= 0.5
        engine.turnOn()
    }
}