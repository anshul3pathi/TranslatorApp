package com.example.translatorapp.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.AppTheme
import com.example.core.repository.TranslationRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: TranslationRepo,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _appTheme = MutableLiveData(getTheme())
    val appTheme: LiveData<AppTheme>
        get() = _appTheme

    fun clearAllTranslations() {
        viewModelScope.launch(dispatcher) {
            repository.deleteAllSavedTranslations()
        }
    }

    private fun getTheme() = repository.getAppTheme()

    fun changeTheme(theme: AppTheme) {
        repository.saveAppTheme(theme)
        _appTheme.value = theme
    }

}