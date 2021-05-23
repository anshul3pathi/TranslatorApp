package com.example.core.remote

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.core.TranslationDataSource
import com.example.core.testUtils.ExampleDataAndroidTest
import com.example.core.testUtils.hindiAndEnglish1
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.OkHttpClient
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class RemoteDataSourceAndroidTest {

//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var remoteDataSource: TranslationDataSource
    private lateinit var apiService: TranslationAPIService

    @Before
    fun init() {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()


        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(25, TimeUnit.SECONDS)
            .build()


        val retrofit = Retrofit.Builder()
            .baseUrl("https://translator-api.herokuapp.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()

        apiService = retrofit.create(TranslationAPIService::class.java)
        remoteDataSource = RemoteDataSource(apiService)
    }

    @Test
    fun getTranslation_givenString_returnsHindiAndEnglishTranslation() = runBlockingTest {
        //  Given - the word to be searched
        val word = "hot"

        //  When - translation of the word is asked for
        val translation = remoteDataSource.getTranslation(word)

        //  Then - it should return the appropriate translation
        assertThat(translation, `is`(ExampleDataAndroidTest.hindiAndEnglish1))
    }

}