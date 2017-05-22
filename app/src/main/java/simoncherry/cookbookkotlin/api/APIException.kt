package simoncherry.cookbookkotlin.api

import java.lang.RuntimeException

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/22
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class APIException(var code: Int, var msg: String) : RuntimeException()