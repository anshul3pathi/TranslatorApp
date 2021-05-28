package com.example.translatorapp.fakes

import com.example.core.AppTheme
import com.example.core.Result
import com.example.core.data.entities.HindiAndEnglishTranslation
import com.example.core.data.entities.HindiTranslation
import com.example.core.repository.TranslationRepo
import kotlin.contracts.contract

class FakeRepository(private var shouldFetchError: Boolean) : TranslationRepo {

    private val translationDb = mutableMapOf<String, HindiAndEnglishTranslation>()
    private var appTheme = 1

    override suspend fun getTranslation(word: String): Result<HindiAndEnglishTranslation> {
        return if (!shouldFetchError) {
            val translation = translationDb[word]
            if (translation == null) {
                Result.Error(Exception("translation not found"))
            } else {
                Result.Success(translation)
            }
        } else {
            Result.Error(Exception("shouldFetchError = true"))
        }
    }

    override suspend fun getAllTranslations(): Result<List<HindiAndEnglishTranslation>> {
        return if (!shouldFetchError) {
            Result.Success(translationDb.values.toList())
        } else {
            Result.Error(Exception("shouldFetchError = true"))
        }
    }

    override suspend fun deleteAllSavedTranslations() {
        translationDb.clear()
    }

    override suspend fun updateFavourite(hindiTranslation: HindiTranslation) {
        val translation = translationDb[hindiTranslation.word]!!
        val hindi = translation.hindi.copy(favourite = hindiTranslation.favourite)
        val english = translation.englishTranslations
        val newTranslation = HindiAndEnglishTranslation(hindi, english)
        translationDb[hindiTranslation.word] = newTranslation
    }

    override fun saveAppTheme(theme: AppTheme) {
        appTheme = if (theme == AppTheme.LIGHT) 1 else 2
    }

    override fun getAppTheme(): AppTheme {
        return if (appTheme == 1) AppTheme.LIGHT else AppTheme.DARK
    }

    fun setShouldFetchError(value: Boolean) {
        shouldFetchError = value
    }

    fun addData(translation: HindiAndEnglishTranslation) {
        val hindi = translation.hindi.copy()
        val english = translation.englishTranslations
        val toInsert = HindiAndEnglishTranslation(hindi, english)
        translationDb[translation.hindi.word] = toInsert
    }

}