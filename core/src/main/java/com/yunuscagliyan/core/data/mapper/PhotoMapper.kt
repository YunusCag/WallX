package com.yunuscagliyan.core.data.mapper

import com.yunuscagliyan.core.data.local.entity.PhotoEntity
import com.yunuscagliyan.core.data.remote.model.photo.PhotoModel


fun PhotoModel.toPhotoEntity(): PhotoEntity = PhotoEntity(
    photoId = this.id,
    pageURL = this.pageURL,
    type = this.type,
    tags = this.tags,
    previewURL = this.previewURL,
    previewWidth = this.previewWidth,
    previewHeight = this.previewHeight,
    webformatURL = this.webformatURL,
    webformatWidth = this.webformatWidth,
    webformatHeight = this.webformatHeight,
    largeImageURL = this.largeImageURL,
    imageWidth = this.imageWidth,
    imageHeight = this.imageHeight,
    imageSize = this.imageSize,
    views = this.views,
    downloads = this.downloads,
    collections = this.collections,
    likes = this.likes,
    comments = this.comments,
    userId = this.userId,
    user = this.user,
    userImageURL = this.userImageURL
)

fun PhotoEntity.toPhotoModel(): PhotoModel = PhotoModel(
    id = this.photoId,
    pageURL = this.pageURL,
    type = this.type,
    tags = this.tags,
    previewURL = this.previewURL,
    previewWidth = this.previewWidth,
    previewHeight = this.previewHeight,
    webformatURL = this.webformatURL,
    webformatWidth = this.webformatWidth,
    webformatHeight = this.webformatHeight,
    largeImageURL = this.largeImageURL,
    imageWidth = this.imageWidth,
    imageHeight = this.imageHeight,
    imageSize = this.imageSize,
    views = this.views,
    downloads = this.downloads,
    collections = this.collections,
    likes = this.likes,
    comments = this.comments,
    userId = this.userId,
    user = this.user,
    userImageURL = this.userImageURL
)

fun List<PhotoEntity>.toPhotoModelList(): List<PhotoModel> =
    this.map { it.toPhotoModel() }.toList()