package com.simoncherry.cookbookkotlin.util

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.support.annotation.StringRes
import android.widget.Toast


/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class ToastUtils {
    companion object {
        private var sToast: Toast? = null
        private val sHandler = Handler(Looper.getMainLooper())

        fun cancel() {
            sToast?.cancel()
            sToast = null
        }

        fun show(context: Context, text: CharSequence, duration: Int) {
            sHandler.post {
                cancel()
                sToast = Toast.makeText(context, text, duration)
                sToast?.show()
            }
        }

        fun show(context: Context, @StringRes resId: Int, duration: Int) {
            show(context, context.resources.getText(resId).toString(), duration)
        }

        fun show(context: Context, text: CharSequence) {
            show(context, text, Toast.LENGTH_SHORT)
        }

        fun show(context: Context, @StringRes resId: Int) {
            show(context, resId, Toast.LENGTH_SHORT)
        }
    }
}