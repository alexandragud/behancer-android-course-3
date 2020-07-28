package com.example.aleks.behancer_kotlin.data.model.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(primaryKeys = ["id"])
data class Image(
    var id: Int,
    @ColumnInfo(name = "photo_url")
    @SerializedName("230")
    var photoUrl: String,
    @ColumnInfo(name = "user_id")
    var userId: Int
) : Serializable