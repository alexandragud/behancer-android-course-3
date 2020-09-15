package com.example.aleks.behancer_kotlin.data.model.project

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    foreignKeys = [ForeignKey(
        entity = Project::class,
        parentColumns = ["id"],
        childColumns = ["project_id"]
    )]
)
data class Cover(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "photo_url")
    @SerializedName("202")
    var photoUrl: String,
    @ColumnInfo(name = "project_id")
    var projectId: Int
) : Serializable{

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cover

        if (photoUrl != other.photoUrl) return false
        if (projectId != other.projectId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = photoUrl.hashCode()
        result = 31 * result + projectId
        return result
    }
}
