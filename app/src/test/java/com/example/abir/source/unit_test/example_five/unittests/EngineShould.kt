package com.example.abir.source.unit_test.example_five.unittests

import com.example.abir.source.unit_test.example_five.EngineV2
import com.example.abir.source.unit_test.example_five.MainCoroutineScopeRule
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class EngineShould {

    private val engine = EngineV2()

    @get:Rule
    var coroutinesTestRule = MainCoroutineScopeRule()

    @Test
    fun turnOn() = runTest {
        engine.turnOn()
        assertTrue(engine.isTurnedOn)
    }

    @Test
    fun riseTemperatureWhenItTurnsOn() = runTest {
        engine.turnOn()
        assertEquals(95, engine.temperature)
    }
}