package com.example.resttestapp.data.models

data class RemoteListItemDetailModel(
    val userId: Number,
    val id: Number,
    val title: String,
    val body: String
) {
    fun toLocalListItemModel(): LocalListItemModel = LocalListItemModel(
        id = id,
        photoAlbumId = null,
        photoThumbnailUrl = null,
        photoTitle = null,
        photoUrl = null,
        postBody = body,
        postTitle = title,
        userId = userId
    )
}
