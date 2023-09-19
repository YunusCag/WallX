package com.yunuscagliyan.core.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yunuscagliyan.core.data.remote.model.link.Links
import com.yunuscagliyan.core.data.remote.model.url.Urls
import com.yunuscagliyan.core.data.remote.model.user.User
import com.yunuscagliyan.core.util.Constant.DBUtil.PHOTO_ENTITY_NAME


@Entity(tableName = PHOTO_ENTITY_NAME)
data class PhotoEntity(
    @PrimaryKey(autoGenerate = false)
    var photoId: String,
    @ColumnInfo(name = "alt_description")
    var altDescription: String? = null,
    @ColumnInfo(name = "blur_hash")
    var blurHash: String? = null,
    @ColumnInfo(name = "color")
    var color: String? = null,
    @ColumnInfo(name = "created_at")
    var createdAt: String? = null,
    @ColumnInfo(name = "description")
    var description: String? = null,
    @ColumnInfo(name = "height")
    var height: Int? = null,
    @ColumnInfo(name = "width")
    var width: Int? = null,
    @ColumnInfo(name = "likes")
    var likes: Int? = null,

    @ColumnInfo(name = "download_link")
    var downloadLink: String? = null,

    @Embedded
    var urls: Urls? = null,

    @ColumnInfo(name = "profile_image")
    var profileImage: String? = null,
    @ColumnInfo(name = "first_name")
    var firstName: String? = null,
    @ColumnInfo(name = "last_name")
    var lastName: String? = null,
)

