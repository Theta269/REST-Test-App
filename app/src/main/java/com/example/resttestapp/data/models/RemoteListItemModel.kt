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
        postBody = "",
        postTitle = "null",
        userId = 0
    )
}

fun List<RemoteListItemModel>.asLocalListItemModel(): List<LocalListItemModel> {
    return map {
        it.toLocalListItemModel()
    }
}


