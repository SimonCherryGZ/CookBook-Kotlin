package com.simoncherry.cookbookkotlin.mvp.contract

import com.simoncherry.cookbookkotlin.model.MobCategoryResult
import com.simoncherry.cookbookkotlin.model.MobRecipe
import com.simoncherry.cookbookkotlin.model.MobRecipeResult
import com.simoncherry.cookbookkotlin.mvp.presenter.BasePresenter
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
interface ApiTestContract {
    interface View : BaseView {
        fun onQueryCategorySuccess(value: MobCategoryResult?)
        fun onQueryRecipeSuccess(value: MobRecipeResult?)
        fun onQueryDetailSuccess(value: MobRecipe?)
    }

    interface Presenter : BasePresenter<View> {
        fun queryCategory()
        fun queryRecipe(cid: String, page: Int, size: Int)
        fun queryDetail(id: String)
    }
}