package com.yunuscagliyan.core.data.local.preference

import android.content.SharedPreferences
import com.yunuscagliyan.core.data.local.preference.Preferences.Companion.THEME_KEY

class AppPreference(
    private val sharedPref: SharedPreferences
) : Preferences {
    override var themeIndex: Int
        get() = sharedPref.getInt(THEME_KEY, 0)
        set(value) {
            sharedPref.edit()
                .putInt(THEME_KEY, value)
                .apply()
        }
}