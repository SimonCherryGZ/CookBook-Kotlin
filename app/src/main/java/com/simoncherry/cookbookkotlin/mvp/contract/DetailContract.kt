package com.simoncherry.cookbookkotlin.mvp.contract

import com.simoncherry.cookbookkotlin.model.MobRecipe
import com.simoncherry.cookbookkotlin.mvp.presenter.BasePresenter
import com.simoncherry.cookbookkotlin.ui.BaseView

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
interface DetailContract {
    interface View : BaseView {
        fun onQueryDetailSuccess(value: MobRecipe)
    }

    interface Presenter : BasePresenter<View> {
        fun queryDetail(id: String)
    }
}