package com.example.resttestapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.resttestapp.data.models.LocalListItemModel

@Entity
data class LocalListItemEntity constructor(
    @PrimaryKey val id: Number,
    @ColumnInfo(name = "photo_album_id") val photoAlbumId: Number,
    @ColumnInfo(name = "photo_thumnail_url")val photoThumbnailUrl: String,
    @ColumnInfo(name = "photo_title")val photoTitle: String,
    @ColumnInfo(name = "photo_url")val photoUrl: String,
    @ColumnInfo(name = "post_body")val postBody: String,
    @ColumnInfo(name = "post_title")val postTitle: String,
    @ColumnInfo(name = "user_id")val userId: Number,
)

fun List<LocalListItemEntity>.asLocalListItemModel(): List<LocalListItemModel> {
    return map {
        LocalListItemModel(
            id = it.id,
            photoAlbumId = it.photoAlbumId,
            photoThumbnailUrl = it.photoThumbnailUrl,
            photoTitle = it.photoTitle,
            photoUrl = it.photoUrl,
            postBody = it.postBody,
            postTitle = it.postTitle,
            userId = it.userId
        )
    }
}
