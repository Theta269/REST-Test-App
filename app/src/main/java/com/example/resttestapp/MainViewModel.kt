package com.example.resttestapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resttestapp.data.models.RemoteListItemModel
import com.example.resttestapp.data.sources.PhotoApi
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
        var photoListResponse:List<RemoteListItemModel> by mutableStateOf(listOf())
        var errorMessage: String by mutableStateOf("")
        fun getPhotoList() {
            viewModelScope.launch {
                val photoApi = PhotoApi.getInstance()
                try {
                    val photoList = photoApi.fetchAllPhotos()
                    photoListResponse = photoList
                }
                catch (e: Exception) {
                    errorMessage = e.message.toString()
                }
            }
        }
}