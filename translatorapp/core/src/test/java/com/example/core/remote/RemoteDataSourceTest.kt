package com.example.core.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.core.Result
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
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.robolectric.annotation.Config

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
@Config(sdk = [29])
class RemoteDataSourceTest {

    @Mock
    private lateinit var mockedApiService: TranslationAPIService
    private lateinit var remoteDataSource: RemoteDataSource
    private val word = "hot"

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        remoteDataSource = RemoteDataSource(mockedApiService)
    }

    @Test
    fun getTranslation_givenWord_fetchesSuccess() = runBlockingTest {
        `when`(mockedApiService.getTranslation(word = word))
            .thenReturn(ExampleDataTest.hindiHindi1)

        val translation = remoteDataSource.getTranslation(word)
        assertThat(translation.succeeded, `is`(true))
        translation as Result.Success
        assertThat(
            translation.data.hindi.favourite,
            `is`(ExampleDataTest.hindiAndEnglish1.hindi.favourite)
        )
        assertThat(
            translation.data.hindi.word,
            `is`(ExampleDataTest.hindiAndEnglish1.hindi.word)
        )
        assertThat(
            translation.data.hindi.hindiTranslation,
            `is`(ExampleDataTest.hindiAndEnglish1.hindi.hindiTranslation)
        )
        assertThat(
            translation.data.englishTranslations[0].translations,
            `is`(ExampleDataTest.hindiAndEnglish1.englishTranslations[0].translations)
        )
        assertThat(
            translation.data.englishTranslations[0].examples,
            `is`(ExampleDataTest.hindiAndEnglish1.englishTranslations[0].examples)
        )
        assertThat(
            translation.data.englishTranslations[0].usage,
            `is`(ExampleDataTest.hindiAndEnglish1.englishTranslations[0].usage)
        )
    }

}