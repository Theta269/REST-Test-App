package com.example.resttestapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resttestapp.data.models.LocalListItemModel
import com.example.resttestapp.data.models.RemoteListItemDetailModel
import com.example.resttestapp.data.models.RemoteListItemModel
import com.example.resttestapp.data.models.asLocalListItemModel
import com.example.resttestapp.data.sources.JsonApi
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var photoListResponse:List<RemoteListItemModel> by mutableStateOf(listOf())
    var detailListResponse:List<RemoteListItemDetailModel> by mutableStateOf(listOf())
//    private val listRepository = ListRepository(getDatabase(application))
    private var errorMessage: String by mutableStateOf("")

//    private fun refreshDataFromRepository() {
//        viewModelScope.launch {
//            try {
//                listRepository.refreshVideos()
//                _eventNetworkError.value = false
//                _isNetworkErrorShown.value = false
//
//            } catch (networkError: IOException) {
//                // Show a Toast error message and hide the progress bar.
//                if(playlist.value.isNullOrEmpty())
//                    _eventNetworkError.value = true
//            }
//        }
//    }

    fun getPhotoList() {
        viewModelScope.launch {
            val photoApi = JsonApi.getInstance()
            try {
                val photoList = photoApi.fetchAllPhotos()
                // TODO Limit list of photos to 100 to match posts return for demo purposes
                photoListResponse = photoList.subList(0, 100)
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getDetailList() {
        viewModelScope.launch {
            val detailApi = JsonApi.getInstance()
            try {
                val detailList = detailApi.fetchAllPosts()
                detailListResponse = detailList
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    // TODO move to data layer? improve through mapping/reflection?
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