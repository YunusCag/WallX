package com.yunuscagliyan.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yunuscagliyan.core.data.local.dao.PhotoDao
import com.yunuscagliyan.core.data.local.entity.PhotoEntity
import com.yunuscagliyan.core.util.Constant.DBUtil.DB_VERSION


@Database(
    entities = [PhotoEntity::class],
    exportSchema = false,
    version = DB_VERSION,
)
abstract class PhotoDatabase : RoomDatabase() {
    abstract val dao: PhotoDao
}