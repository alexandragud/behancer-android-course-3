package com.example.aleks.behancer_kotlin.ui.profile

import com.example.aleks.behancer_kotlin.common.BaseView
import com.example.aleks.behancer_kotlin.data.model.user.User
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType


interface ProfileView : BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showUser(user: User)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openProjects(username:String?)
}