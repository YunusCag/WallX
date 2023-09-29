package com.yunuscagliyan.core.data.local.preference

import android.content.SharedPreferences
import com.yunuscagliyan.core.data.local.preference.Preferences.Companion.AUTO_CHANGE_KEY
import com.yunuscagliyan.core.data.local.preference.Preferences.Companion.FEATURE_COUNTER_KEY
import com.yunuscagliyan.core.data.local.preference.Preferences.Companion.IS_APP_RATED
import com.yunuscagliyan.core.data.local.preference.Preferences.Companion.PERIOD_INDEX_KEY
import com.yunuscagliyan.core.data.local.preference.Preferences.Companion.SCREEN_INDEX_KEY
import com.yunuscagliyan.core.data.local.preference.Preferences.Companion.SOURCE_INDEX_KEY
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
    override var autoChange: Boolean
        get() = sharedPref.getBoolean(AUTO_CHANGE_KEY, false)
        set(value) {
            sharedPref.edit()
                .putBoolean(AUTO_CHANGE_KEY, value)
                .apply()
        }
    override var periodIndex: Int
        get() = sharedPref.getInt(PERIOD_INDEX_KEY, 0)
        set(value) {
            sharedPref.edit()
                .putInt(PERIOD_INDEX_KEY, value)
                .apply()
        }
    override var sourceIndex: Int
        get() = sharedPref.getInt(SOURCE_INDEX_KEY, 0)
        set(value) {
            sharedPref.edit()
                .putInt(SOURCE_INDEX_KEY, value)
                .apply()
        }
    override var screenIndex: Int
        get() = sharedPref.getInt(SCREEN_INDEX_KEY, 0)
        set(value) {
            sharedPref.edit()
                .putInt(SCREEN_INDEX_KEY, value)
                .apply()
        }
    override var isAppRated: Boolean
        get() = sharedPref.getBoolean(IS_APP_RATED, false)
        set(value) {
            sharedPref.edit()
                .putBoolean(IS_APP_RATED, value)
                .apply()
        }
    override var featureCounter: Int
        get() = sharedPref.getInt(FEATURE_COUNTER_KEY, 0)
        set(value) {
            sharedPref.edit()
                .putInt(FEATURE_COUNTER_KEY, value)
                .apply()
        }
}