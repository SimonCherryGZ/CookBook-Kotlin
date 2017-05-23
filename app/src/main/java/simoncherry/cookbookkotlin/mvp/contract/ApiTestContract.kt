package simoncherry.cookbookkotlin.mvp.contract

import simoncherry.cookbookkotlin.model.MobCategoryResult
import simoncherry.cookbookkotlin.model.MobRecipe
import simoncherry.cookbookkotlin.model.MobRecipeResult
import simoncherry.cookbookkotlin.mvp.presenter.BasePresenter
import simoncherry.cookbookkotlin.ui.BaseView

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
        fun onQueryRecipe(value: MobRecipeResult?)
        fun onQueryDetail(value: MobRecipe?)
    }

    interface Presenter : BasePresenter<View> {
        fun queryCategory()
        fun queryRecipe(cid: String, page: Int, size: Int)
        fun queryDetail(id: String)
    }
}