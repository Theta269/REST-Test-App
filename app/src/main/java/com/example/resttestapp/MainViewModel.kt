package com.example.resttestapp

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.resttestapp.data.local.entities.asLocalListItemModel
import com.example.resttestapp.data.local.getDatabase
import com.example.resttestapp.data.models.LocalListItemModel
import com.example.resttestapp.data.repositories.ListRepository
import com.example.resttestapp.data.sources.JsonApi
import com.example.resttestapp.data.sources.LocalListDataSource
import com.example.resttestapp.data.sources.RemoteListDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(application = Application()) {
    var localListResponse:List<LocalListItemModel> by mutableStateOf(listOf())
    private val listRepository: ListRepository
    private var errorMessage: String by mutableStateOf("")

    init {
        val remoteListDataSource = RemoteListDataSource(JsonApi.getInstance(), Dispatchers.IO)
        val localListDataSource = LocalListDataSource(getDatabase(app), Dispatchers.IO)
        listRepository = ListRepository(remoteListDataSource, localListDataSource)
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                listRepository.synchronize()
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getLocalDataList() {
        viewModelScope.launch {
            try {
                localListResponse = listRepository.getDataFromLocal().asLocalListItemModel()
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}