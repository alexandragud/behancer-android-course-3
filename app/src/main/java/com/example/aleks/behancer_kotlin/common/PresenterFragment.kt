package com.example.aleks.behancer_kotlin.common

import androidx.fragment.app.Fragment

abstract class PresenterFragment<out T : BasePresenter> : Fragment() {

    abstract fun getPresenter(): T?

    override fun onDetach() {
        if (getPresenter() != null) getPresenter()?.disposeAll()
        super.onDetach()
    }

}