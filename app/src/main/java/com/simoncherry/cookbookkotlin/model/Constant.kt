package com.simoncherry.cookbookkotlin.model

import com.simoncherry.cookbookkotlin.MyApplication
import java.io.File

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class Constant {
    companion object {
        val SP_NAME = "spCookBook"
        val SP_SAVE_MODE = "saveMode"
        val SP_HISTORY_LIMIT = "historyLimit"

        val DEFAULT_SAVE_MODE = false
        val DEFAULT_HISTORY_LIMIT = 10

        val PATH_DATA = MyApplication.instance.cacheDir.absolutePath + File.separator + "data"
        val PATH_CACHE = PATH_DATA + "/NetCache"
    }
}