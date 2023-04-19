package com.example.resttestapp.data.sources

import com.example.resttestapp.data.models.RemoteListItemDetailModel
import com.example.resttestapp.data.models.RemoteListItemModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class RemoteListDataSource(
    private val jsonApi: JsonApi,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchAllPhotos(): List<RemoteListItemModel> =
        withContext(ioDispatcher) {
            jsonApi.fetchAllPhotos()
        }

    suspend fun fetchAllDetails(): List<RemoteListItemDetailModel> =
        withContext(ioDispatcher) {
            jsonApi.fetchAllPosts()
        }
}

interface JsonApi {
    @GET("photos")
    suspend fun fetchAllPhotos(): List<RemoteListItemModel>

    @GET("posts")
    suspend fun fetchAllPosts(): List<RemoteListItemDetailModel>

    companion object {
        private var jsonApi: JsonApi? = null

        fun getInstance() : JsonApi {
            if (jsonApi == null) {
                jsonApi = Retrofit.Builder()
                    // TODO This is an example API, this would be changed when connecting to real data
                    .baseUrl("https://jsonplaceholder.typicode.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(JsonApi::class.java)
            }
            return jsonApi!!
        }
    }
}
