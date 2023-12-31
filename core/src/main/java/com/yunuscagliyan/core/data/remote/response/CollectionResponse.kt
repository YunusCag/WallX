package com.yunuscagliyan.core.data.remote.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.yunuscagliyan.core.data.remote.model.collection.CollectionModel
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class CollectionResponse(
    @Json(name = "total")
    var total: Int? = null,
    @Json(name = "total_pages")
    var totalPages: Int? = null,
    @Json(name = "results")
    var results: List<CollectionModel>? = null
) : Parcelable