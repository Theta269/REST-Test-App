package com.example.resttestapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalListItemEntity(
    @PrimaryKey val id: Number,
    @ColumnInfo(name = "photo_album_id") val photoAlbumId: Number,
    @ColumnInfo(name = "photo_thumnail_url")val photoThumbnailUrl: String,
    @ColumnInfo(name = "photo_title")val photoTitle: String,
    @ColumnInfo(name = "photo_url")val photoUrl: String,
    @ColumnInfo(name = "post_body")val postBody: String,
    @ColumnInfo(name = "post_title")val postTitle: String,
    @ColumnInfo(name = "user_id")val userId: String,
)
