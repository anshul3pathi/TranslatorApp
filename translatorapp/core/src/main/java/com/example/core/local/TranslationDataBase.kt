package com.example.core.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.core.data.entities.EnglishTranslation
import com.example.core.data.entities.HindiTranslation

@Database(
    entities = [HindiTranslation::class, EnglishTranslation::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TranslationDataBase : RoomDatabase() {

    abstract fun getTranslationDao(): TranslationDao

}