package com.example.resttestapp.data.models

import com.example.resttestapp.data.local.entities.LocalListItemEntity

data class LocalListItemModel(
    val id: Number,
    val photoAlbumId: Number,
    val photoThumbnailUrl: String,
    val photoTitle: String,
    val photoUrl: String,
    var postBody: String,
    var postTitle: String,
    var userId: Number,
) {
    fun toLocalListItemEntity(): LocalListItemEntity = LocalListItemEntity(
        id = id,
        photoAlbumId = photoAlbumId,
        photoThumbnailUrl = photoThumbnailUrl,
        photoTitle = photoTitle,
        photoUrl = photoUrl,
        postBody = postBody,
        postTitle = postTitle,
        userId = userId
    )
}

fun List<LocalListItemModel>.asLocalListItemEntity(): List<LocalListItemEntity> {
    return map {
        it.toLocalListItemEntity()
    }
}
