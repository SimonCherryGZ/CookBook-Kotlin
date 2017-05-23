package com.simoncherry.cookbookkotlin.ui

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/22
 *     desc   :
 *     version: 1.0
 * </pre>
 */
interface BaseView {
    fun onQueryEmpty()
    fun onQueryError(msg: String)
    fun onShowProgressBar()
    fun onHideProgressBar()
    fun onShowToast(msg: String)
}