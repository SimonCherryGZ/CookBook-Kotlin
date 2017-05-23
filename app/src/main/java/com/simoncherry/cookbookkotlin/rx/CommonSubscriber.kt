package com.simoncherry.cookbookkotlin.rx

import com.simoncherry.cookbookkotlin.ui.BaseView
import io.reactivex.subscribers.ResourceSubscriber

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/22
 *     desc   :
 *     version: 1.0
 * </pre>
 */
abstract class CommonSubscriber<T>(var view: BaseView?) : ResourceSubscriber<T>() {

    override fun onStart() {
        super.onStart()
        view?.onShowProgressBar()
    }

    override fun onError(t: Throwable?) {
        view?.onHideProgressBar()
        view?.onQueryError(t?.message ?: "未知错误")
    }

    override fun onNext(t: T?) {
        view?.onHideProgressBar()
    }

    override fun onComplete() {
        view?.onHideProgressBar()
    }
}