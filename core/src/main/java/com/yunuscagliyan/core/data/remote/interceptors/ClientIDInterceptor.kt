package com.yunuscagliyan.core.data.remote.interceptors

import com.yunuscagliyan.core.BuildConfig
import com.yunuscagliyan.core.util.Constant.NetworkCacheUtil.CACHE_HEADER_KEY
import com.yunuscagliyan.core.util.Constant.NetworkCacheUtil.MAX_AGE_DAY
import com.yunuscagliyan.core.util.Constant.NetworkQueryParamKey.CLIENT_ID
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class ClientIDInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter(CLIENT_ID,BuildConfig.API_KEY)
            .build()

        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()

        val response = chain.proceed(request)
        val cacheControl = CacheControl.Builder()
            .maxAge(MAX_AGE_DAY, TimeUnit.DAYS)
            .build()
        return response.newBuilder()
            .header(CACHE_HEADER_KEY,cacheControl.toString())
            .removeHeader("Pragma")
            .build()
    }
}