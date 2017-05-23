package com.simoncherry.cookbookkotlin.mvp.contract

import com.simoncherry.cookbookkotlin.model.MobCategoryResult
import com.simoncherry.cookbookkotlin.mvp.presenter.BasePresenter
import com.simoncherry.cookbookkotlin.ui.BaseView

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
interface SplashContract {
    interface View : BaseView {
        fun onQueryCategorySuccess(value: MobCategoryResult?)
    }

    interface Presenter : BasePresenter<View> {
        fun queryCategory()
    }
}