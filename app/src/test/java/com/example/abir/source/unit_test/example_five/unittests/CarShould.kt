package com.example.abir.source.unit_test.example_five.unittests

import com.example.abir.source.unit_test.example_five.Car
import com.example.abir.source.unit_test.example_five.EngineV2
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase.assertEquals
import org.junit.Test

class CarShould {

    private val engine: EngineV2 = mock()
    private val car = Car(engine, 5.0)

    @Test
    fun looseFuelWhenItTurnsOn() {
        car.turnOn()
        assertEquals(4.5, car.fuel)
    }

    @Test
    fun turnItsEngine() {
        car.turnOn()
        verify(engine, times(1)).turnOn()
    }
}