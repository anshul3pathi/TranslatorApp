package com.example.core

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.core.data.entities.HindiAndEnglishTranslation
import com.example.core.local.TranslationDataBase
import com.example.core.testUtils.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TranslationDaoTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: TranslationDataBase

    @Before
    fun init() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TranslationDataBase::class.java
        ).build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun getTranslation_givenWord_returnHindiAndEnglishTranslation() = runBlockingTest {
        //  Given - insert hindi and corresponding english translations
        database.getTranslationDao().insertHindiTranslation(hindi1)
        database.getTranslationDao().insertEnglishTranslation(english1)

        //  When - Get the translation by word form the database
        val loaded = database.getTranslationDao().getTranslation(hindi1.word)

        //  Then - The loaded data contains the expected value
        assertThat(loaded as HindiAndEnglishTranslation, CoreMatchers.notNullValue())
        assertThat(loaded.hindi.word, `is`(hindi1.word))
        assertThat(loaded.hindi.hindiTranslation, `is`(hindi1.hindiTranslation))
        assertThat(loaded.englishTranslations[0].translations[0], `is`(english1.translations[0]))
        assertThat(loaded.englishTranslations[0].examples[0], `is`(english1.examples[0]))
        assertThat(loaded.englishTranslations[0].parentKey, `is`(english1.parentKey))
        assertThat(loaded.englishTranslations[0].usage, `is`(english1.usage))
//        assert(containsInAnyOrder(loaded.englishTranslations, english22))

    }

    @Test
    fun deleteAllHindiTranslations() = runBlockingTest {
        //  Given - insert a new hindi and corresponding english translation
        database.getTranslationDao().insertHindiTranslation(hindi2)
        database.getTranslationDao().insertEnglishTranslation(english22)
        database.getTranslationDao().insertEnglishTranslation(english32)
        database.getTranslationDao().insertEnglishTranslation(english42)

        //  When - all english translations in database are deleted
        database.getTranslationDao().deleteAllEnglishTranslations()
        val loaded = database.getTranslationDao().getTranslation(hindi2.word)

        //  Then - the loaded data is emptyList
        assertThat(loaded as HindiAndEnglishTranslation, CoreMatchers.notNullValue())
        assertThat(loaded.englishTranslations, `is`(emptyList()))
    }

    @Test
    fun getAllTranslations() = runBlockingTest {
        //  Given - inserting a few hindi and corresponding english translations
        database.getTranslationDao().insertHindiTranslation(hindi1)
        database.getTranslationDao().insertEnglishTranslation(english1)
        database.getTranslationDao().insertHindiTranslation(hindi2)
        database.getTranslationDao().insertEnglishTranslation(english22)
        database.getTranslationDao().insertEnglishTranslation(english32)
        database.getTranslationDao().insertEnglishTranslation(english42)

        //  When - all translations in database are fetched
        val translations = database.getTranslationDao().getAllTranslations()

        //  Then - the loaded translations contain the expected values
//        assertThat(translations, `is`(listOf(hindiAndEnglish1, hindiAndEnglish2)))
        assertThat(translations[0].englishTranslations[0].translations.size, `is`(english1.translations.size))
        assertThat(translations[0], `is`(hindiAndEnglish1))
        assertThat(translations[1].englishTranslations.size, `is`(hindiAndEnglish2.englishTranslations.size))
        assertThat(translations[1], `is`(hindiAndEnglish2))

    }

}