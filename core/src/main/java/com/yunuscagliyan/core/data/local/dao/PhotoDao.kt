package com.yunuscagliyan.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yunuscagliyan.core.data.local.entity.PhotoEntity

@Dao
interface PhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photoEntity: PhotoEntity)

    @Query("SELECT * FROM photo_entity")
    fun getPhotos(): List<PhotoEntity>

    @Query("SELECT * FROM photo_entity WHERE photoId=:photoId")
    fun getPhotoById(photoId: Long): PhotoEntity?

    @Query("DELETE FROM photo_entity WHERE photoId =:photoId")
    suspend fun deletePhoto(photoId: Long)
}