package com.yunuscagliyan.core.data.remote.model.photo


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class LinksX(
    @Json(name = "followers")
    var followers: String?,
    @Json(name = "following")
    var following: String?,
    @Json(name = "html")
    var html: String?,
    @Json(name = "likes")
    var likes: String?,
    @Json(name = "photos")
    var photos: String?,
    @Json(name = "portfolio")
    var portfolio: String?,
    @Json(name = "self")
    var self: String?
):Parcelable