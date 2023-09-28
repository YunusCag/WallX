package com.yunuscagliyan.core.data.remote.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class PhotoResponse(
    @Json(name = "total")
    var total: Int? = null,
    @Json(name = "totalHits")
    var totalHits: Int? = null,
    @Json(name = "hits")
    var results: List<PhotoModel>? = null
):Parcelable