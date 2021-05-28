package com.example.core.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.core.Result
import com.example.core.data.entities.HindiAndEnglishTranslation
import com.example.core.succeeded
import com.example.core.testUtils.ExampleDataAndroidTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.hasItems
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@MediumTest
class LocalDataSourceAndroidTest {

    private val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var localDataSource: LocalDataSource

    @get:Rule
    var rule = RuleChain.outerRule(hiltRule).around(instantTaskExecutorRule)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun deleteTranslations() = runBlocking {
        localDataSource.saveTranslation(ExampleDataAndroidTest.hindiAndEnglish1)

        localDataSource.deleteSavedTranslations()

        val translation =
            localDataSource.getTranslation(ExampleDataAndroidTest.hindiAndEnglish1.hindi.word)
        assertThat(translation.succeeded, `is`(false))
    }

    @Test
    fun getTranslation_returnsError_whenEmpty() = runBlocking {
        localDataSource.deleteSavedTranslations()
        val translation = localDataSource.getTranslation("hot")
        assertThat(translation.succeeded, `is`(false))
    }

    @Test
    fun saveTranslation_and_getTranslation() = runBlocking {
        localDataSource.saveTranslation(ExampleDataAndroidTest.hindiAndEnglish1)

        val translation =
            localDataSource.getTranslation(ExampleDataAndroidTest.hindiAndEnglish1.hindi.word)
        assertThat(translation.succeeded, `is`(true))
        translation as Result.Success
        assertThat(translation.data, `is`(ExampleDataAndroidTest.hindiAndEnglish1))
    }

    @Test
    fun getTranslations_returnError_whenDbEmpty() = runBlocking {
        localDataSource.deleteSavedTranslations()

        val translations = localDataSource.getAllTranslations()

        assertThat(translations.succeeded, `is`(false))
    }

//    @Test
//    fun getTranslations_returns_listOfTranslations() = runBlocking {
//        localDataSource.saveTranslation(ExampleDataAndroidTest.hindiAndEnglish1)
//        localDataSource.saveTranslation(ExampleDataAndroidTest.hindiAndEnglish2)
//        val inserted =
//            listOf(ExampleDataAndroidTest.hindiAndEnglish1, ExampleDataAndroidTest.hindiAndEnglish2)
//
//        val translations = localDataSource.getAllTranslations()
//
//        assertThat(translations.succeeded, `is`(true))
//        translations as Result.Success
//        assertThat(translations.data, hasItems(inserted[0], inserted[1]))
//    }

    @Test
    fun updatedTranslation() = runBlocking {
        val hindi = ExampleDataAndroidTest.hindi1.copy()
        val english = ExampleDataAndroidTest.english1.copy()
        val inserted = HindiAndEnglishTranslation(hindi, listOf(english))
        localDataSource.saveTranslation(inserted)

        val updatedHindi = hindi.copy(favourite = true)
        localDataSource.updateFavourite(updatedHindi)
        val expectedUpdated = HindiAndEnglishTranslation(updatedHindi, listOf(english))

        val updatedTranslation = localDataSource.getTranslation(updatedHindi.word)
        assertThat(updatedTranslation.succeeded, `is`(true))
        updatedTranslation as Result.Success
        assertThat(updatedTranslation.data, `is`(expectedUpdated))
    }

}