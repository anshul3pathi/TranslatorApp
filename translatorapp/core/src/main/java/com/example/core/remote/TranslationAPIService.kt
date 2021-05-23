package com.example.core.remote

import com.example.core.data.serializable.Hindi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface TranslationAPIService {

    @GET("translate/{word}")
    suspend fun getTranslation(@Path("word") word: String): Hindi

}