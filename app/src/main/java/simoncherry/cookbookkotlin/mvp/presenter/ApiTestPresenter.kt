package simoncherry.cookbookkotlin.mvp.presenter

import simoncherry.cookbookkotlin.api.ApiCallback
import simoncherry.cookbookkotlin.model.MobCategoryResult
import simoncherry.cookbookkotlin.model.MobRecipe
import simoncherry.cookbookkotlin.model.MobRecipeResult
import simoncherry.cookbookkotlin.mvp.biz.ApiTestBiz
import simoncherry.cookbookkotlin.mvp.contract.ApiTestContract

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/22
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class ApiTestPresenter(var apiTestBiz : ApiTestBiz) : RxPresenter<ApiTestContract.View>(), ApiTestContract.Presenter{

    //lateinit var apiTestBiz : ApiTestBiz

    override fun queryCategory() {
        addSubscribe(
                apiTestBiz.queryCategory(object : ApiCallback.QueryCategoryCallback {
                    override fun onStart() {
                        mView?.onShowProgressBar()
                    }

                    override fun onEnd() {
                        mView?.onHideProgressBar()
                    }

                    override fun onQueryError(msg: String) {
                        mView?.onQueryError(msg)
                    }

                    override fun onQueryCategoryEmpty() {
                        mView?.onQueryEmpty()
                    }

                    override fun onQueryCategorySuccess(value: MobCategoryResult) {
                        mView?.onQueryCategorySuccess(value)
                    }
                }))
    }

    override fun queryRecipe(cid: String, page: Int, size: Int) {
        addSubscribe(
                apiTestBiz.queryRecipe(cid, page, size, object : ApiCallback.QueryRecipeCallback {
                    override fun onStart() {
                        mView?.onShowProgressBar()
                    }

                    override fun onEnd() {
                        mView?.onHideProgressBar()
                    }

                    override fun onQueryError(msg: String) {
                        mView?.onQueryError(msg)
                    }

                    override fun onQueryRecipeSuccess(value: MobRecipeResult) {
                        mView?.onQueryRecipe(value)
                    }

                    override fun onQueryRecipeEmpty() {
                        mView?.onQueryEmpty()
                    }
                })
        )
    }

    override fun queryDetail(id: String) {
        addSubscribe(
                apiTestBiz.queryDetail(id, object : ApiCallback.QueryDetailCallback {
                    override fun onStart() {
                        mView?.onShowProgressBar()
                    }

                    override fun onEnd() {
                        mView?.onHideProgressBar()
                    }

                    override fun onQueryError(msg: String) {
                        mView?.onQueryError(msg)
                    }

                    override fun onQueryDetailSuccess(value: MobRecipe) {
                        mView?.onQueryDetail(value)
                    }

                    override fun onQueryDetailEmpty() {
                        mView?.onQueryEmpty()
                    }
                })
        )
    }
}