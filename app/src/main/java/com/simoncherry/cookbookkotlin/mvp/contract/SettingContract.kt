package com.simoncherry.cookbookkotlin.mvp.contract

import com.simoncherry.cookbookkotlin.mvp.presenter.BasePresenter
import com.simoncherry.cookbookkotlin.ui.BaseView

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
interface SettingContract {
    interface View : BaseView {
        fun onChangeSaveMode(isSaveMode: Boolean)
        fun onShowCacheSize()
        fun onClearSearchHistorySuccess()
        fun onClearCacheSuccess()
    }

    interface Presenter : BasePresenter<View> {
        fun changeSaveMode(isSaveMode: Boolean)
        fun changeHistoryLimit(limit: Int)
        fun clearSearchHistory()
        fun clearCache()
    }
}