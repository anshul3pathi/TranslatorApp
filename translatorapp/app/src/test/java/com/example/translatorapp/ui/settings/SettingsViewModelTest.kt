package com.example.translatorapp.ui.settings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core.AppTheme
import com.example.core.Result
import com.example.core.succeeded
import com.example.core.testUtils.ExampleDataTest
import com.example.translatorapp.fakes.FakeRepository
import com.example.translatorapp.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import org.apache.tools.ant.Main
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [28])
class SettingsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val fakeRepository = FakeRepository(shouldFetchError = false)
    private val testDispatcher = TestCoroutineDispatcher()
    private val settingsViewModel = SettingsViewModel(fakeRepository, testDispatcher)

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun clearAllTranslations_clearsSavedTranslations() = runBlockingTest {
        // Given - database has some translations saved
        fakeRepository.addData(ExampleDataTest.hindiAndEnglish1)
        fakeRepository.addData(ExampleDataTest.hindiAndEnglish2)

        // When - all translation are cleared
        settingsViewModel.clearAllTranslations()

        // Then - database should be empty
        val translations = fakeRepository.getAllTranslations()
        assertThat(translations.succeeded, `is`(true))
        translations as Result.Success
        assertThat(translations.data.isEmpty(), `is`(true))
    }

    @Test
    fun changeTheme_changesThemeValueInRepository() {
        // Given - value of app theme
        val appTheme = AppTheme.DARK
        // When - value of app theme is changed
        settingsViewModel.changeTheme(appTheme)
        // Then - the changed value should reflect in repository
        assertThat(fakeRepository.getAppTheme(), `is`(appTheme))
    }

    @Test
    fun appThemeLivedata() {
        // Given - value of app theme
        val appTheme = AppTheme.DARK
        // When - value of app theme is changed
        settingsViewModel.changeTheme(appTheme)
        // Then - value in appTheme livedata should be updated
        assertThat(settingsViewModel.appTheme.getOrAwaitValue(),`is`(appTheme))
    }

}