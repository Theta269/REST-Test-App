package com.example.resttestapp.data.sources

import com.example.resttestapp.data.models.RemoteListItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RemoteListDataSource(
    private val photoAPI: PhotoApi,
    private val detailAPI: DetailApi,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchAllPhotos(): List<RemoteListItem> =
        withContext(ioDispatcher) {
            photoAPI.fetchAllPhotos()
        }

    suspend fun fetchDetail(): List<RemoteListItem> =
        withContext(ioDispatcher) {
            detailAPI.fetchDetail()
        }
}

interface PhotoApi {
    fun fetchAllPhotos(): List<RemoteListItem>
}

interface DetailApi {
    fun fetchDetail(): List<RemoteListItem>
}
