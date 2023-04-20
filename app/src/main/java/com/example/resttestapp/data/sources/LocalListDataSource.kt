package com.example.resttestapp.data.sources

import com.example.resttestapp.data.local.ListItemDatabase
import com.example.resttestapp.data.local.entities.LocalListItemEntity
import com.example.resttestapp.data.models.LocalListItemModel
import com.example.resttestapp.data.models.RemoteListItemDetailModel
import com.example.resttestapp.data.models.RemoteListItemModel
import com.example.resttestapp.data.models.asLocalListItemModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class LocalListDataSource(
    private val listItemDatabase: ListItemDatabase,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getAllListItems(): List<LocalListItemEntity> =
        withContext(ioDispatcher) {
            listItemDatabase.listItemDao().getAll()
        }

    suspend fun saveLocalData(localListItems: List<LocalListItemEntity>) {
        withContext(ioDispatcher) {
            listItemDatabase.listItemDao().insertAll(localListItems)
        }
    }

    fun mergeRemoteSources(
        photos: List<RemoteListItemModel>,
        details: List<RemoteListItemDetailModel>
    ): List<LocalListItemModel> {
        //Convert full list of photos to LocalListItemModel
        var mergedList = photos.asLocalListItemModel()

        //Convert full list of details to LocalListItemModel
        val newList = details.asLocalListItemModel()

        //Merge elements from both lists to return one consolidated list
        mergedList = mergedList.mapIndexed { index, item ->
            LocalListItemModel(
                id = item.id,
                photoAlbumId = item.photoAlbumId,
                photoThumbnailUrl = item.photoThumbnailUrl,
                photoTitle = item.photoTitle,
                photoUrl = item.photoUrl,
                postBody = newList[index].postBody,
                postTitle = newList[index].postTitle,
                userId = newList[index].userId
            )
        }

        return mergedList
    }
}