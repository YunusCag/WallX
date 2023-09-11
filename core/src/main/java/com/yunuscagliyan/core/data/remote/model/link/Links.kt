package com.yunuscagliyan.core.data.remote.model.link


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Links(
    @Json(name = "download")
    var download: String?,
    @Json(name = "download_location")
    var downloadLocation: String?,
    @Json(name = "html")
    var html: String?,
    @Json(name = "self")
    var self: String?
):Parcelable