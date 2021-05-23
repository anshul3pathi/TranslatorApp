package com.example.core.repository

import com.example.core.AppTheme
import com.example.core.data.entities.EnglishTranslation
import com.example.core.data.entities.HindiAndEnglishTranslation
import com.example.core.data.entities.HindiTranslation
import com.example.core.Result

interface TranslationRepo {

    suspend fun getTranslation(word: String): Result<HindiAndEnglishTranslation>
    suspend fun getAllTranslations(): Result<List<HindiAndEnglishTranslation>>
    suspend fun deleteAllSavedTranslations()
    suspend fun updateFavourite(hindiTranslation: HindiTranslation)
    fun saveAppTheme(theme: AppTheme)
    fun getAppTheme(): AppTheme

}