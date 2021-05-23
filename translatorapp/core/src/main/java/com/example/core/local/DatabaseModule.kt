package com.example.core.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideTranslationDao(database: TranslationDataBase): TranslationDao {
        return database.getTranslationDao()
    }

    @Provides
    @Singleton
    fun provideTranslationDatabase(@ApplicationContext context: Context): TranslationDataBase {
        return Room.databaseBuilder(
            context,
            TranslationDataBase::class.java,
            "translation.db"
        ).build()
    }

}