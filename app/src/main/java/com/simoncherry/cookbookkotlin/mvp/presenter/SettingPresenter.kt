package com.simoncherry.cookbookkotlin.mvp.presenter

import android.content.Context
import android.provider.SearchRecentSuggestions
import com.simoncherry.cookbookkotlin.model.Constant
import com.simoncherry.cookbookkotlin.mvp.contract.SettingContract
import com.simoncherry.cookbookkotlin.util.DataCleanManager
import com.simoncherry.cookbookkotlin.util.MySuggestionProvider
import com.simoncherry.cookbookkotlin.util.RealmHelper
import com.simoncherry.cookbookkotlin.util.SPUtils

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class SettingPresenter(
        val mContext: Context) : RxPresenter<SettingContract.View>(), SettingContract.Presenter{

    override fun changeSaveMode(isSaveMode: Boolean) {
        SPUtils.put(Constant.SP_SAVE_MODE, isSaveMode)
        mView?.onChangeSaveMode(isSaveMode)
    }

    override fun changeHistoryLimit(limit: Int) {
        SPUtils.put(Constant.SP_HISTORY_LIMIT, limit)
        RealmHelper.deleteMultiHistoryAsync(limit)
    }

    override fun clearSearchHistory() {
        val suggestions = SearchRecentSuggestions(mContext,
                MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE)
        suggestions.clearHistory()
        mView?.onClearSearchHistorySuccess()
    }

    override fun clearCache() {
        DataCleanManager.cleanInternalCache(mContext)
        mView?.onClearCacheSuccess()
        mView?.onShowCacheSize()
    }
}