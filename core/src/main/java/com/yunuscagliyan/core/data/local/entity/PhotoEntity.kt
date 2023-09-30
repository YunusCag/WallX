package com.yunuscagliyan.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.yunuscagliyan.core.data.remote.model.link.Links
import com.yunuscagliyan.core.data.remote.model.url.Urls
import com.yunuscagliyan.core.data.remote.model.user.User
import com.yunuscagliyan.core.util.Constant.DBUtil.PHOTO_ENTITY_NAME


@Entity(tableName = PHOTO_ENTITY_NAME)
data class PhotoEntity(
    @PrimaryKey(autoGenerate = false)
    val photoId: Long? = null,
    @ColumnInfo(name = "pageURL")
    val pageURL: String? = null,
    @ColumnInfo(name = "type")
    val type: String? = null,
    @ColumnInfo(name = "tags")
    val tags: String? = null,
    @ColumnInfo(name = "previewURL")
    val previewURL: String? = null,
    @ColumnInfo(name = "previewWidth")
    val previewWidth: Int? = null,
    @ColumnInfo(name = "previewHeight")
    val previewHeight: Int? = null,
    @ColumnInfo(name = "webformatURL")
    val webformatURL: String? = null,
    @ColumnInfo(name = "webformatWidth")
    val webformatWidth: Int? = null,
    @ColumnInfo(name = "webformatHeight")
    val webformatHeight: Int? = null,
    @ColumnInfo(name = "largeImageURL")
    val largeImageURL: String? = null,
    @ColumnInfo(name = "imageWidth")
    val imageWidth: Int? = null,
    @ColumnInfo(name = "imageHeight")
    val imageHeight: Int? = null,
    @ColumnInfo(name = "imageSize")
    val imageSize: Long? = null,
    @ColumnInfo(name = "views")
    val views: Int? = null,
    @ColumnInfo(name = "downloads")
    val downloads: Int? = null,
    @ColumnInfo(name = "collections")
    val collections: Int? = null,
    @ColumnInfo(name = "likes")
    val likes: Int? = null,
    @ColumnInfo(name = "comments")
    val comments: Int? = null,
    @ColumnInfo(name = "user_id")
    val userId: Long? = null,
    @ColumnInfo(name = "user")
    val user: String? = null,
    @ColumnInfo(name = "userImageURL")
    val userImageURL: String? = null,
)

