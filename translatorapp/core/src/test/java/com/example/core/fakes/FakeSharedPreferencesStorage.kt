package com.example.core.fakes

import com.example.core.storage.Storage

class FakeSharedPreferencesStorage : Storage {

    private var themeValue: Int = 0

    override fun setTheme(int: Int) {
        themeValue = int
    }

    override fun getTheme(): Int {
        return themeValue
    }

}