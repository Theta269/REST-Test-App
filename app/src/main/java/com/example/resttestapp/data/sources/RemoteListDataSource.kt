package com.example.resttestapp.data.sources

import com.example.resttestapp.data.models.RemoteListItemModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class RemoteListDataSource(
    private val photoAPI: PhotoApi,
    private val detailAPI: DetailApi,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchAllPhotos(): List<RemoteListItemModel> =
        withContext(ioDispatcher) {
            photoAPI.fetchAllPhotos()
        }

    suspend fun fetchDetail(): List<RemoteListItemModel> =
        withContext(ioDispatcher) {
            detailAPI.fetchDetail()
        }
}

interface PhotoApi {
    @GET("photos")
    suspend fun fetchAllPhotos(): List<RemoteListItemModel>
    companion object {
        private var photoApi: PhotoApi? = null

        fun getInstance() : PhotoApi {
            if (photoApi == null) {
                photoApi = Retrofit.Builder()
                    // TODO This is an example API, this would be changed when connecting to real data
                    .baseUrl("https://jsonplaceholder.typicode.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(PhotoApi::class.java)
            }
            return photoApi!!
        }
    }
}

interface DetailApi {
    fun fetchDetail(): List<RemoteListItemModel>
}
