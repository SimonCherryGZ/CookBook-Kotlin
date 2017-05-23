package com.simoncherry.cookbookkotlin.mvp.presenter

import com.simoncherry.cookbookkotlin.api.MobAPIService
import com.simoncherry.cookbookkotlin.model.MobCategoryResult
import com.simoncherry.cookbookkotlin.mvp.contract.SplashContract
import com.simoncherry.cookbookkotlin.rx.CommonSubscriber
import com.simoncherry.cookbookkotlin.util.RxUtils.Companion.handleMobResult
import com.simoncherry.cookbookkotlin.util.RxUtils.Companion.rxSchedulerHelper

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class SplashPresenter : RxPresenter<SplashContract.View>(), SplashContract.Presenter{

    override fun queryCategory() {
        addSubscribe(MobAPIService.Companion.getMobAPI()
                .queryCategory(MobAPIService.Companion.MOB_API_KEY)
                .compose(rxSchedulerHelper())
                .compose(handleMobResult())
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
}