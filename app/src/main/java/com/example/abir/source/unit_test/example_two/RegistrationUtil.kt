package com.example.abir.source.unit_test.example_two

object RegistrationUtil {

    private val exisitingUsers = listOf("Test", "Abir")

    /**
     * The input is not valid if...
     * ... username/password/confirmedPassword is empty
     * ... username exists in db
     * ... password is less than 6 digits
     */
    fun validateRegistrationInput(
        userName: String,
        userPassword: String,
        confirmedPassword: String
    ): Boolean {
        return true
    }

}