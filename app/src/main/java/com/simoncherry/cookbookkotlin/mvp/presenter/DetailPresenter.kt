package com.simoncherry.cookbookkotlin.mvp.presenter

import com.simoncherry.cookbookkotlin.api.MobAPIService
import com.simoncherry.cookbookkotlin.model.MobRecipe
import com.simoncherry.cookbookkotlin.mvp.contract.DetailContract
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
class DetailPresenter : RxPresenter<DetailContract.View>(), DetailContract.Presenter{

    override fun queryDetail(id: String) {
        val params = HashMap<String, String>()
        params.put("key", MobAPIService.Companion.MOB_API_KEY)
        params.put("id", id)

        addSubscribe(MobAPIService.Companion.getMobAPI()
                .queryDetail(params)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleMobResult())
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