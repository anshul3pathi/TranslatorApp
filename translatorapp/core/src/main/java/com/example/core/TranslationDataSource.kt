package com.example.core

import com.example.core.data.entities.HindiAndEnglishTranslation
import com.example.core.data.entities.HindiTranslation


interface TranslationDataSource {

    suspend fun getTranslation(word: String): Result<HindiAndEnglishTranslation>
    suspend fun saveTranslation(translation: HindiAndEnglishTranslation)
    suspend fun getAllTranslations(): Result<List<HindiAndEnglishTranslation>>
    suspend fun deleteSavedTranslations()
    suspend fun updateFavourite(hindiTranslation: HindiTranslation)

}

