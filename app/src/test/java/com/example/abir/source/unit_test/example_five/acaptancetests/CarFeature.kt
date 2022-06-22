package com.example.abir.source.unit_test.example_five.acaptancetests

import com.example.abir.source.unit_test.example_five.Car
import com.example.abir.source.unit_test.example_five.EngineV2
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class CarFeature {

    private val engine = EngineV2()
    private val car = Car(engine, 6.0)

    @Test
    fun carIsLoosingFuelWhenItTurnsOn() {
        car.turnOn()
        assertEquals(5.5, car.fuel)
    }

    @Test
    fun carIsTurningOnItsEngineAndIncreasesTheTemperature() {
        car.turnOn()
        assertEquals(95, car.engine.temperature)
        assertTrue(car.engine.isTurnedOn)
    }
}