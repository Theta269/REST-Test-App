package com.example.resttestapp.data.sources

import com.example.resttestapp.data.models.LocalListItemModel
import com.example.resttestapp.data.models.RemoteListItemDetailModel
import com.example.resttestapp.data.models.RemoteListItemModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class LocalListDataSource(
    private val ioDispatcher: CoroutineDispatcher
) {
//    suspend fun getAllListItems(): List<LocalListItemModel> =
//        withContext(ioDispatcher) {
//            // TODO get all list items from ROOM DB
//        }
    fun saveLocalData(localListItem: List<LocalListItemModel>) {
        // TODO store list of LocalListItemModel to ROOM DB
    }

    fun mergeRemoteSources(
        photos: List<RemoteListItemModel>,
        details: List<RemoteListItemDetailModel>
    ): List<LocalListItemModel> {
        //Convert full list of photos to LocalListItemModel
        val photoItemList: MutableList<LocalListItemModel> =
            emptyList<LocalListItemModel>().toMutableList()
        for (photo in photos) {
            photoItemList.add(photo.toLocalListItemModel())
        }

        //Convert full list of details to LocalListItemModel
        val detailItemList: MutableList<LocalListItemModel> =
            emptyList<LocalListItemModel>().toMutableList()
        for (detail in details) {
            detailItemList.add(detail.toLocalListItemModel())
        }

        //Merge elements from both lists to return one consolidated list
        for (i in photoItemList.indices) {
            if (photoItemList[i].id == detailItemList[i].id) {
                photoItemList[i].postBody = detailItemList[i].postBody
                photoItemList[i].postTitle = detailItemList[i].postTitle
                photoItemList[i].userId = detailItemList[i].userId
            }
        }

        return photoItemList
    }
}