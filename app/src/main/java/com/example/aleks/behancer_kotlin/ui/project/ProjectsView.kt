package com.example.aleks.behancer_kotlin.ui.project

import com.example.aleks.behancer_kotlin.common.BaseView
import com.example.aleks.behancer_kotlin.data.model.project.Project

interface ProjectsView : BaseView {

    fun showProjects(projects: List<Project>)

    fun openProfileFragment(userName: String)
}