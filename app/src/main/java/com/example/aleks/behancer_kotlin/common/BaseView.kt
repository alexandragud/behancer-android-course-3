package com.example.aleks.behancer_kotlin.common

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface BaseView : MvpView{

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showLoading()
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun hideLoading()
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showError()

}