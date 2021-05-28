package com.example.translatorapp.ui.home

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.core.testUtils.ExampleDataAndroidTest
import com.example.translatorapp.R
import com.example.translatorapp.ui.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@MediumTest
class HomeFragmentTest {


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun loadActivity() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun typeAWord() {
        onView(withId(R.id.edit_text)).perform(typeText("hot"), closeSoftKeyboard())
        Thread.sleep(20000)

        onView(withId(R.id.translation_container_home_LL)).check(matches(isDisplayed()))
        onView(withId(R.id.hindi_word_home_TV))
            .check(matches(withText(ExampleDataAndroidTest.hindi1.hindiTranslation)))
    }

}