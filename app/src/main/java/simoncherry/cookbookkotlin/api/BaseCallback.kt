package simoncherry.cookbookkotlin.api

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/22
 *     desc   :
 *     version: 1.0
 * </pre>
 */
interface BaseCallback {
    fun onStart()
    fun onEnd()
    fun onQueryError(msg: String)
}