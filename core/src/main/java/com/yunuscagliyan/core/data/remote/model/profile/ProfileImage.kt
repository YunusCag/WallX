package com.yunuscagliyan.core.data.remote.model.profile


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ProfileImage(
    @Json(name = "large")
    var large: String? = null,
    @Json(name = "medium")
    var medium: String? = null,
    @Json(name = "small")
    var small: String? = null,
) : Parcelable