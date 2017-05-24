package com.simoncherry.cookbookkotlin.ui.fragment

import android.R
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.simoncherry.cookbookkotlin.mvp.presenter.BasePresenter
import com.simoncherry.cookbookkotlin.ui.BaseView
import com.simoncherry.cookbookkotlin.util.ToastUtils

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
abstract class BaseFragment<in V : BaseView, T : BasePresenter<V>> : Fragment(), BaseView {
    protected var mPresenter: T? = null
    lateinit var mActivity: Activity
    lateinit var mContext: Context
    private var mProgressBar: ProgressBar? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLayout(), container, false)
        initComponent()
        if (mPresenter != null) {
            mPresenter?.attachView(this as V)
        }
        return view
    }

    protected abstract fun getLayout(): Int
    protected abstract fun initComponent()

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter?.detachView()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as Activity
        mContext = context
    }

    private fun initProgressBar() {
        mProgressBar = ProgressBar(mContext, null, R.attr.progressBarStyleLarge)
        val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.CENTER
        mActivity.addContentView(mProgressBar, layoutParams)
    }

    override fun onShowProgressBar() {
        if (mProgressBar == null) {
            initProgressBar()
        }
        mProgressBar?.isIndeterminate = true
        mProgressBar?.visibility = View.VISIBLE
    }

    override fun onHideProgressBar() {
        mProgressBar?.visibility = View.GONE
    }

    override fun onShowToast(msg: String) {
        ToastUtils.show(mContext, msg)
    }

    override fun onQueryEmpty() {
        onShowToast("查询结果为空")
    }

    override fun onQueryError(msg: String) {
        onShowToast(msg)
    }
}