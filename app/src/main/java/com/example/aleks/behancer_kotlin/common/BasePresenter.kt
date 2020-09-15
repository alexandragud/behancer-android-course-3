package com.example.aleks.behancer_kotlin.common

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter {

    val mCompositeDisposable = CompositeDisposable()

    fun disposeAll() = mCompositeDisposable.clear()
}