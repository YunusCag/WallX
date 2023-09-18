package com.yunuscagliyan.core.data.remote.model.link


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Links(
    @Json(name = "download")
    var download: String? = null,
    @Json(name = "download_location")
    var downloadLocation: String? = null,
    @Json(name = "html")
    var html: String? = null,
    @Json(name = "self")
    var self: String? = null,
) : Parcelable