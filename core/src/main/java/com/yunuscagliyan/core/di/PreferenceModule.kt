package com.yunuscagliyan.core.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import com.yunuscagliyan.core.data.local.preference.AppPreference
import com.yunuscagliyan.core.data.local.preference.Preferences
import com.yunuscagliyan.core.util.Constant.PreferencesUtil.PREFERENCE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {

    @Provides
    @Singleton
    fun providePreference(
        application: Application
    ): Preferences {
        val sharedPref = application.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        return AppPreference(
            sharedPref = sharedPref
        )
    }
}