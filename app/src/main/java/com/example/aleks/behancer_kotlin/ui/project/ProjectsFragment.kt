package com.example.aleks.behancer_kotlin.ui.project

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aleks.behancer_kotlin.BuildConfig
import com.example.aleks.behancer_kotlin.R
import com.example.aleks.behancer_kotlin.common.RefreshOwner
import com.example.aleks.behancer_kotlin.common.Refreshable
import com.example.aleks.behancer_kotlin.data.Storage
import com.example.aleks.behancer_kotlin.ui.profile.ProfileActivity
import com.example.aleks.behancer_kotlin.ui.profile.ProfileFragment
import com.example.aleks.behancer_kotlin.utils.ApiUtils
import com.example.aleks.behancer_kotlin.utils.networkExceptions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fr_projects.*
import kotlinx.android.synthetic.main.v_error.*

class ProjectsFragment : Fragment(), Refreshable, ProjectsAdapter.OnItemClickListener {

    private var storage: Storage? = null
    private lateinit var projectsAdapter: ProjectsAdapter
    private var refreshOwner: RefreshOwner? = null
    private var disposable: Disposable? = null

    companion object {
        fun newInstance(): ProjectsFragment =
            ProjectsFragment()
    }

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
        activity?.title = getText(R.string.projects)
        projectsAdapter =
            ProjectsAdapter(this)
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = projectsAdapter
        onRefreshData()
    }

    override fun onDetach() {
        storage = null
        refreshOwner = null
        if (disposable != null)
            disposable?.dispose()
        super.onDetach()
    }

    override fun onRefreshData() {
        getProjects()
    }

    override fun onItemClick(username: String) {
          val intent = Intent(activity, ProfileActivity::class.java)
        val args = Bundle()
        args.putString(ProfileFragment.profileKey, username)
        intent.putExtra(ProfileActivity.usernameKey, args)
        startActivity(intent)
    }

    private fun getProjects() {
        disposable = ApiUtils.initApiService()
            .getProjects(BuildConfig.API_QUERY)
            .doOnSuccess { storage?.insertProjects(it) }
            .onErrorReturn {
                if (networkExceptions.contains(it::class))
                    storage?.getProjects()
                else
                    null
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { refreshOwner?.setRefreshState(true) }
            .doFinally { refreshOwner?.setRefreshState(false) }
            .subscribe(
                {
                    errorView.visibility = View.GONE
                    recycler.visibility = View.VISIBLE
                    projectsAdapter.addData(it.projects, true)
                },
                {
                    errorView.visibility = View.VISIBLE
                    recycler.visibility = View.GONE
                }
            )
    }
}