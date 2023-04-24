package com.example.resttestapp.data.repositories

import com.example.resttestapp.data.local.entities.LocalListItemEntity
import com.example.resttestapp.data.models.LocalListItemModel
import com.example.resttestapp.data.models.RemoteListItemDetailModel
import com.example.resttestapp.data.models.RemoteListItemModel
import com.example.resttestapp.data.models.asLocalListItemEntity
import com.example.resttestapp.data.sources.LocalListDataSource
import com.example.resttestapp.data.sources.RemoteListDataSource

class ListRepository (
    private val remoteDataSource: RemoteListDataSource,
    private val localDataSource: LocalListDataSource,
) {
    suspend fun fetchLatestPhotos(): List<RemoteListItemModel> = remoteDataSource.fetchAllPhotos().subList(0, 100)

    suspend fun fetchLatestDetails(): List<RemoteListItemDetailModel> = remoteDataSource.fetchAllDetails()

    suspend fun getDataFromLocal(): List<LocalListItemEntity> = localDataSource.getAllListItems()

    suspend fun synchronize() {
        val photos = fetchLatestPhotos()
        val details = fetchLatestDetails()
        val localListItems: List<LocalListItemModel> = localDataSource.mergeRemoteSources(photos, details)
        localDataSource.saveLocalData(localListItems.asLocalListItemEntity())
    }

}