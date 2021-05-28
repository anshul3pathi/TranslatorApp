package com.example.core.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
object AndroidTestDatabaseModule {

    @Provides
    fun provideTranslationDao(database: TranslationDataBase): TranslationDao {
        return database.getTranslationDao()
    }

    @Provides
    @Singleton
    fun provideTranslationDatabase(@ApplicationContext context: Context): TranslationDataBase {
        return Room.inMemoryDatabaseBuilder(
            context,
            TranslationDataBase::class.java
        ).build()
    }

}