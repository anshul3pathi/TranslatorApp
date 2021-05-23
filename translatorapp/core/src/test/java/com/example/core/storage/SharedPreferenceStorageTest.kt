package com.example.core.storage

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core.AppTheme
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE, sdk = [29])
class SharedPreferenceStorageTest {

    private lateinit var sharedPreferenceStorage: SharedPreferenceStorage

    @Before
    fun init() {
        val context = ApplicationProvider.getApplicationContext<Application>()
        sharedPreferenceStorage = SharedPreferenceStorage(context)
    }

    @Test
    fun setTheme_value_value() {
        val value = 100
        //  When - value is saved using shared preferences
        sharedPreferenceStorage.setTheme(value)
        //  Then - when the value is fetched, it should fetch the saved value
        val fetchedValue = sharedPreferenceStorage.getTheme()
        assertThat(value, `is`(fetchedValue))
    }

    @Test
    fun getTheme_noValue_fetches_1() {
        //  When - no value is saved using shared preferences
        val fetchedValue = sharedPreferenceStorage.getTheme()
        // Then - sharedPref fetches a default value of 1
        assertThat(fetchedValue, `is`(1))
    }

}