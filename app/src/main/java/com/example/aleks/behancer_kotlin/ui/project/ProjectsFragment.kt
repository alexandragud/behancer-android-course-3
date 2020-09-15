package com.example.aleks.behancer_kotlin.ui.project

import android.content.Intent
import android.os.Bundle
import com.example.aleks.behancer_kotlin.R
import com.example.aleks.behancer_kotlin.ui.profile.ProfileActivity
import com.example.aleks.behancer_kotlin.ui.profile.ProfileFragment

class ProjectsFragment : AbstractProjectsFragment(), ProjectsView,
    ProjectsAdapter.OnItemClickListener {

    companion object {
        fun newInstance(): ProjectsFragment = ProjectsFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.title = getString(R.string.projects)
    }

    override fun onRefreshData() {
        projectsPresenter.getProjects()
    }

    override fun onItemClick(username: String) {
        projectsPresenter.openProfileFragment(username)
    }


    override fun openProfileFragment(userName: String) {
        val intent = Intent(activity, ProfileActivity::class.java)
        val args = Bundle()
        args.putString(ProfileFragment.profileKey, userName)
        intent.putExtra(ProfileActivity.usernameKey, args)
        startActivity(intent)
    }

    override fun getClickListener(): ProjectsAdapter.OnItemClickListener? = this

}