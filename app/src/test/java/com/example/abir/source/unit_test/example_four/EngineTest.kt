package com.example.abir.source.unit_test.example_four

import junit.framework.TestCase.assertEquals
import org.junit.Test

class EngineTest {

    private val engine = Engine(2000, 189, 15, false)

    @Test
    fun engineTurnsOn() {
        engine.turnOn()
        assertEquals(true, engine.isTurnedOn)
        assertEquals(95, engine.temperature)
    }

    @Test
    fun engineTurnsOff() {
        engine.turnOn()
        engine.turnOff()
        assertEquals(false, engine.isTurnedOn)
        assertEquals(15, engine.temperature)
    }

}