package com.example.aleks.behancer_kotlin

import android.app.Application
import androidx.room.Room
import com.example.aleks.behancer_kotlin.data.Storage
import com.example.aleks.behancer_kotlin.data.database.BehanceDatabase

class AppDelegate : Application() {

    lateinit var storage:Storage

    override fun onCreate() {
        super.onCreate()
        val database = Room.databaseBuilder(this, BehanceDatabase::class.java, "behance_database")
            .fallbackToDestructiveMigration()
            .build()
        storage = Storage(database.behanceDao)
    }

}