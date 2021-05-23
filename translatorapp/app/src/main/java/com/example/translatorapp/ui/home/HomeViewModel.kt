package com.example.translatorapp.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.Result
import com.example.core.data.entities.HindiAndEnglishTranslation
import com.example.core.data.entities.HindiTranslation
import com.example.core.repository.TranslationRepo
import com.example.core.succeeded
import com.example.translatorapp.R
import com.example.translatorapp.utils.Constants
import com.example.translatorapp.utils.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val applicationContext: Application,
    private val repository: TranslationRepo
) : ViewModel() {

    private var gettingTranslationJob: Job? = null

    private val _translations = MutableLiveData<List<HindiAndEnglishTranslation>>()
    val translations: LiveData<List<HindiAndEnglishTranslation>>
        get() = _translations

    private val _translation = MutableLiveData(Constants.EMPTY_TRANSLATION)
    val translation: LiveData<HindiAndEnglishTranslation>
        get() = _translation

    private val _networkStatus = MutableLiveData(NetworkStatus.IDLE)
    val networkStatus: LiveData<NetworkStatus>
        get() = _networkStatus

    private val _snackBarMessage = MutableLiveData<String>()
    val snackBarMessage: LiveData<String>
        get() = _snackBarMessage

    init {
        getTranslations()
    }

    private fun updateTranslations() {
        getTranslations()
    }

    fun getTranslation(word: String) {
        _networkStatus.value = NetworkStatus.LOADING
        gettingTranslationJob = viewModelScope.launch {
            val translation = repository.getTranslation(word)
            if (translation.succeeded) {
                withContext(Dispatchers.Main) {
                    translation as Result.Success
                    _translation.value = translation.data
                    _networkStatus.value = NetworkStatus.SUCCESS
                }
            } else {
                translation as Result.Error
                if (translation.exception.message != "StandaloneCoroutine was cancelled") {
                    _snackBarMessage.value =
                        applicationContext.getString(R.string.network_error_message)
                    _networkStatus.value = NetworkStatus.ERROR
                }
            }
            if (_networkStatus.value == NetworkStatus.SUCCESS) {
                updateTranslations()
            }
        }
    }

    private fun getTranslations() {
        viewModelScope.launch {
            val translations = repository.getAllTranslations()
            if (translations.succeeded) {
                translations as Result.Success
                _translations.value = translations.data.reversed()
            }
        }
    }

    fun updateFavouriteFromRecentWords(hindiTranslation: HindiTranslation) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavourite(hindiTranslation)
        }
    }

    fun updateFavouriteFromTranslationContainer() {
        val currentTranslation = _translation.value!!
        currentTranslation.hindi.favourite = !currentTranslation.hindi.favourite
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavourite(currentTranslation.hindi)
            withContext(Dispatchers.Main) {
                _translation.value = currentTranslation
                updateTranslations()
            }
        }
    }

    fun cancelGettingTranslation() {
        gettingTranslationJob?.cancel()
    }

    fun typingInSearchBox() {
        _networkStatus.value = NetworkStatus.LOADING
    }

    fun searchBoxEmpty() {
        gettingTranslationJob?.cancel()
        _networkStatus.value = NetworkStatus.IDLE
        _translation.value = Constants.EMPTY_TRANSLATION
    }

}