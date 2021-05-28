package com.example.translatorapp.ui.phrasebook

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core.Result
import com.example.core.succeeded
import com.example.core.testUtils.ExampleDataTest
import com.example.translatorapp.fakes.FakeRepository
import com.example.translatorapp.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [28])
class PhraseBookViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val fakeRepository = FakeRepository(false)
    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var viewModel: PhraseBookViewModel

    @Before
    fun init() {
        viewModel = PhraseBookViewModel(fakeRepository, testDispatcher)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun updateTranslations_updatesValueInTranslationsLiveData() = runBlockingTest {
        // Given - translations in database with {favourite = true}
        val translation1 = ExampleDataTest.hindiAndEnglish1.copy()
        val translation2 = ExampleDataTest.hindiAndEnglish2.copy()
        translation1.hindi.favourite = true
        translation2.hindi.favourite = true
        fakeRepository.addData(translation1)
        fakeRepository.addData(translation2)

        // When - updateTranslations() is called when viewModel is initialised
        viewModel.refreshTranslations()

        // Then - translations livedata should have the inserted translations
        assertThat(
            viewModel.translations.getOrAwaitValue(),
            `is`(listOf(translation1, translation2))
        )
    }

    @Test
    fun updateFavourite_updatesFavouriteAttribute() = runBlockingTest {
        // Given - an existing translation in db with favourite = false
        val translationToInsert = ExampleDataTest.hindiAndEnglish1.copy()
        translationToInsert.hindi.favourite = false
        fakeRepository.addData(translationToInsert)
        assertThat(translationToInsert.hindi.favourite, `is`(false))

        // When - updateFavourite is called given a translation
        val translationToUpdate = ExampleDataTest.hindiAndEnglish1.copy()
        translationToUpdate.hindi.favourite = true
        viewModel.updateFavourite(translationToUpdate.hindi)

        // Then - favourite attribute of the given translation in db should be updated
        val updatedTranslation = fakeRepository.getTranslation(translationToUpdate.hindi.word)
        assertThat(updatedTranslation.succeeded, `is`(true))
        updatedTranslation as Result.Success
        assertThat(updatedTranslation.data.hindi.favourite, `is`(true))
    }

}