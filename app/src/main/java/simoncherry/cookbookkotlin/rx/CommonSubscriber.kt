package simoncherry.cookbookkotlin.rx

import io.reactivex.subscribers.ResourceSubscriber
import simoncherry.cookbookkotlin.api.BaseCallback

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/22
 *     desc   :
 *     version: 1.0
 * </pre>
 */
abstract class CommonSubscriber<T>(var callback: BaseCallback) : ResourceSubscriber<T>() {

    override fun onStart() {
        super.onStart()
        callback.onStart()
    }

    override fun onError(t: Throwable?) {
        callback.onEnd()
        callback.onQueryError(t?.message ?: "未知错误")
    }

    override fun onNext(t: T?) {
        callback.onEnd()
    }

    override fun onComplete() {
        callback.onEnd()
    }
}