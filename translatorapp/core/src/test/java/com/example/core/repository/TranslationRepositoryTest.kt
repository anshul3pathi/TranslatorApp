package com.example.core.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.Result
import com.example.core.fakes.FakeDataSource
import com.example.core.fakes.FakeSharedPreferencesStorage
import com.example.core.succeeded
import com.example.core.testUtils.ExampleDataTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class TranslationRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var localDataSource: FakeDataSource
    private lateinit var remoteDataSource: FakeDataSource
    private lateinit var repository: TranslationRepository
    private val sharedPref = FakeSharedPreferencesStorage()


    @Before
    fun init() {
        localDataSource = FakeDataSource(shouldReturnError = false, shouldActAsRemote = false)
        remoteDataSource = FakeDataSource(shouldActAsRemote = true, shouldReturnError = false)
        repository = TranslationRepository(remoteDataSource, localDataSource, sharedPref)
    }

    @Test
    fun deleteAllTranslations_deleteExistingTranslations() = runBlockingTest {
        localDataSource.saveTranslation(ExampleDataTest.hindiAndEnglish1)
        localDataSource.saveTranslation(ExampleDataTest.hindiAndEnglish2)
        repository.deleteAllSavedTranslations()
        assertThat(localDataSource.isDbEmpty(), `is`(true))
    }

    @Test
    fun getTranslation_returnsTranslation_And_savesInDb() = runBlockingTest {
        // Given - localDb does not contain translation
        localDataSource.deleteSavedTranslations()
        remoteDataSource.deleteSavedTranslations()
        remoteDataSource.saveTranslation(ExampleDataTest.hindiAndEnglish1)
        localDataSource.setShouldFetchError(true)

        // When - repository is asked for the translation
        val translation = repository.getTranslation(ExampleDataTest.hindiAndEnglish1.hindi.word)

        // Then - it should fetch the translation from remote...
        assertThat(translation.succeeded, `is`(true))
        translation as Result.Success
        assertThat(translation.data, `is`(ExampleDataTest.hindiAndEnglish1))

        // Then - it should save the new translation in local database
        localDataSource.setShouldFetchError(false)
        val savedTranslation = localDataSource.getTranslation(ExampleDataTest.hindi1.word)
        assertThat(savedTranslation.succeeded, `is`(true))
        savedTranslation as Result.Success
        assertThat(
            savedTranslation.data,
            `is`(ExampleDataTest.hindiAndEnglish1)
        )
    }

    @Test
    fun getTranslation_remoteAndLocalFetchError_returnsError() = runBlockingTest {
        // Given - remote and local data sources fetch error
        remoteDataSource.setShouldFetchError(true)
        localDataSource.setShouldFetchError(true)
        // When - getTranslation()
        val translation = repository.getTranslation(ExampleDataTest.hindiAndEnglish1.hindi.word)
        // Then - it should return Error
        assertThat(translation.succeeded, `is`(false))
    }

    @Test
    fun getAllTranslations_returnListOfAllTranslations() = runBlockingTest {
        // Given - translations in db
        localDataSource.saveTranslation(ExampleDataTest.hindiAndEnglish1)
        localDataSource.saveTranslation(ExampleDataTest.hindiAndEnglish2)

        // When - getAllTranslations()
        val translations = repository.getAllTranslations()
        // Then - it should return list of all saved translations
        assertThat(translations.succeeded, `is`(true))
        translations as Result.Success
        assertThat(
            translations.data,
            `is`(listOf(ExampleDataTest.hindiAndEnglish1, ExampleDataTest.hindiAndEnglish2))
        )
    }

    @Test
    fun updateFavourite_updatesFavouriteAttributeOfGivenTranslation() = runBlockingTest {
        // Given - translation saved in database
        localDataSource.saveTranslation(ExampleDataTest.hindiAndEnglish1)
        // When - updateTranslation() is given a translation to update
        assertThat(ExampleDataTest.hindiAndEnglish1.hindi.favourite, `is`(false))
        val translationToUpdate = ExampleDataTest.hindi1.copy(favourite = true)
        repository.updateFavourite(translationToUpdate)
        // Then - saved translations should be updated
        val updatedTranslation =
            localDataSource.getTranslation(ExampleDataTest.hindiAndEnglish1.hindi.word)
        assertThat(updatedTranslation.succeeded, `is`(true))
        updatedTranslation as Result.Success
        assertThat(updatedTranslation.data.hindi.favourite, `is`(true))
    }

}