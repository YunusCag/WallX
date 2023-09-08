package com.yunuscagliyan.core.data.remote.model.url


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Urls(
    @Json(name = "full")
    var full: String?,
    @Json(name = "raw")
    var raw: String?,
    @Json(name = "regular")
    var regular: String?,
    @Json(name = "small")
    var small: String?,
    @Json(name = "small_s3")
    var smallS3: String?,
    @Json(name = "thumb")
    var thumb: String?
):Parcelable