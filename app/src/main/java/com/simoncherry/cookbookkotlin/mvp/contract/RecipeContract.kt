package com.simoncherry.cookbookkotlin.mvp.contract

import com.simoncherry.cookbookkotlin.model.MobRecipeResult
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
interface RecipeContract {
    interface View : BaseView {
        fun onQueryRecipeSuccess(result: MobRecipeResult)
    }

    interface Presenter : BasePresenter<View> {
        fun queryRecipe(cid: String, page: Int, size: Int)
        fun queryRecipeByField(field: String, value: String, page: Int, size: Int)
    }
}