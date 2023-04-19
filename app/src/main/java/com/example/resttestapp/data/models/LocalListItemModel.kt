package com.example.resttestapp.data.models

data class LocalListItemModel(
    val id: Number,
    val photoAlbumId: Number?,
    val photoThumbnailUrl: String?,
    val photoTitle: String?,
    val photoUrl: String?,
    var postBody: String?,
    var postTitle: String?,
    var userId: Number?,
)
