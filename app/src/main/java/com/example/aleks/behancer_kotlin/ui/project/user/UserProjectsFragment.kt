package com.example.aleks.behancer_kotlin.ui.project.user

import android.os.Bundle
import com.example.aleks.behancer_kotlin.ui.project.AbstractProjectsFragment
import com.example.aleks.behancer_kotlin.ui.project.ProjectsAdapter

class UserProjectsFragment:AbstractProjectsFragment() {

    private var username: String? = null

    companion object {
        const val USER_KEY = "USER_KEY"

        fun newInstance(args: Bundle?): UserProjectsFragment {
            val fragment = UserProjectsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        username = arguments?.getString(USER_KEY)
        activity?.title = "$username/Projects"
        super.onActivityCreated(savedInstanceState)
    }

    override fun getClickListener(): ProjectsAdapter.OnItemClickListener? = null

    override fun openProfileFragment(userName: String) {
        //do nothing
    }

    override fun onRefreshData() {
        if (username!=null) projectsPresenter.getUserProjects(username!!)
    }
}