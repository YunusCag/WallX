package com.yunuscagliyan.core.data.remote.model.user


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.yunuscagliyan.core.data.remote.model.link.LinksX
import com.yunuscagliyan.core.data.remote.model.profile.ProfileImage
import com.yunuscagliyan.core.data.remote.model.social.Social
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "accepted_tos")
    var acceptedTos: Boolean?,
    @Json(name = "bio")
    var bio: String?,
    @Json(name = "first_name")
    var firstName: String?,
    @Json(name = "for_hire")
    var forHire: Boolean?,
    @Json(name = "id")
    var id: String?,
    @Json(name = "instagram_username")
    var instagramUsername: String?,
    @Json(name = "last_name")
    var lastName: String?,
    @Json(name = "links")
    var links: LinksX?,
    @Json(name = "location")
    var location: String?,
    @Json(name = "name")
    var name: String?,
    @Json(name = "portfolio_url")
    var portfolioUrl: String?,
    @Json(name = "profile_image")
    var profileImage: ProfileImage?,
    @Json(name = "social")
    var social: Social?,
    @Json(name = "total_collections")
    var totalCollections: Int?,
    @Json(name = "total_likes")
    var totalLikes: Int?,
    @Json(name = "total_photos")
    var totalPhotos: Int?,
    @Json(name = "twitter_username")
    var twitterUsername: String?,
    @Json(name = "updated_at")
    var updatedAt: String?,
    @Json(name = "username")
    var username: String?
):Parcelable