package com.yunuscagliyan.core.util

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import timber.log.Timber

object JsonConverter {
    private val moshi: Moshi by lazy {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    fun <T> convertFromJsonString(data: String, type: Class<T>): T? {
        return try {
            val jsonAdapter = moshi.adapter<T>(type)
            jsonAdapter.fromJson(data)
        } catch (e: Exception) {
            Timber.e("ConvertFromJsonString:${e.localizedMessage}")
            null
        }
    }

    fun <T> convertToJson(data: T, type: Class<T>): String? {
        return try {
            val jsonAdapter = moshi.adapter(type)
            jsonAdapter.toJson(data)
        } catch (e: Exception) {
            Timber.e("ConvertToJson:${e.localizedMessage}")
            null
        }
    }
}