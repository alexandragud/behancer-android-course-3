package com.example.aleks.behancer_kotlin.ui.profile

import com.example.aleks.behancer_kotlin.common.BasePresenter
import com.example.aleks.behancer_kotlin.data.Storage
import com.example.aleks.behancer_kotlin.utils.ApiUtils
import com.example.aleks.behancer_kotlin.utils.networkExceptions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProfilePresenter(private val view: ProfileView, private val storage: Storage?) : BasePresenter() {

    fun getProfile(username: String?) {
        mCompositeDisposable.add(ApiUtils.initApiService().getUserInfo(username)
            .subscribeOn(Schedulers.io())
            .doOnSuccess { storage?.insertUser(it) }
            .onErrorReturn {
                when (it::class) {
                    in networkExceptions -> storage?.getUser(username)
                    else -> null
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view.showLoading() }
            .doFinally { view.hideLoading() }
            .subscribe({ view.showUser(it.user) },
                { view.showError() })

        )

    }
}