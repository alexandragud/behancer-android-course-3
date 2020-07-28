package com.example.aleks.behancer_kotlin.data.model.project

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    primaryKeys = ["id"],
    foreignKeys = [ForeignKey(
        entity = Project::class,
        parentColumns = ["id"],
        childColumns = ["project_id"]
    )]
)
data class Cover(
    var id: Int,
    @ColumnInfo(name = "photo_url")
    @SerializedName("202")
    var photoUrl: String,
    @ColumnInfo(name = "project_id")
    var projectId: Int
) : Serializable