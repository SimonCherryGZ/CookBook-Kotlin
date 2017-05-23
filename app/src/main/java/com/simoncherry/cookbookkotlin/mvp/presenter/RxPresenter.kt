package com.simoncherry.cookbookkotlin.mvp.presenter

import com.simoncherry.cookbookkotlin.ui.BaseView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/22
 *     desc   :
 *     version: 1.0
 * </pre>
 */
open class RxPresenter<V : BaseView> : BasePresenter<V> {
    var mView: V? = null
    var mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun addSubscribe(subscription: Disposable) {
        mCompositeDisposable.add(subscription)
    }

    protected fun unSubscribe() {
        mCompositeDisposable.dispose()
    }

    override fun attachView(view: V) {
        mView = view
    }

    override fun detachView() {
        mView = null
        unSubscribe()
    }
}