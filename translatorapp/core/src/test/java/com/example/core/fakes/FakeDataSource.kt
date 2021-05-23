package com.example.core.fakes

import com.example.core.Result
import com.example.core.TranslationDataSource
import com.example.core.data.entities.HindiAndEnglishTranslation
import com.example.core.data.entities.HindiTranslation
import com.example.core.testUtils.ExampleDataTest
import okio.IOException

class FakeDataSource(
    private var shouldReturnError: Boolean = false,
    private var shouldActAsRemote:Boolean
) : TranslationDataSource {

    private val translationsDb = mutableMapOf(
        "hot" to ExampleDataTest.hindiAndEnglish1,
        "brave" to ExampleDataTest.hindiAndEnglish2
    )

    override suspend fun getTranslation(word: String): Result<HindiAndEnglishTranslation> {
        if (!shouldActAsRemote) {
            return if (shouldReturnError) {
                Result.Error(IOException("fetch error as remote"))
            } else {
                return Result.Success(translationsDb[word]!!)
            }
        } else {
            return if (!shouldReturnError) {
                return Result.Success(translationsDb[word]!!)
            } else {
                Result.Error(IOException("return error as local"))
            }
        }
    }

    override suspend fun saveTranslation(translation: HindiAndEnglishTranslation) {
        if (!shouldActAsRemote) {
            translationsDb[translation.hindi.word] = translation
        }
    }

    override suspend fun getAllTranslations(): Result<List<HindiAndEnglishTranslation>> {
        return if (!shouldActAsRemote) {
            return Result.Success(translationsDb.values.toList())
        } else {
            Result.Error(Exception("not implemented for remote."))
        }
    }

    override suspend fun deleteSavedTranslations() {
        if (!shouldActAsRemote) {
            translationsDb.clear()
        }
    }

    override suspend fun updateFavourite(hindiTranslation: HindiTranslation) {
        if (!shouldActAsRemote) {
            val translationToUpdate = translationsDb[hindiTranslation.word]!!.copy()
            translationToUpdate.hindi.favourite = hindiTranslation.favourite
            translationsDb[hindiTranslation.word] = translationToUpdate
        }
    }

    fun setShouldFetchError(value: Boolean) {
        shouldReturnError = value
    }

    fun isDbEmpty() = translationsDb.isEmpty()

}