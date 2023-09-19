package com.yunuscagliyan.core.di

import android.app.Application
import androidx.room.Room
import com.yunuscagliyan.core.data.local.dao.PhotoDao
import com.yunuscagliyan.core.data.local.db.PhotoDatabase
import com.yunuscagliyan.core.util.Constant.DBUtil.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object RoomModule {
    @Singleton
    @Provides
    fun providePhotoDatabase(app: Application): PhotoDatabase {
        return Room.databaseBuilder(
            context = app,
            klass = PhotoDatabase::class.java,
            name = DB_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun providePhotoDao(photoDatabase: PhotoDatabase): PhotoDao = photoDatabase.dao
}