package com.example.core.local

import androidx.room.*
import com.example.core.data.entities.EnglishTranslation
import com.example.core.data.entities.HindiAndEnglishTranslation
import com.example.core.data.entities.HindiTranslation

@Dao
interface TranslationDao {


    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertHindiTranslation(hindiTranslation: HindiTranslation)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertEnglishTranslation(englishTranslation: EnglishTranslation)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertEnglishTranslations(englishTranslations: List<EnglishTranslation>)

    @Update
    suspend fun updateFavourite(hindiTranslation: HindiTranslation)

    @Transaction
    @Query("select * from hindi_translations")
    suspend fun getAllTranslations(): List<HindiAndEnglishTranslation>

    @Transaction
    @Query("select * from hindi_translations where word = :word")
    suspend fun getTranslation(word: String): HindiAndEnglishTranslation?


    @Query("delete from english_translations")
    suspend fun deleteAllEnglishTranslations()


    @Query("delete from hindi_translations")
    suspend fun deleteAllHindiTranslations()

}