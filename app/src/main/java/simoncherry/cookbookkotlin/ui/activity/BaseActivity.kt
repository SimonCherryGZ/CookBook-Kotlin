package simoncherry.cookbookkotlin.ui.activity

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import butterknife.ButterKnife
import butterknife.Unbinder
import simoncherry.cookbookkotlin.mvp.presenter.BasePresenter
import simoncherry.cookbookkotlin.ui.BaseView

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/22
 *     desc   :
 *     version: 1.0
 * </pre>
 */
abstract class BaseActivity<in V : BaseView, T : BasePresenter<V>> : AppCompatActivity(), BaseView {
    var mPresenter: T? = null
    lateinit var mContext: Activity
    lateinit var mUnBinder: Unbinder
    var mProgressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        mUnBinder = ButterKnife.bind(this)
        mContext = this
        initComponent()
        mPresenter?.attachView(this as V)
    }

    protected abstract fun getLayout(): Int
    protected abstract fun initComponent()

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
        mUnBinder.unbind()
    }

    private fun initProgressBar() {
        mProgressBar = ProgressBar(this, null, android.R.attr.progressBarStyleLarge)
        val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.gravity = Gravity.CENTER
        addContentView(mProgressBar, layoutParams)
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
}