package com.example.aleks.behancer_kotlin.data

import com.example.aleks.behancer_kotlin.data.database.BehanceDao
import com.example.aleks.behancer_kotlin.data.model.project.Cover
import com.example.aleks.behancer_kotlin.data.model.project.Owner
import com.example.aleks.behancer_kotlin.data.model.project.Project
import com.example.aleks.behancer_kotlin.data.model.project.ProjectResponse
import com.example.aleks.behancer_kotlin.data.model.user.UserResponse

class Storage(val behanceDao: BehanceDao) {

    fun insertProjects(response: ProjectResponse) {
        val projects = response.projects
        behanceDao.insertProjects(projects)
        val assembled = assemble(projects)
        behanceDao.insertCovers(assembled.first)
        behanceDao.insertOwners(assembled.second)
    }

    private fun assemble(projects: List<Project>): Pair<List<Cover?>, List<Owner>> {
        val covers = ArrayList<Cover?>()
        val owners = ArrayList<Owner>()
        for (i in projects.indices) {
            val cover = projects[i].cover
            cover?.projectId = projects[i].id
            if (behanceDao.getCoverFromProject(projects[i].id) == null)
                covers.add(cover)
            covers += cover
            val owner = projects[i].owners[0]
            owner.projectId = projects[i].id
            if (!behanceDao.getOwnersFromProject(projects[i].id).contains(owner))
                owners += owner
        }
        return Pair(covers, owners)
    }

    fun getProjects(): ProjectResponse {
        val projects = behanceDao.getProjects()
        for (p in projects!!){
            p.apply {
                 cover = behanceDao.getCoverFromProject(id)!!
                owners = behanceDao.getOwnersFromProject(id)
            }
        }
        return ProjectResponse(projects)
    }

    fun insertUser(userResponse: UserResponse) {
        val user=userResponse.user
        val image = user.image
        image?.id=user.id
        image?.userId=user.id
        behanceDao.insertUser(user)
        behanceDao.insertImage(image)
    }

    fun getUser(username: String?): UserResponse {
        val user = behanceDao.getUserByName(username)
        user.image = behanceDao.getImageFromUser(user.id)
        return UserResponse(user)
    }

    fun getUserProjects(username: String): ProjectResponse? {
        val projects = behanceDao.getUserProjects(username)
        if (projects != null && !projects.isEmpty()) {
            for(p in projects){
                p.apply {
                    cover = behanceDao.getCoverFromProject(id)!!
                    owners = behanceDao.getOwnersFromProject(id)
                }
            }
            return ProjectResponse(projects)
        }
        else return null
    }

    interface StorageOwner {
        fun obtainStorage(): Storage
    }

}