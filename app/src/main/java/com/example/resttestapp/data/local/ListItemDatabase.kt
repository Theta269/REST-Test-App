package com.example.resttestapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.resttestapp.data.local.dao.ListItemDao
import com.example.resttestapp.data.local.entities.LocalListItemEntity

// TODO Store local data in database
@Database(entities = [LocalListItemEntity::class], version = 1)
abstract class ListItemDatabase : RoomDatabase() {
    abstract fun listItemDao(): ListItemDao
}