package com.yunuscagliyan.core.data.remote.model.collection

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel
import com.yunuscagliyan.core.data.remote.model.user.User
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class CollectionModel(
    @Json(name = "id")
    var id: String? = null,
    @Json(name = "title")
    var title: String? = null,
    @Json(name = "description")
    var description: String? = null,
    @Json(name = "published_at")
    var publishedAt: String? = null,
    @Json(name = "last_collected_at")
    var lastCollectedAt: String? = null,
    @Json(name = "updated_at")
    var updatedAt: String? = null,
    @Json(name = "featured")
    var featured: Boolean? = null,
    @Json(name = "total_photos")
    var totalPhotos: Int? = null,
    @Json(name = "user")
    var user: User? = null,
    @Json(name = "cover_photo")
    var coverPhoto: PhotoModel? = null,
) : Parcelable