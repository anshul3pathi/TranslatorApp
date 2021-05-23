package com.example.core.local

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core.*
import com.example.core.testUtils.ExampleDataTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okio.IOException
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [29])
class LocalDataSourceTest {

    private lateinit var localDataSource: LocalDataSource
    private lateinit var translationDao: TranslationDao
    private lateinit var translationDb: TranslationDataBase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        val context = ApplicationProvider.getApplicationContext<Application>()
        translationDb = Room.inMemoryDatabaseBuilder(
            context, TranslationDataBase::class.java
        ).allowMainThreadQueries().build()
        translationDao = translationDb.getTranslationDao()
        localDataSource = LocalDataSource(translationDao)
    }

    @After
    fun cleanUp() = translationDb.close()

    @Test
    fun getTranslation_returnsErrorWhenTranslationNotFound() = runBlockingTest {
        val translation = localDataSource.getTranslation("shot")
        assertThat(translation.succeeded, `is`(false))
        translation as Result.Error
        assertThat(translation.exception, instanceOf(IOException::class.java))
    }

    @Test
    fun getTranslationAndSaveTranslation() = runBlockingTest {
        localDataSource.saveTranslation(ExampleDataTest.hindiAndEnglish1)
        val translation = localDataSource.getTranslation(ExampleDataTest.hindi1.word)
        assertThat(translation.succeeded, `is`(true))
        translation as Result.Success
        assertThat(translation.data, `is`(ExampleDataTest.hindiAndEnglish1))
    }

    @Test
    fun getAllTranslations_returnsErrorWhenDbEmpty() = runBlockingTest {
        val translations = localDataSource.getAllTranslations()
        assertThat(translations.succeeded, `is`(false))
        translations as Result.Error
        assertThat(translations.exception, instanceOf(IOException::class.java))
    }

    @Test
    fun getAllTranslationsAndSaveTranslation() = runBlockingTest {
        localDataSource.saveTranslation(ExampleDataTest.hindiAndEnglish1)
        localDataSource.saveTranslation(ExampleDataTest.hindiAndEnglish2)

        val translations = localDataSource.getAllTranslations()
        assertThat(translations.succeeded, `is`(true))
        translations as Result.Success
        assertThat(
            translations.data,
            `is`(listOf(ExampleDataTest.hindiAndEnglish1, ExampleDataTest.hindiAndEnglish2))
        )
    }

    @Test
    fun deleteSavedTranslations() = runBlockingTest {
        localDataSource.saveTranslation(ExampleDataTest.hindiAndEnglish1)
        localDataSource.saveTranslation(ExampleDataTest.hindiAndEnglish2)

        localDataSource.deleteSavedTranslations()

        val translations = localDataSource.getAllTranslations()
        assertThat(translations.succeeded, `is`(false))
        translations as Result.Error
        assertThat(translations.exception, instanceOf(IOException::class.java))
    }

    @Test
    fun updateFavourite() = runBlockingTest {
        val testData = ExampleDataTest.hindi1.copy(favourite = true)

        localDataSource.saveTranslation(ExampleDataTest.hindiAndEnglish1)
        localDataSource.updateFavourite(testData)

        val translation = localDataSource.getTranslation(ExampleDataTest.hindiAndEnglish1.hindi.word)
        assertThat(translation.succeeded, `is`(true))
        translation as Result.Success
        assertThat(translation.data.hindi.favourite, `is`(true))
        assertThat(ExampleDataTest.hindiAndEnglish1.hindi.favourite, `is`(false))
    }

}