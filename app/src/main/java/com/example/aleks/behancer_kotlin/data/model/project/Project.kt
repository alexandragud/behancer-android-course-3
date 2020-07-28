package com.example.aleks.behancer_kotlin.data.model.project

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(primaryKeys = ["id"], ignoredColumns = ["owners", "cover"])
data class Project(
    var id: Int,
    var name: String,
    @ColumnInfo(name = "published_on")
    @SerializedName("published_on") var publishedOn: Long?,
    var owners: List<Owner>,
    @SerializedName("covers")
    var cover: Cover?
) : Serializable{
    constructor(id:Int, name:String, publishedOn: Long?) : this (id, name, publishedOn, ArrayList<Owner>(), null)
}