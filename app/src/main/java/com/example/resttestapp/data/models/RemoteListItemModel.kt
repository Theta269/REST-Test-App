package com.example.resttestapp.data.models

data class RemoteListItemModel(
    val albumId: Number,
    val id: Number,
    val title: String,
    val url: String,
    val thumbnailUrl: String
) {
    fun toLocalListItemModel(): LocalListItemModel  = LocalListItemModel(
        id = id,
        photoAlbumId = albumId,
        photoThumbnailUrl = thumbnailUrl,
        photoTitle = title,
        photoUrl = url,
        postBody = null,
        postTitle = null,
        userId = null
    )
}


