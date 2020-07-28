package com.example.aleks.behancer_kotlin.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.aleks.behancer_kotlin.R
import com.example.aleks.behancer_kotlin.common.RefreshOwner
import com.example.aleks.behancer_kotlin.common.Refreshable
import com.example.aleks.behancer_kotlin.data.Storage
import com.example.aleks.behancer_kotlin.data.model.user.User
import com.example.aleks.behancer_kotlin.utils.ApiUtils
import com.example.aleks.behancer_kotlin.utils.formatTime
import com.example.aleks.behancer_kotlin.utils.networkExceptions
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.v_error.*
import kotlinx.android.synthetic.main.v_profile.*


class ProfileFragment : Fragment(), Refreshable {

    private var storage: Storage? = null
    private var refreshOwner: RefreshOwner? = null
    private var disposable: Disposable? = null
    private var username: String? = null

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
        getProfile()
    }

    private fun getProfile() {
        disposable = ApiUtils.initApiService().getUserInfo(username)
            .subscribeOn(Schedulers.io())
            .doOnSuccess { storage?.insertUser(it) }
            .onErrorReturn {
                when (it::class) {
                    in networkExceptions -> storage?.getUser(username)
                    else -> null
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { refreshOwner?.setRefreshState(true) }
            .doFinally { refreshOwner?.setRefreshState(false) }
            .subscribe({
                errorView.visibility = View.GONE
                view_profile.visibility = View.VISIBLE
                bind(it.user)
            },
                {
                    errorView.visibility = View.VISIBLE
                    view_profile.visibility = View.GONE
                })

    }

    private fun bind(user: User) {
        Picasso.get().load(user.image?.photoUrl)
            .fit()
            .into(iv_profile)
        tv_display_name_details.text = user.displayName
        tv_created_on_details.text = formatTime(user.createdOn)
        tv_location_details.text = user.location
    }
}