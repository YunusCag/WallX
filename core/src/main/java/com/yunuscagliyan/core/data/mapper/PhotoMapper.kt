package com.yunuscagliyan.core.data.mapper

import com.yunuscagliyan.core.data.local.entity.PhotoEntity
import com.yunuscagliyan.core.data.remote.model.link.Links
import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel
import com.yunuscagliyan.core.data.remote.model.profile.ProfileImage
import com.yunuscagliyan.core.data.remote.model.user.User
import java.util.UUID


fun PhotoModel.toPhotoEntity(): PhotoEntity = PhotoEntity(
    photoId = this.id ?: UUID.randomUUID().toString(),
    altDescription = this.altDescription,
    blurHash = this.blurHash,
    color = this.color,
    createdAt = this.createdAt,
    description = this.description,
    height = this.height,
    width = this.width,
    likes = this.likes,
    downloadLink = this.links?.download,
    downloadLocation = this.links?.downloadLocation,
    urls = this.urls,
    profileImage = this.user?.profileImage?.medium,
    firstName = this.user?.firstName,
    lastName = this.user?.lastName,
)

fun PhotoEntity.toPhotoModel(): PhotoModel = PhotoModel(
    id = this.photoId,
    altDescription = this.altDescription,
    blurHash = this.blurHash,
    color = this.color,
    createdAt = this.createdAt,
    description = this.description,
    height = this.height,
    width = this.width,
    likes = this.likes,
    links = Links(
        download = this.downloadLink,
        downloadLocation = this.downloadLocation
    ),
    urls = this.urls,
    user = User(
        firstName = this.firstName,
        lastName = this.lastName,
        profileImage = ProfileImage(
            medium = this.profileImage
        )
    )
)

fun List<PhotoEntity>.toPhotoModelList(): List<PhotoModel> =
    this.map { it.toPhotoModel() }.toList()