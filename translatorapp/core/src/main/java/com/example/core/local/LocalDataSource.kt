package com.example.core.local

import com.example.core.Result
import com.example.core.TranslationDataSource
import com.example.core.data.entities.EnglishTranslation
import com.example.core.data.entities.HindiAndEnglishTranslation
import com.example.core.data.entities.HindiTranslation
import com.example.core.data.serializable.Hindi
import okio.IOException
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val translationDao: TranslationDao
) : TranslationDataSource {

    override suspend fun getTranslation(word: String): Result<HindiAndEnglishTranslation> {
        val translation = translationDao.getTranslation(word)
        return if (translation != null) {
            Result.Success(translation)
        } else {
            Result.Error(IOException("Translation not found in Db."))
        }
    }

    override suspend fun saveTranslation(translation: HindiAndEnglishTranslation) {
        translationDao.insertHindiTranslation(translation.hindi)

        translationDao.insertEnglishTranslations(translation.englishTranslations)
    }

    override suspend fun getAllTranslations(): Result<List<HindiAndEnglishTranslation>> {
        val translations = translationDao.getAllTranslations()
        return if (translations.isEmpty()) {
            Result.Error(IOException("There are no translations saved in Db."))
        } else {
            Result.Success(translations)
        }
    }

    override suspend fun deleteSavedTranslations() {
        translationDao.deleteAllEnglishTranslations()
        translationDao.deleteAllHindiTranslations()
    }

    override suspend fun updateFavourite(hindiTranslation: HindiTranslation) {
        translationDao.updateFavourite(hindiTranslation)
    }
}