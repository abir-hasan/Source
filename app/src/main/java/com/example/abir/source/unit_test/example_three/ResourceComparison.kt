package com.example.abir.source.unit_test.example_three

import android.content.Context

class ResourceComparison {

    /**
     * This function returns true only if...
     * ... string from resource id and given string are equal
     */
    fun isEqual(context: Context, resId: Int, string: String): Boolean {
        return context.getString(resId) == string
    }
}