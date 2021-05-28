package com.example.core.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.example.core.testUtils.ExampleDataAndroidTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Test


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TranslationDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: TranslationDataBase
    private lateinit var dao: TranslationDao

    @Before
    fun init() {
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            TranslationDataBase::class.java
        ).build()
        dao = database.getTranslationDao()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun getTranslation_returnNullWhenNotFound() = runBlockingTest {
        // Given - database doesn't contain the solicited translation
        dao.deleteAllEnglishTranslations()
        dao.deleteAllHindiTranslations()
        // When - translation is asked
        val translation = dao.getTranslation("hot")
        // Then - dao return null value
        assertThat(translation, `is`(nullValue()))
    }

    @Test
    fun insertTranslation_getTranslation_returnInsertedTranslation() = runBlockingTest {
        // Given - inserting hindi and english translations
        dao.insertHindiTranslation(ExampleDataAndroidTest.hindi1)
        dao.insertEnglishTranslation(ExampleDataAndroidTest.english1)
        // When - HindiAndEnglishTranslation is required
        val translation = dao.getTranslation(ExampleDataAndroidTest.hindi1.word)
        // Then - inserted hindi and english translation are fetched
        assertThat(translation, `is`(notNullValue()))
        assertThat(translation!!.hindi, `is`(ExampleDataAndroidTest.hindi1))
        assertThat(translation.englishTranslations, `is`(listOf(ExampleDataAndroidTest.english1)))
    }

    @Test
    fun insertEnglishTranslations_insertsListOfEnglishTranslations() = runBlockingTest {
        // Given - when list of english translations pertaining to hindi translation are inserted
        dao.insertHindiTranslation(ExampleDataAndroidTest.hindi2)
        dao.insertEnglishTranslations(
            listOf(
                ExampleDataAndroidTest.english22,
                ExampleDataAndroidTest.english32,
                ExampleDataAndroidTest.english42
            )
        )
        // When - translation is retrieved
        val translation = dao.getTranslation(ExampleDataAndroidTest.hindi2.word)
        // Then - inserted translation is returned
        assertThat(translation, `is`(notNullValue()))
        assertThat(translation!!.hindi, `is`(ExampleDataAndroidTest.hindi2))
    }

    @Test
    fun updateFavourite() = runBlockingTest {
        //  Given - a HindiAndEnglishTranslation
        dao.insertHindiTranslation(ExampleDataAndroidTest.hindi1)
        dao.insertEnglishTranslation(ExampleDataAndroidTest.english1)
        // When - favourite value of the inserted translation is updated
        val updatedTranslation = ExampleDataAndroidTest.hindi1.copy(favourite = true)
        dao.updateFavourite(updatedTranslation)
        // Then - favourite attribute of the inserted translation will be updated
        val translation = dao.getTranslation(ExampleDataAndroidTest.hindi1.word)
        assertThat(translation, `is`(notNullValue()))
        assertThat(translation!!.hindi, `is`(updatedTranslation))
        assertThat(translation.englishTranslations, `is`(listOf(ExampleDataAndroidTest.english1)))
    }

    @Test
    fun deleteHindiAndEnglishTranslations() = runBlockingTest {
        // Given - db containing some translations
        dao.insertEnglishTranslation(ExampleDataAndroidTest.english1)
        dao.insertHindiTranslation(ExampleDataAndroidTest.hindi1)
        // When - all hindi and english translations are deleted
        dao.deleteAllHindiTranslations()
        dao.deleteAllEnglishTranslations()
        // Then - db should be empty
        val translations = dao.getAllTranslations()
        assertThat(translations, `is`(emptyList()))
    }

}