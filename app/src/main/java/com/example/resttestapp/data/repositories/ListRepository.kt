package com.example.resttestapp.data.repositories

import com.example.resttestapp.data.sources.LocalListDataSource
import com.example.resttestapp.data.sources.RemoteListDataSource

class ListRepository (
    private val RemoteDataSource: RemoteListDataSource,
    private val LocalDataSource: LocalListDataSource
) {

}