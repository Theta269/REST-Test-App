package com.example.resttestapp.data.models

data class RemoteListItemDetailModel(
    val userId: Number,
    val id: Number,
    val title: String,
    val body: String
) {
    fun toLocalListItemModel(): LocalListItemModel = LocalListItemModel(
        id = id,
        photoAlbumId = 0,
        photoThumbnailUrl = "",
        photoTitle = "",
        photoUrl = "",
        postBody = body,
        postTitle = title,
        userId = userId
    )
}

fun List<RemoteListItemDetailModel>.asLocalListItemModel(): List<LocalListItemModel> {
    return map {
        it.toLocalListItemModel()
    }
}
