package com.example.abir.source.unit_test.example_three

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.abir.source.R
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ResourceComparisonTest {

    private lateinit var resourceComparison: ResourceComparison

    @Before
    fun setUp() {
        resourceComparison = ResourceComparison()
    }

    @Test
    fun stringResourceSameAsGivenString_returnsTrue() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparison.isEqual(
            context,
            R.string.app_name,
            "Source"
        )
        assertThat(result).isTrue()
    }

    @Test
    fun stringResourceNotSameAsGivenString_returnsFalse() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparison.isEqual(
            context,
            R.string.app_name,
            "Test App"
        )
        assertThat(result).isFalse()
    }
}