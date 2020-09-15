package com.example.aleks.behancer_kotlin.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.aleks.behancer_kotlin.data.model.project.Cover
import com.example.aleks.behancer_kotlin.data.model.project.Owner
import com.example.aleks.behancer_kotlin.data.model.project.Project
import com.example.aleks.behancer_kotlin.data.model.user.Image
import com.example.aleks.behancer_kotlin.data.model.user.User

@Dao
interface BehanceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProjects(projects: List<Project?>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCovers(covers: List<Cover?>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOwners(owners: List<Owner?>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImage(image: Image?)

    @Query("select * from project")
    fun getProjects(): List<Project>?

    @Query("select * from cover where project_id = :projectId")
    fun getCoverFromProject(projectId: Int): Cover?

    @Query("select * from owner where project_id = :projectId")
    fun getOwnersFromProject(projectId: Int): List<Owner>

    @Query("select * from user where username = :userName")
    fun getUserByName(userName: String?): User

    @Query("select * from image where user_id = :userId")
    fun getImageFromUser(userId: Int?): Image

    @Query("delete from owner")
    fun clearOwnerTable()

    @Query("delete from cover")
    fun clearCoverTable()

    @Query("delete from image")
    fun clearImageTable()

    @Query("select * from user")
    fun getUsers(): List<User?>?

    @Query("select * from image")
    fun getImages(): List<Image?>?

    @Query("select project.* from project inner join owner on project.id = owner.project_id where owner.username=:username")
    fun getUserProjects(username: String):List<Project>?

}