package com.example.abir.source.unit_test.example_five.acaptancetests

import com.example.abir.source.unit_test.example_five.Car
import com.example.abir.source.unit_test.example_five.EngineV2
import com.example.abir.source.unit_test.example_five.MainCoroutineScopeRule
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CarFeature {

    private val engine = EngineV2()
    private val car = Car(engine, 6.0)

    @get:Rule
    var coroutinesTestRule = MainCoroutineScopeRule()

    @Test
    fun carIsLoosingFuelWhenItTurnsOn() = runTest {
        car.turnOn()
        assertEquals(5.5, car.fuel)
    }

    @Test
    fun carIsTurningOnItsEngineAndIncreasesTheTemperatureGradually() = runTest {
        car.turnOn()

        coroutinesTestRule.testDispatcher.scheduler.advanceTimeBy(2001)
        assertEquals(25, car.engine.temperature)

        coroutinesTestRule.testDispatcher.scheduler.advanceTimeBy(2000)
        assertEquals(50, car.engine.temperature)

        coroutinesTestRule.testDispatcher.scheduler.advanceTimeBy(2000)
        assertEquals(95, car.engine.temperature)

        assertTrue(car.engine.isTurnedOn)
    }
}