package com.example.resttestapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.resttestapp.data.local.entities.asLocalListItemModel
import com.example.resttestapp.data.models.LocalListItemModel
import com.example.resttestapp.data.repositories.ListRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val listRepository: ListRepository
) : ViewModel() {
    var localListResponse:List<LocalListItemModel> by mutableStateOf(listOf())
    private var errorMessage: String by mutableStateOf("")

    init {
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

    class Factory(
        private val listRepository: ListRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(listRepository) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}