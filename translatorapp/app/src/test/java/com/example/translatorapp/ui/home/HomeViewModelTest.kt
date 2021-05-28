package com.example.translatorapp.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core.Result
import com.example.core.data.entities.HindiAndEnglishTranslation
import com.example.core.succeeded
import com.example.core.testUtils.ExampleDataTest
import com.example.translatorapp.R
import com.example.translatorapp.TranslationApplication
import com.example.translatorapp.fakes.FakeRepository
import com.example.translatorapp.getOrAwaitValue
import com.example.translatorapp.utils.Constants
import com.example.translatorapp.utils.NetworkStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.contains
import org.hamcrest.Matchers.containsInAnyOrder
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [28])
class HomeViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val fakeRepository = FakeRepository(false)
    private lateinit var viewModel: HomeViewModel
    private val context = getApplicationContext<TranslationApplication>()

    @Before
    fun init() {
        viewModel = HomeViewModel(context, fakeRepository)
    }

    @Test
    fun getTranslation_assignsErrorToNetworkStatus() = runBlockingTest {
        // Given - repository returns Error
        fakeRepository.setShouldFetchError(true)

        // When - translation is retrieved
        viewModel.getTranslation("word")

        // Then - networkStatus livedata should have value NetworkStatus.ERROR and snackBarMessage
        // should be set to appropriate message
        assertThat(viewModel.networkStatus.getOrAwaitValue(),  `is`(NetworkStatus.ERROR))
        assertThat(
            viewModel.snackBarMessage.getOrAwaitValue(),
            `is`(context.resources.getString(R.string.network_error_message))
        )
    }

    @Test
    fun getTranslation_assignsSuccessTo_networkStatus_andUpdates_translation() = runBlockingTest {
        // Given - repository returns Success
        fakeRepository.setShouldFetchError(false)
        fakeRepository.addData(ExampleDataTest.hindiAndEnglish1)

        // When - translation is retrieved
        viewModel.getTranslation(ExampleDataTest.hindiAndEnglish1.hindi.word)

        // Then - networkStatus livedata should have value NetworkStatus.SUCCESS
        assertThat(viewModel.networkStatus.getOrAwaitValue(), `is`(NetworkStatus.SUCCESS))
        assertThat(viewModel.translation.getOrAwaitValue(), `is`(ExampleDataTest.hindiAndEnglish1))
    }

    @Test
    fun getTranslations_updates_translations() = runBlockingTest {
        // Given - repository has a bunch of translations saved
        fakeRepository.deleteAllSavedTranslations()
        fakeRepository.addData(ExampleDataTest.hindiAndEnglish1)
        fakeRepository.addData(ExampleDataTest.hindiAndEnglish2)
        val inserted = listOf(ExampleDataTest.hindiAndEnglish1, ExampleDataTest.hindiAndEnglish2)

        // When - translations are retrieved
        viewModel.updateTranslations()

        // Then - translations livedata should contain list of inserted translations
        assertThat(viewModel.translations.getOrAwaitValue(), `is`(inserted.reversed()))
    }

    @Test
    fun updateFavouriteFromRecentWords() = runBlockingTest {
        // Given - translation to be updated
        val translationToUpdate = ExampleDataTest.hindiAndEnglish1.copy()
        fakeRepository.addData(translationToUpdate)
        translationToUpdate.hindi.favourite = true

        // When - this function is called given the translation
        viewModel.updateFavouriteFromRecentWords(translationToUpdate.hindi)

        // Then - the translation in database should be updated
        val updatedTranslation = fakeRepository.getTranslation(translationToUpdate.hindi.word)
        assertThat(updatedTranslation.succeeded, `is`(true))
        updatedTranslation as Result.Success
        assertThat(
            updatedTranslation.data,
            `is`(translationToUpdate)
        )
    }

    @Test
    fun updateFavouriteFromTranslationContainer() = runBlockingTest {
        // Given - translation in translation livedata
        fakeRepository.deleteAllSavedTranslations()
        val englishTranslation = ExampleDataTest.english1.copy()
        val hindiTranslation = ExampleDataTest.hindi1.copy()
        val inserted = HindiAndEnglishTranslation(hindiTranslation, listOf(englishTranslation))
        fakeRepository.addData(inserted)

        // When - this function is called
        viewModel.getTranslation(hindiTranslation.word)
        viewModel.updateFavouriteFromTranslationContainer()

        // Then - it should updated tha favourite attribute in database and update the values of
        // translation and translations livedata
        val updatedTranslation = fakeRepository.getTranslation(hindiTranslation.word)
        assertThat(updatedTranslation.succeeded, `is`(true))
        updatedTranslation as Result.Success
        assertThat(updatedTranslation.data.hindi.favourite, `is`(!hindiTranslation.favourite))
        assertThat(
            viewModel.translation.getOrAwaitValue().hindi.favourite,
            `is`(updatedTranslation.data.hindi.favourite)
        )
        assertThat(viewModel.translations.getOrAwaitValue(), `is`(listOf(updatedTranslation.data)))
    }

    @Test
    fun typingInSearchBox_sets_networkStatusToLoading() {
        // When - this method is invoked
        viewModel.typingInSearchBox()

        // Then - networkStatus should change to LOADING
        assertThat(viewModel.networkStatus.getOrAwaitValue(), `is`(NetworkStatus.LOADING))
    }

    @Test
    fun searchBoxEmpty_sets_networkStatusTIdle() {
        // When - this method is invoked
        viewModel.searchBoxEmpty()

        // Then - networksStatus should change to IDLE and
        // translation should change to emptyValue
        assertThat(viewModel.networkStatus.getOrAwaitValue(), `is`(NetworkStatus.IDLE))
        assertThat(viewModel.translation.getOrAwaitValue(), `is`(Constants.EMPTY_TRANSLATION))
    }

}