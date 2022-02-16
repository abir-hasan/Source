package com.example.abir.source.unit_test.example_two

import com.google.common.truth.Truth.assertThat
import org.junit.Test


class HomeWorkTest {

    @Test
    fun `true if 0th fibonacci is 0`() {
        val result: Int = HomeWork.getNthFibonacci(0)
        assertThat(result).isEqualTo(0)
    }

    @Test
    fun `true if 1st fibonacci is 1`() {
        val result: Int = HomeWork.getNthFibonacci(1)
        assertThat(result).isEqualTo(1)
    }

    @Test
    fun `true if 2nd fibonacci is 1`() {
        val result: Int = HomeWork.getNthFibonacci(2)
        assertThat(result).isEqualTo(1)
    }

    @Test
    fun `true if 3rd fibonacci is 2`() {
        val result: Int = HomeWork.getNthFibonacci(3)
        assertThat(result).isEqualTo(2)
    }

    @Test
    fun `true if 7th fibonacci is 13`() {
        val result: Int = HomeWork.getNthFibonacci(7)
        assertThat(result).isEqualTo(13)
    }

    @Test
    fun `this case should return false`() {
        val result = HomeWork.checkBraces("(a*(b+c)")
        assertThat(result).isFalse()
    }

    @Test
    fun `this case should return true`() {
        val result = HomeWork.checkBraces("(a*(b+c))")
        assertThat(result).isTrue()
    }

}