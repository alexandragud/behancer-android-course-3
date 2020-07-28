package com.example.aleks.behancer_kotlin.data.model.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(primaryKeys = ["id"], ignoredColumns = ["image"])
data class User(
    var id: Int,
    var username: String,
    var location: String,
    @ColumnInfo(name = "created_on")
    @SerializedName("created_on")
    var createdOn: Long,
    @SerializedName("images")
    var image: Image?,
    @ColumnInfo(name = "display_name")
    @SerializedName("display_name")
    var displayName: String
) : Serializable {
    constructor(id: Int, username: String, location: String, createdOn: Long, displayName: String):
            this(id, username, location, createdOn, null, displayName)
}