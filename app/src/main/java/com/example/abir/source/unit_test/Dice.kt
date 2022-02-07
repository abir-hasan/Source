package com.example.abir.source.unit_test

import kotlin.random.Random

class Dice(private val maxDiceSide: Int) {

    companion object {
        private const val TAG = "Dice"
    }

    private val seedValue = 321

    fun roll(): Int {
        val random = Random(seedValue)
        return random.nextInt(maxDiceSide)
    }


}