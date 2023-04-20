package com.example.resttestapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.resttestapp.data.local.dao.ListItemDao
import com.example.resttestapp.data.local.entities.LocalListItemEntity

// TODO Store local data in database
@Database(entities = [LocalListItemEntity::class], version = 1)
abstract class ListItemDatabase : RoomDatabase() {
    abstract fun listItemDao(): ListItemDao
}

private lateinit var instance: ListItemDatabase

fun getDatabase(context: Context): ListItemDatabase {
    synchronized(ListItemDatabase::class.java) {
        if (!::instance.isInitialized) {
            instance = Room.databaseBuilder(context.applicationContext,
                ListItemDatabase::class.java,
                "listItem").build()
        }
    }
    return instance
}