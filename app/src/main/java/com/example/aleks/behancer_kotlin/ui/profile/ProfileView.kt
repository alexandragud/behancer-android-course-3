package com.example.aleks.behancer_kotlin.ui.profile

import com.example.aleks.behancer_kotlin.common.BaseView
import com.example.aleks.behancer_kotlin.data.model.user.User

interface ProfileView : BaseView {

    fun showUser(user: User)
}