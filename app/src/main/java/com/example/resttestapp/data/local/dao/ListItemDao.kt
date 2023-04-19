package com.example.resttestapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.resttestapp.data.local.entities.LocalListItemEntity

// TODO use DAO to access data offline
@Dao
interface ListItemDao {
    @Query("SELECT * FROM locallistitementity")
    fun getAll(): List<LocalListItemEntity>

    @Insert
    fun insertAll(vararg listItems: LocalListItemEntity)

    @Delete
    fun delete(listItem: LocalListItemEntity)
}