package com.simoncherry.cookbookkotlin.mvp.presenter

import com.simoncherry.cookbookkotlin.api.MobAPIService
import com.simoncherry.cookbookkotlin.model.MobAPIResult
import com.simoncherry.cookbookkotlin.model.MobCategoryResult
import com.simoncherry.cookbookkotlin.model.MobRecipe
import com.simoncherry.cookbookkotlin.model.MobRecipeResult
import com.simoncherry.cookbookkotlin.mvp.contract.ApiTestContract
import com.simoncherry.cookbookkotlin.rx.CommonSubscriber
import com.simoncherry.cookbookkotlin.util.RxUtils
import java.util.*

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/22
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class ApiTestPresenter : RxPresenter<ApiTestContract.View>(), ApiTestContract.Presenter {

    override fun queryCategory() {
        addSubscribe(MobAPIService.Companion.getMobAPI()
                .queryCategory(MobAPIService.Companion.MOB_API_KEY)
                .compose(RxUtils.Companion.rxSchedulerHelper())
                .compose(RxUtils.Companion.handleMobResult())
                .subscribeWith(object : CommonSubscriber<MobCategoryResult>(mView) {
                    override fun onNext(t: MobCategoryResult?) {
                        if (t != null) {
                            mView?.onQueryCategorySuccess(t)
                        } else {
                            mView?.onQueryEmpty()
                        }
                    }
                }))
    }

    override fun queryRecipe(cid: String, page: Int, size: Int) {
        val params = HashMap<String, String>()
        params.put("key", MobAPIService.Companion.MOB_API_KEY)
        params.put("cid", cid)
        params.put("page", page.toString())
        params.put("size", size.toString())

        addSubscribe(MobAPIService.Companion.getMobAPI()
                .queryRecipe(params)
                .compose(RxUtils.Companion.rxSchedulerHelper<MobAPIResult<MobRecipeResult>>())
                .compose(RxUtils.Companion.handleMobResult<MobRecipeResult>())
                .subscribeWith(object : CommonSubscriber<MobRecipeResult>(mView) {
                    override fun onNext(t: MobRecipeResult?) {
                        if (t != null) {
                            mView?.onQueryRecipeSuccess(t)
                        } else {
                            mView?.onQueryEmpty()
                        }
                    }
                }))
    }

    override fun queryDetail(id: String) {
        val params = HashMap<String, String>()
        params.put("key", MobAPIService.Companion.MOB_API_KEY)
        params.put("id", id)

        addSubscribe(MobAPIService.Companion.getMobAPI()
                .queryDetail(params)
                .compose(RxUtils.Companion.rxSchedulerHelper<MobAPIResult<MobRecipe>>())
                .compose(RxUtils.Companion.handleMobResult<MobRecipe>())
                .subscribeWith(object : CommonSubscriber<MobRecipe>(mView) {
                    override fun onNext(t: MobRecipe?) {
                        super.onNext(t)
                        if (t != null) {
                            mView?.onQueryDetailSuccess(t)
                        } else {
                            mView?.onQueryEmpty()
                        }
                    }
                }))
    }
}