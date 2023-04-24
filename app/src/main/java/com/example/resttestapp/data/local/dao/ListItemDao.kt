package com.example.resttestapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.resttestapp.data.local.entities.LocalListItemEntity

@Dao
interface ListItemDao {
    @Query("SELECT * FROM locallistitementity")
    fun getAll(): List<LocalListItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(listItems: List<LocalListItemEntity>)

    @Delete
    fun deleteAll(listItems: List<LocalListItemEntity>)
}