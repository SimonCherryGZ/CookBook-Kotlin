package com.simoncherry.cookbookkotlin.mvp.presenter

import com.simoncherry.cookbookkotlin.api.MobAPIService
import com.simoncherry.cookbookkotlin.model.MobRecipeResult
import com.simoncherry.cookbookkotlin.mvp.contract.RecipeContract
import com.simoncherry.cookbookkotlin.rx.CommonSubscriber
import com.simoncherry.cookbookkotlin.util.RxUtils
import java.util.*

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class RecipePresenter : RxPresenter<RecipeContract.View>(), RecipeContract.Presenter{

    override fun queryRecipe(cid: String, page: Int, size: Int) {
        queryRecipeByField("cid", cid, page, size)
    }

    override fun queryRecipeByField(field: String, value: String, page: Int, size: Int) {
        val params = HashMap<String, String>()
        params.put("key", MobAPIService.MOB_API_KEY)
        params.put(field, value)
        params.put("page", page.toString())
        params.put("size", size.toString())

        addSubscribe(MobAPIService.getMobAPI()
                .queryRecipe(params)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleMobResult())
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
}