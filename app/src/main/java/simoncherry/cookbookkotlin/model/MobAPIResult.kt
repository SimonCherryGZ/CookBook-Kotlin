package simoncherry.cookbookkotlin.model

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/22
 *     desc   :
 *     version: 1.0
 * </pre>
 */
data class MobAPIResult<T>(
        var retCode: Int,
        var msg: String?,
        var result: T?
)