package com.example.aleks.behancer_kotlin.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aleks.behancer_kotlin.R
import com.example.aleks.behancer_kotlin.common.PresenterFragment
import com.example.aleks.behancer_kotlin.common.RefreshOwner
import com.example.aleks.behancer_kotlin.common.Refreshable
import com.example.aleks.behancer_kotlin.data.Storage
import com.example.aleks.behancer_kotlin.data.model.user.User
import com.example.aleks.behancer_kotlin.ui.project.user.UserProjectsFragment
import com.example.aleks.behancer_kotlin.utils.formatTime
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.v_error.*
import kotlinx.android.synthetic.main.v_profile.*
import moxy.ktx.moxyPresenter


class ProfileFragment : PresenterFragment(), ProfileView, Refreshable {

    private var storage: Storage? = null
    private var refreshOwner: RefreshOwner? = null
    private var username: String? = null

    private val profilePresenter by moxyPresenter { ProfilePresenter(storage) }

    companion object {
        const val profileKey = "PROFILE_KEY"

        fun newInstance(args: Bundle?): ProfileFragment {
            val fragment = ProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        storage = when (context) {
            is Storage.StorageOwner -> context.obtainStorage()
            else -> null
        }
        refreshOwner = when (context) {
            is RefreshOwner -> context
            else -> null
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fr_profile, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        username = arguments?.getString(profileKey)
        activity?.title = username
        view_profile.visibility = View.VISIBLE
        show_projects_btn.setOnClickListener { profilePresenter.openUserProjects(username) }
        onRefreshData()
    }

    override fun onDetach() {
        storage = null
        refreshOwner = null
        super.onDetach()
    }

    override fun onRefreshData() {
        profilePresenter.getProfile(username)
    }

    private fun bind(user: User) {
        Picasso.get().load(user.image?.photoUrl)
            .fit()
            .into(iv_profile)
        tv_display_name_details.text = user.displayName
        tv_created_on_details.text = formatTime(user.createdOn)
        tv_location_details.text = user.location
    }

    override fun getPresenter(): ProfilePresenter? = profilePresenter

    override fun showUser(user: User) {
        errorView.visibility = View.GONE
        view_profile.visibility = View.VISIBLE
        bind(user)
    }

    override fun openProjects(username: String?) {
        val bundle = Bundle()
        bundle.putString(UserProjectsFragment.USER_KEY, username)
        fragmentManager?.beginTransaction()!!
            .replace(R.id.fragmentContainer, UserProjectsFragment.newInstance(bundle))
            .addToBackStack(ProfileFragment::class.java.simpleName)
            .commit()
    }

    override fun showLoading() {
        refreshOwner?.setRefreshState(true)
    }

    override fun hideLoading() {
        refreshOwner?.setRefreshState(false)
    }

    override fun showError() {
        errorView.visibility = View.VISIBLE
        view_profile.visibility = View.GONE
    }
}