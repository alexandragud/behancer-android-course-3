package com.example.aleks.behancer_kotlin.ui.project

import com.example.aleks.behancer_kotlin.BuildConfig
import com.example.aleks.behancer_kotlin.common.BasePresenter
import com.example.aleks.behancer_kotlin.data.Storage
import com.example.aleks.behancer_kotlin.utils.ApiUtils
import com.example.aleks.behancer_kotlin.utils.networkExceptions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProjectsPresenter(private val view: ProjectsView, private val storage: Storage?) : BasePresenter() {

    fun getProjects() {
        mCompositeDisposable.add(ApiUtils.initApiService()
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
            .doOnSubscribe { view.showLoading() }
            .doFinally { view.hideLoading() }
            .subscribe(
                { view.showProjects(it.projects) },
                { view.showError() }
            )
        )
    }

    fun openProfileFragment(name: String) = view.openProfileFragment(name)

}