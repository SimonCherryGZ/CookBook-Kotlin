package com.simoncherry.cookbookkotlin.mvp.presenter

import com.simoncherry.cookbookkotlin.ui.BaseView

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/22
 *     desc   :
 *     version: 1.0
 * </pre>
 */
interface BasePresenter<in V : BaseView> {
    fun attachView(view: V)
    fun detachView()
}