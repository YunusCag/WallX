package com.yunuscagliyan.core.data.remote.model.photo


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.yunuscagliyan.core.data.remote.model.link.Links
import com.yunuscagliyan.core.data.remote.model.sponsor.Sponsorship
import com.yunuscagliyan.core.data.remote.model.url.Urls
import com.yunuscagliyan.core.data.remote.model.user.User
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class PhotoModel(
    @Json(name = "alt_description")
    var altDescription: String? = null,
    @Json(name = "blur_hash")
    var blurHash: String? = null,
    @Json(name = "color")
    var color: String? = null,
    @Json(name = "created_at")
    var createdAt: String? = null,
    @Json(name = "description")
    var description: String? = null,
    @Json(name = "height")
    var height: Int? = null,
    @Json(name = "id")
    var id: String? = null,
    @Json(name = "liked_by_user")
    var likedByUser: Boolean? = null,
    @Json(name = "likes")
    var likes: Int? = null,
    @Json(name = "links")
    var links: Links? = null,
    @Json(name = "promoted_at")
    var promotedAt: String? = null,
    @Json(name = "slug")
    var slug: String? = null,
    @Json(name = "sponsorship")
    var sponsorship: Sponsorship? = null,
    @Json(name = "updated_at")
    var updatedAt: String? = null,
    @Json(name = "urls")
    var urls: Urls? = null,
    @Json(name = "user")
    var user: User? = null,
    @Json(name = "width")
    var width: Int? = null
) : Parcelable