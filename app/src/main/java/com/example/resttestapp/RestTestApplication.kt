package com.example.resttestapp

import android.app.Application
import com.example.resttestapp.data.local.getDatabase

class RestTestApplication : Application() {
    val database by lazy {
        getDatabase(this)
    }

    override fun onCreate() {
        super.onCreate()
        database
    }
}