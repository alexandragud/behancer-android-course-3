package com.example.aleks.behancer_kotlin.ui.project

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aleks.behancer_kotlin.R
import com.example.aleks.behancer_kotlin.common.BasePresenter
import com.example.aleks.behancer_kotlin.common.PresenterFragment
import com.example.aleks.behancer_kotlin.common.RefreshOwner
import com.example.aleks.behancer_kotlin.common.Refreshable
import com.example.aleks.behancer_kotlin.data.Storage
import com.example.aleks.behancer_kotlin.data.model.project.Project
import kotlinx.android.synthetic.main.fr_projects.*
import kotlinx.android.synthetic.main.v_error.*
import moxy.ktx.moxyPresenter

abstract class AbstractProjectsFragment:PresenterFragment(), ProjectsView, Refreshable {

    private var storage: Storage? = null
    private lateinit var projectsAdapter: ProjectsAdapter
    private var refreshOwner: RefreshOwner? = null
    private lateinit var presenter: ProjectsPresenter

    val projectsPresenter by moxyPresenter { ProjectsPresenter(storage) }

    override fun getPresenter(): BasePresenter<*>? = projectsPresenter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Storage.StorageOwner)
            storage = context.obtainStorage()

        refreshOwner = when (context) {
            is RefreshOwner -> context
            else -> null
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fr_projects, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        projectsAdapter = ProjectsAdapter(getClickListener())
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = projectsAdapter
        onRefreshData()
    }

    override fun onDetach() {
        storage = null
        refreshOwner = null
        super.onDetach()
    }

    abstract fun getClickListener():ProjectsAdapter.OnItemClickListener?

    override fun showLoading() {
        refreshOwner?.setRefreshState(true)
    }

    override fun hideLoading() {
        refreshOwner?.setRefreshState(false)
    }

    override fun showError() {
        errorView.visibility = View.VISIBLE
        recycler.visibility = View.GONE
    }

    override fun showProjects(projects: List<Project>) {
        errorView.visibility = View.GONE
        recycler.visibility = View.VISIBLE
        projectsAdapter.addData(projects, true)
    }


}