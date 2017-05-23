package simoncherry.cookbookkotlin.mvp.biz

import io.reactivex.disposables.Disposable
import simoncherry.cookbookkotlin.api.ApiCallback
import simoncherry.cookbookkotlin.api.MobAPIService
import simoncherry.cookbookkotlin.model.MobAPIResult
import simoncherry.cookbookkotlin.model.MobCategoryResult
import simoncherry.cookbookkotlin.model.MobRecipe
import simoncherry.cookbookkotlin.model.MobRecipeResult
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
class ApiTestBiz : BaseBiz(), ApiCallback{

    fun queryCategory(callback: ApiCallback.QueryCategoryCallback): Disposable {
        checkNotNull(callback)

        return MobAPIService.getMobAPI()
                .queryCategory(MobAPIService.MOB_API_KEY)
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleMobResult())
                .subscribeWith(object : CommonSubscriber<MobCategoryResult>(callback) {
                    override fun onNext(t: MobCategoryResult?) {
                        if (t != null) {
                            callback.onQueryCategorySuccess(t)
                        } else {
                            callback.onQueryCategoryEmpty()
                        }
                    }
                })
    }

    fun queryRecipe(cid: String, page: Int, size: Int, callback: ApiCallback.QueryRecipeCallback): Disposable {
        checkNotNull(callback)

        val params = HashMap<String, String>()
        params.put("key", MobAPIService.MOB_API_KEY)
        params.put("cid", cid)
        params.put("page", page.toString())
        params.put("size", size.toString())

        return MobAPIService.getMobAPI()
                .queryRecipe(params)
                .compose(RxUtils.rxSchedulerHelper<MobAPIResult<MobRecipeResult>>())
                .compose(RxUtils.handleMobResult<MobRecipeResult>())
                .subscribeWith(object : CommonSubscriber<MobRecipeResult>(callback) {
                    override fun onNext(t: MobRecipeResult?) {
                        if (t != null) {
                            callback.onQueryRecipeSuccess(t)
                        } else {
                            callback.onQueryRecipeEmpty()
                        }
                    }
                })

    }

    fun queryDetail(id: String, callback: ApiCallback.QueryDetailCallback): Disposable {
        checkNotNull(callback)

        val params = HashMap<String, String>()
        params.put("key", MobAPIService.MOB_API_KEY)
        params.put("id", id)

        return MobAPIService.getMobAPI()
                .queryDetail(params)
                .compose(RxUtils.rxSchedulerHelper<MobAPIResult<MobRecipe>>())
                .compose(RxUtils.handleMobResult<MobRecipe>())
                .subscribeWith(object : CommonSubscriber<MobRecipe>(callback) {
                    override fun onNext(t: MobRecipe?) {
                        super.onNext(t)
                        if (t != null) {
                            callback.onQueryDetailSuccess(t)
                        } else {
                            callback.onQueryDetailEmpty()
                        }
                    }
                })
    }
}