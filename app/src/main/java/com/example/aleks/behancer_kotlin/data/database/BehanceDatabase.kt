package com.example.aleks.behancer_kotlin.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.aleks.behancer_kotlin.data.model.project.Cover
import com.example.aleks.behancer_kotlin.data.model.project.Owner
import com.example.aleks.behancer_kotlin.data.model.project.Project
import com.example.aleks.behancer_kotlin.data.model.user.Image
import com.example.aleks.behancer_kotlin.data.model.user.User

@Database(entities = [Project::class, Cover::class, Owner::class, User::class, Image::class], version = 1)
abstract class BehanceDatabase : RoomDatabase(){
    abstract val behanceDao:BehanceDao

}
