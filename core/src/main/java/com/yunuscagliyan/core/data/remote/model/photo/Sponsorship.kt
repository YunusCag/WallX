package com.yunuscagliyan.core.data.remote.model.photo


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Sponsorship(
    @Json(name = "impression_urls")
    var impressionUrls: List<String>?,
    @Json(name = "sponsor")
    var sponsor: Sponsor?,
    @Json(name = "tagline")
    var tagline: String?,
    @Json(name = "tagline_url")
    var taglineUrl: String?
):Parcelable