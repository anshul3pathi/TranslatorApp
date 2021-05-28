package com.example.translatorapp.ui.navigation

import android.view.Gravity
import androidx.navigation.findNavController
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions.open
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.NavigationViewActions.navigateTo
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import com.example.translatorapp.R
import com.example.translatorapp.ui.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@LargeTest
class NavigationTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)
    private lateinit var activityScenario: ActivityScenario<MainActivity>

    @Before
    fun init() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun drawerNavigationFromHomeToPhraseBook() {
        onView(withId(R.id.drawer))
            .check(matches(isClosed(Gravity.START)))
            .perform(open())

        onView(withId(R.id.navigation_view))
            .perform(navigateTo(R.id.phraseBookFragment))

        onView(withId(R.id.phrase_book_LL)).check(matches(isDisplayed()))

        onView(withId(R.id.drawer))
            .check(matches(isClosed(Gravity.START)))
            .perform(open())

        onView(withId(R.id.navigation_view))
            .perform(navigateTo(R.id.homeFragment))

        onView(withId(R.id.input_word_editText))
            .check(matches(isDisplayed()))
    }

    @Test
    fun drawerNavigationFromHomeToSettings() {
        onView(withId(R.id.drawer))
            .check(matches(isClosed(Gravity.START)))
            .perform(open())

        onView(withId(R.id.navigation_view))
            .perform(navigateTo(R.id.settingsFragment))

        onView(withId(R.id.parent_settings_LL)).check(matches(isDisplayed()))

        onView(withId(R.id.drawer))
            .check(matches(isClosed(Gravity.START)))
            .perform(open())

        onView(withId(R.id.navigation_view))
            .perform(navigateTo(R.id.homeFragment))

        onView(withId(R.id.input_word_editText))
            .check(matches(isDisplayed()))
    }

    @Test
    fun drawerNavigationFromPhraseBookToSettings() {
        activityScenario.onActivity {
            it.findNavController(R.id.nav_host_fragment).navigate(R.id.phraseBookFragment)
        }
        onView(withId(R.id.phrase_book_LL))
            .check(matches(isDisplayed()))

        onView(withId(R.id.drawer))
            .check(matches(isClosed(Gravity.START)))
            .perform(open())

        onView(withId(R.id.navigation_view))
            .perform(navigateTo(R.id.settingsFragment))

        onView(withId(R.id.parent_settings_LL)).check(matches(isDisplayed()))

        onView(withId(R.id.drawer))
            .check(matches(isClosed(Gravity.START)))
            .perform(open())

        onView(withId(R.id.navigation_view))
            .perform(navigateTo(R.id.phraseBookFragment))

        onView(withId(R.id.phrase_book_LL))
            .check(matches(isDisplayed()))
    }

}