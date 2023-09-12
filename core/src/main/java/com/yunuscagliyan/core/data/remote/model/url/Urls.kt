package com.yunuscagliyan.core.data.remote.model.url


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Urls(
    @Json(name = "full")
    var full: String? = null,
    @Json(name = "raw")
    var raw: String? = null,
    @Json(name = "regular")
    var regular: String? = null,
    @Json(name = "small")
    var small: String? = null,
    @Json(name = "small_s3")
    var smallS3: String? = null,
    @Json(name = "thumb")
    var thumb: String? = null,
) : Parcelable