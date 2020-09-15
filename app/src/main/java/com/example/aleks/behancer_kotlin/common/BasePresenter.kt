package com.example.aleks.behancer_kotlin.common

import io.reactivex.disposables.CompositeDisposable
import moxy.MvpPresenter

abstract class BasePresenter<V : BaseView> : MvpPresenter<V>() {

    val mCompositeDisposable = CompositeDisposable()

    fun disposeAll() = mCompositeDisposable.clear()
}