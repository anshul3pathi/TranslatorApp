package com.example.core.storage

import android.content.Context
import androidx.core.content.edit
import com.example.core.AppTheme
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferenceStorage @Inject constructor(
    @ApplicationContext context: Context
) : Storage {

    companion object {
        private const val SHARED_PREFERENCE_KEY = "TRANSLATOR_SHARED_PREFERENCE"
        private const val THEME_KEY = "APP_THEME"
    }

    private val sharedPref = context.getSharedPreferences(
        SHARED_PREFERENCE_KEY,
        Context.MODE_PRIVATE
    )

    override fun setTheme(int: Int) {
        sharedPref.edit {
            putInt(THEME_KEY, int)
        }
    }

    override fun getTheme(): Int {
        return sharedPref.getInt(THEME_KEY, 1)
    }
}