package simoncherry.cookbookkotlin.mvp.presenter

import simoncherry.cookbookkotlin.api.MobAPIService
import simoncherry.cookbookkotlin.model.MobAPIResult
import simoncherry.cookbookkotlin.model.MobCategoryResult
import simoncherry.cookbookkotlin.model.MobRecipe
import simoncherry.cookbookkotlin.model.MobRecipeResult
import simoncherry.cookbookkotlin.mvp.contract.ApiTestContract
import simoncherry.cookbookkotlin.rx.CommonSubscriber
import simoncherry.cookbookkotlin.util.RxUtils
import java.util.HashMap

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/22
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class ApiTestPresenter : RxPresenter<ApiTestContract.View>(), ApiTestContract.Presenter{

    override fun queryCategory() {
        addSubscribe(MobAPIService.getMobAPI()
                .queryCategory(MobAPIService.MOB_API_KEY)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleMobResult())
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
        params.put("key", MobAPIService.MOB_API_KEY)
        params.put("cid", cid)
        params.put("page", page.toString())
        params.put("size", size.toString())

        addSubscribe(MobAPIService.getMobAPI()
                .queryRecipe(params)
                .compose(RxUtils.rxSchedulerHelper<MobAPIResult<MobRecipeResult>>())
                .compose(RxUtils.handleMobResult<MobRecipeResult>())
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
        params.put("key", MobAPIService.MOB_API_KEY)
        params.put("id", id)

        addSubscribe(MobAPIService.getMobAPI()
                .queryDetail(params)
                .compose(RxUtils.rxSchedulerHelper<MobAPIResult<MobRecipe>>())
                .compose(RxUtils.handleMobResult<MobRecipe>())
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