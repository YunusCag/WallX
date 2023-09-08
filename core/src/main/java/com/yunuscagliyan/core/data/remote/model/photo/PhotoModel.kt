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
    var altDescription: String?,
    @Json(name = "blur_hash")
    var blurHash: String?,
    @Json(name = "color")
    var color: String?,
    @Json(name = "created_at")
    var createdAt: String?,
    @Json(name = "description")
    var description: String?,
    @Json(name = "height")
    var height: Int?,
    @Json(name = "id")
    var id: String?,
    @Json(name = "liked_by_user")
    var likedByUser: Boolean?,
    @Json(name = "likes")
    var likes: Int?,
    @Json(name = "links")
    var links: Links?,
    @Json(name = "promoted_at")
    var promotedAt: String?,
    @Json(name = "slug")
    var slug: String?,
    @Json(name = "sponsorship")
    var sponsorship: Sponsorship?,
    @Json(name = "updated_at")
    var updatedAt: String?,
    @Json(name = "urls")
    var urls: Urls?,
    @Json(name = "user")
    var user: User?,
    @Json(name = "width")
    var width: Int?
): Parcelable