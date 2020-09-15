package com.example.aleks.behancer_kotlin.ui.project

import com.example.aleks.behancer_kotlin.common.BaseView
import com.example.aleks.behancer_kotlin.data.model.project.Project
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ProjectsView : BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showProjects(projects: List<Project>)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openProfileFragment(userName: String)
}