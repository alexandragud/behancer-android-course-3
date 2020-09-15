package com.example.aleks.behancer_kotlin.common

import moxy.MvpAppCompatFragment

abstract class PresenterFragment : MvpAppCompatFragment() {

    abstract fun getPresenter(): BasePresenter<*>?

    override fun onDetach() {
        if ( getPresenter() != null) getPresenter()?.disposeAll()
        super.onDetach()
    }

}