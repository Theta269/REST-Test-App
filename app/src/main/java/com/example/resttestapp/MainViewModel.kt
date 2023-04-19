package com.example.resttestapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resttestapp.data.models.LocalListItemModel
import com.example.resttestapp.data.models.RemoteListItemDetailModel
import com.example.resttestapp.data.models.RemoteListItemModel
import com.example.resttestapp.data.sources.JsonApi
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    var photoListResponse:List<RemoteListItemModel> by mutableStateOf(listOf())
    var detailListResponse:List<RemoteListItemDetailModel> by mutableStateOf(listOf())
    private var errorMessage: String by mutableStateOf("")

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