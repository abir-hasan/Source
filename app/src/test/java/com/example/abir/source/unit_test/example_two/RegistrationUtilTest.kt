package com.example.abir.source.unit_test.example_two

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationUtilTest {

    @Test
    fun `empty username returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "",
            "123456",
            "123456"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `valid username and correctly repeated password returns true`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Junayed",
            "123456",
            "123456"
        )
        assertThat(result).isTrue()
    }

    @Test
    fun `username already exists returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Abir",
            "123456",
            "123457"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `empty password returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Junayed",
            "",
            "123457"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `incorrectly repeated password returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Junayed",
            "123456",
            "123457"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `insufficient password length returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Junayed",
            "123",
            "123"
        )
        assertThat(result).isFalse()
    }
}