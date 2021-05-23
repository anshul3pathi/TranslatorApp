package com.example.translatorapp.di
//
//import android.content.Context
//import androidx.room.Room
//import com.example.core.TranslationDataSource
//import com.example.core.local.LocalDataSource
//import com.example.core.local.TranslationDao
//import com.example.core.local.TranslationDataBase
//import com.example.core.remote.RemoteDataSource
//import com.example.core.remote.TranslationAPIService
//import com.example.core.repository.TranslationRepo
//import com.example.core.repository.TranslationRepository
//import com.squareup.moshi.Moshi
//import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
//import dagger.Binds
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import okhttp3.OkHttpClient
//import retrofit2.Retrofit
//import retrofit2.converter.moshi.MoshiConverterFactory
//import java.util.concurrent.TimeUnit
//import javax.inject.Qualifier
//import javax.inject.Singleton
//
//@Qualifier
//annotation class RemoteImplementation
//
//@Qualifier
//annotation class LocalImplementation
//
//@InstallIn(SingletonComponent::class)
//@Module
//abstract class AppModule {
//
//    @RemoteImplementation
//    @Singleton
//    @Binds
//    abstract fun bindRemoteDataSource(impl: RemoteDataSource): TranslationDataSource
//
//    @LocalImplementation
//    @Singleton
//    @Binds
//    abstract fun bindLocalDataSource(impl: LocalDataSource): TranslationDataSource
//
//    @Singleton
//    @Binds
//    abstract fun bindTranslationRepository(impl: TranslationRepository): TranslationRepo
//
//}
//
//@Module
//@InstallIn(SingletonComponent::class)
//object NetworkModule {
//
//    private const val baseUrl = "https://translator-api.herokuapp.com/"
//
//    @Singleton
//    @Provides
//    fun provideMoshi(): Moshi {
//        return Moshi.Builder()
//            .add(KotlinJsonAdapterFactory())
//            .build()
//    }
//
//    @Singleton
//    @Provides
//    fun provideOkhttpClient(): OkHttpClient {
//        return OkHttpClient.Builder()
//            .connectTimeout(60, TimeUnit.SECONDS)
//            .readTimeout(25, TimeUnit.SECONDS)
//            .build()
//    }
//
//    @Singleton
//    @Provides
//    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(this.baseUrl)
//            .client(okHttpClient)
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    fun translationApiService(retrofit: Retrofit): TranslationAPIService {
//        return retrofit.create(TranslationAPIService::class.java)
//    }
//
//}
//
//@InstallIn(SingletonComponent::class)
//@Module
//object DatabaseModule {
//
//    @Provides
//    fun provideTranslationDao(database: TranslationDataBase): TranslationDao {
//        return database.getTranslationDao()
//    }
//
//    @Provides
//    @Singleton
//    fun provideTranslationDatabase(@ApplicationContext context: Context): TranslationDataBase {
//        return Room.databaseBuilder(
//            context,
//            TranslationDataBase::class.java,
//            "translation.db"
//        ).build()
//    }
//
//}