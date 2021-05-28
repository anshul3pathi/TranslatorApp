package com.example.core.local

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@RunWith(AndroidJUnit4::class)
@Config(sdk = [29])
class ConvertersTest {

    private val expectedString = "Ek^Do^Teen^Chaar^Paanch^Chhe^Saat^Aath^Nau^Das"
    private val expectedList =
        listOf("Ek", "Do", "Teen", "Chaar", "Paanch", "Chhe", "Saat", "Aath", "Nau", "Das")

    private val converter = Converters()

    @Test
    fun fromListToString() {
        val actualString = converter.fromListToString(expectedList)
        assertThat(actualString, `is`(expectedString))
    }

    @Test
    fun fromStringToList() {
        val actualList = converter.fromStringToList(expectedString)
        assertThat(actualList, `is`(expectedList))
    }

}