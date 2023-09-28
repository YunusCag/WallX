package com.yunuscagliyan.core.di

import android.app.Application
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yunuscagliyan.core.BuildConfig
import com.yunuscagliyan.core.data.remote.interceptors.ClientIDInterceptor
import com.yunuscagliyan.core.data.remote.service.PixabayService
import com.yunuscagliyan.core.util.Constant.NetworkCacheUtil.CACHE_FILE_NAME
import com.yunuscagliyan.core.util.Constant.NetworkCacheUtil.MAX_SIZE
import com.yunuscagliyan.core.util.Constant.NetworkCacheUtil.TIME_OUT_SECONDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideConverterFactory(): MoshiConverterFactory = MoshiConverterFactory.create(
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    )

    @Provides
    @Singleton
    fun provideOkHttpClient(
        application: Application
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        val httpCacheDirectory = File(application.cacheDir, CACHE_FILE_NAME)
        val cache = Cache(httpCacheDirectory, MAX_SIZE)
        return OkHttpClient.Builder()
            .cache(cache)
            .addNetworkInterceptor(ClientIDInterceptor())
            .addNetworkInterceptor(loggingInterceptor)
            .callTimeout(TIME_OUT_SECONDS,TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        moshiConverterFactory: MoshiConverterFactory,
        okHttpClient: OkHttpClient
    ):Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideUnsplashService(
        retrofit: Retrofit
    ): PixabayService = retrofit.create()
}