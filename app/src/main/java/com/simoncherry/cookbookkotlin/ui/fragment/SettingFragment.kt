package com.simoncherry.cookbookkotlin.ui.fragment

import android.content.DialogInterface
import android.os.Bundle
import com.simoncherry.cookbookkotlin.R
import com.simoncherry.cookbookkotlin.model.Constant
import com.simoncherry.cookbookkotlin.mvp.contract.SettingContract
import com.simoncherry.cookbookkotlin.mvp.presenter.SettingPresenter
import com.simoncherry.cookbookkotlin.util.DataCleanManager
import com.simoncherry.cookbookkotlin.util.DialogUtils
import com.simoncherry.cookbookkotlin.util.SPUtils
import kotlinx.android.synthetic.main.fragment_setting.*

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class SettingFragment : BaseFragment<SettingContract.View, SettingContract.Presenter>(), SettingContract.View {

    companion object {
        fun newInstance(): SettingFragment {
            val fragment = SettingFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_setting
    }

    override fun initComponent() {
        mPresenter = SettingPresenter(mContext)
        SPUtils.init(mContext, Constant.SP_NAME)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        onShowCacheSize()
    }

    private fun init() {
        initData()
        initView()
    }

    private fun initData() {
        val isSaveMode = SPUtils.getBoolean(Constant.SP_SAVE_MODE, Constant.DEFAULT_SAVE_MODE)
        switch_mode.isChecked = isSaveMode

        val count = SPUtils.getInt(Constant.SP_HISTORY_LIMIT, Constant.DEFAULT_HISTORY_LIMIT)
        setRadioButtonByCount(count)
    }

    private fun initView() {
        switch_mode.setOnCheckedChangeListener({ _, isChecked -> mPresenter?.changeSaveMode(isChecked) })

        radio_group.setOnCheckedChangeListener({ _, checkedId ->
            when (checkedId) {
                R.id.radio_5 -> mPresenter?.changeHistoryLimit(5)
                R.id.radio_10 -> mPresenter?.changeHistoryLimit(10)
                R.id.radio_20 -> mPresenter?.changeHistoryLimit(20)
            }
        })

        layout_clear_search.setOnClickListener { onClearSearchHistory() }

        layout_clear_cache.setOnClickListener { onClearCache() }
    }

    private fun setRadioButtonByCount(count: Int) {
        when (count) {
            5 -> radio_5.isChecked = true
            10 -> radio_10.isChecked = true
            20 -> radio_20.isChecked = true
            else -> radio_10.isChecked = true
        }
    }

    private fun onClearSearchHistory() {
        DialogUtils.showDialog(mContext, "提示", "是否要清除搜索记录", DialogInterface.OnClickListener { dialog, _ ->
            dialog.dismiss()
            mPresenter?.clearSearchHistory()
        })
    }

    private fun onClearCache() {
        DialogUtils.showDialog(mContext, "提示", "是否要清除缓存", DialogInterface.OnClickListener { dialog, _ ->
            dialog.dismiss()
            mPresenter?.clearCache()
        })
    }

    override fun onChangeSaveMode(isSaveMode: Boolean) {
        val txt: String
        if (isSaveMode) {
            txt = "已开启省流量模式"
        } else {
            txt = "已关闭省流量模式"
        }
        onShowToast(txt)
    }

    override fun onShowCacheSize() {
        try {
            val cacheSize = DataCleanManager.getCacheSize(mActivity.cacheDir)
            tv_cache.text = cacheSize
        } catch (e: Exception) {
            e.printStackTrace()
            if (tv_cache != null) {
                val txt = "0.0Byte"
                tv_cache.text = txt
            }
        }
    }

    override fun onClearSearchHistorySuccess() {
        onShowToast("已清除搜索记录")
    }

    override fun onClearCacheSuccess() {
        onShowToast("已清除缓存")
    }
}