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
    var acceptedTos: Boolean? = null,
    @Json(name = "bio")
    var bio: String? = null,
    @Json(name = "first_name")
    var firstName: String? = null,
    @Json(name = "for_hire")
    var forHire: Boolean? = null,
    @Json(name = "id")
    var id: String? = null,
    @Json(name = "instagram_username")
    var instagramUsername: String? = null,
    @Json(name = "last_name")
    var lastName: String? = null,
    @Json(name = "links")
    var links: LinksX? = null,
    @Json(name = "location")
    var location: String? = null,
    @Json(name = "name")
    var name: String? = null,
    @Json(name = "portfolio_url")
    var portfolioUrl: String? = null,
    @Json(name = "profile_image")
    var profileImage: ProfileImage? = null,
    @Json(name = "social")
    var social: Social? = null,
    @Json(name = "total_collections")
    var totalCollections: Int? = null,
    @Json(name = "total_likes")
    var totalLikes: Int? = null,
    @Json(name = "total_photos")
    var totalPhotos: Int? = null,
    @Json(name = "twitter_username")
    var twitterUsername: String? = null,
    @Json(name = "updated_at")
    var updatedAt: String? = null,
    @Json(name = "username")
    var username: String? = null
) : Parcelable