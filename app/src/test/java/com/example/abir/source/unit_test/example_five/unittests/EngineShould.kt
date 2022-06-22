package com.example.abir.source.unit_test.example_five.unittests

import com.example.abir.source.unit_test.example_five.EngineV2
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class EngineShould {

    private val engine = EngineV2()

    @Test
    fun turnOn() {
        engine.turnOn()
        assertTrue(engine.isTurnedOn)
    }

    @Test
    fun riseTemperatureWhenItTurnsOn() {
        engine.turnOn()
        assertEquals(95, engine.temperature)
    }
}