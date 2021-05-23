package com.example.core.repository

import com.example.core.AppTheme
import com.example.core.TranslationDataSource
import com.example.core.succeeded
import com.example.core.Result
import com.example.core.data.entities.HindiAndEnglishTranslation
import com.example.core.data.entities.HindiTranslation
import com.example.core.storage.Storage
import javax.inject.Inject

class TranslationRepository @Inject constructor(
    @RemoteImplementation val remoteDataSource: TranslationDataSource,
    @LocalImplementation val localDataSource: TranslationDataSource,
    private val storage: Storage
) : TranslationRepo {

    override suspend fun getAllTranslations(): Result<List<HindiAndEnglishTranslation>> {
        return localDataSource.getAllTranslations()
    }

    override suspend fun deleteAllSavedTranslations() {
        localDataSource.deleteSavedTranslations()
    }

    override suspend fun updateFavourite(hindiTranslation: HindiTranslation) {
        localDataSource.updateFavourite(hindiTranslation)
    }

    override suspend fun getTranslation(word: String): Result<HindiAndEnglishTranslation> {
        val translation = getTranslationFromDb(word)
        return if (translation.succeeded) {
            translation
        } else {
            val newTranslation = getTranslationFromRemote(word)
            if (newTranslation.succeeded) {
                newTranslation as Result.Success
                saveTranslation(newTranslation.data)
            }
            newTranslation
        }
    }

    private suspend fun getTranslationFromDb(word: String): Result<HindiAndEnglishTranslation> {
        return localDataSource.getTranslation(word)
    }

    private suspend fun getTranslationFromRemote(word: String): Result<HindiAndEnglishTranslation> {
        return remoteDataSource.getTranslation(word)
    }

    private suspend fun saveTranslation(translation: HindiAndEnglishTranslation) {
        localDataSource.saveTranslation(translation)
    }

    override fun saveAppTheme(theme: AppTheme) {
        val themeInt = if (theme == AppTheme.LIGHT) 1 else 2
        storage.setTheme(themeInt)
    }

    override fun getAppTheme(): AppTheme {
        val themeInt = storage.getTheme()
        return if (themeInt == 1) AppTheme.LIGHT else AppTheme.DARK
    }

}