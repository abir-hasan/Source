package com.example.abir.source.unit_test.example_five.unittests

import com.example.abir.source.unit_test.example_five.Car
import com.example.abir.source.unit_test.example_five.EngineV2
import com.example.abir.source.unit_test.example_five.MainCoroutineScopeRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CarShould {

    private val engine: EngineV2 = mock()
    private val car: Car

    init {
        // Whenever we make an object, we also need to mock the behavior of all of its functions.
        // Now, when the function does not have a return type, then there is nothing to mock.
        runTest {
            whenever(engine.turnOn()).thenReturn(flow {
                delay(2000)
                emit(25)
                delay(2000)
                emit(50)
                delay(2000)
                emit(95)
            })
        }
        // Mock the behaviour of the dependency first. Then create the object
        car = Car(engine, 5.0)
    }

    @get:Rule
    var coroutinesTestRule = MainCoroutineScopeRule()

    @Test
    fun looseFuelWhenItTurnsOn() = runTest {
        car.turnOn()
        assertEquals(4.5, car.fuel)
    }

    @Test
    fun turnItsEngine() = runTest {
        car.turnOn()
        verify(engine, times(1)).turnOn()
    }
}