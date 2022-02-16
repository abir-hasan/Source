package com.example.abir.source

import com.example.abir.source.unit_test.example_one.Dice
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun generate_number() {
        val dice = Dice(6)
        val rolledNumber = dice.roll()
        assertTrue("Value is not between 1 and 6", rolledNumber in 1..6)
    }
}