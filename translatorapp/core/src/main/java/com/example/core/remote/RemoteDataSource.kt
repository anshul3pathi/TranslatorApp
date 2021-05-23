package com.example.core.remote

import com.example.core.TranslationDataSource
import com.example.core.data.entities.HindiAndEnglishTranslation
import javax.inject.Inject
import com.example.core.Result
import com.example.core.data.entities.EnglishTranslation
import com.example.core.data.entities.HindiTranslation
import com.example.core.data.serializable.Hindi
import com.example.core.succeeded

class RemoteDataSource @Inject constructor(
    private val translationService: TranslationAPIService
) : TranslationDataSource {

    override suspend fun getTranslation(word: String): Result<HindiAndEnglishTranslation> {
        val translationResult = getTranslationFromRemote(word)
        if (translationResult.succeeded) {
            translationResult as Result.Success
            val translation = translationResult.data
            val hindiTranslation = HindiTranslation(
                word = word,
                hindiTranslation = translation.hindiTranslation
            )
            val englishTranslations = mutableListOf<EnglishTranslation>()
            for (item in translation.english) {
                englishTranslations.add(
                    EnglishTranslation(
                        translations = item.englishTranslations,
                        examples = item.englishExamples,
                        usage = item.usage,
                        parentKey = word
                    )
                )
            }
            val hindiAndEnglishTranslation = HindiAndEnglishTranslation(
                hindi = hindiTranslation,
                englishTranslations = englishTranslations
            )
            return Result.Success(hindiAndEnglishTranslation)
        } else {
            translationResult as Result.Error
            return Result.Error(translationResult.exception)
        }
    }

    override suspend fun saveTranslation(translation: HindiAndEnglishTranslation) {
        //  No need to implement here
    }

    override suspend fun getAllTranslations(): Result<List<HindiAndEnglishTranslation>> {
        return Result.Error(Exception("This method is not implemented."))
    }

    override suspend fun deleteSavedTranslations() {
        // No need to implement
    }

    override suspend fun updateFavourite(hindiTranslation: HindiTranslation) {
        // No need to implement
    }

    private suspend fun getTranslationFromRemote(word: String): Result<Hindi> {
        return try {
            val translation = translationService.getTranslation(word)
            Result.Success(translation)
        } catch (ex: Exception) {
            Result.Error(ex)
        }
    }

}