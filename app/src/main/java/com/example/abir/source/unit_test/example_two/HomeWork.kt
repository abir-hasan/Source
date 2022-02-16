package com.example.abir.source.unit_test.example_two

object HomeWork {

    /**
     * Returns the n-th fibonacci number
     * f(0) = 0
     * f(1) = 1
     * f(2) = 1
     * f(3) = 2
     * ......
     * fib(n) = fib(n-1) + fib(n-2)
     */
    fun getNthFibonacci(n: Int): Int {
        if (n == 0 || n == 1) {
            return n
        }
        var a = 0
        var b = 1
        var temp = 0
        for (i in 1 until n) {
            temp = a + b
            a = b
            b = temp
        }
        return temp
    }

    /**
     *Check If Braces Are Set Correctly
     * ex - (a*(b+c)) should return false
     * ex - ((a*(b+c)) should return true
     */
    fun checkBraces(value: String): Boolean {
        return value.count { it == '(' } == value.count { it == ')' }
    }
}