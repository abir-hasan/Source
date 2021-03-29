package com.example.abir.source.utils

import java.text.DecimalFormat


/**
 * Format amount value and return
 */
fun String.formatAmount(): String {
    return try {
        val amount = DecimalFormat("##.##").format(this.toDouble())
        amount
    } catch (e: Exception) {
        ""
    }
}

/**
 * Format amount value and return
 */
fun Double.formatAmount(): String {
    return try {
        val amount = DecimalFormat("##.##").format(this)
        amount
    } catch (e: Exception) {
        ""
    }
}