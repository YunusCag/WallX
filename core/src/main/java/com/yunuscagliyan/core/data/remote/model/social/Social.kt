package com.yunuscagliyan.core.data.remote.model.social


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Social(
    @Json(name = "instagram_username")
    var instagramUsername: String?,
    @Json(name = "paypal_email")
    var paypalEmail: String?,
    @Json(name = "portfolio_url")
    var portfolioUrl: String?,
    @Json(name = "twitter_username")
    var twitterUsername: String?
):Parcelable