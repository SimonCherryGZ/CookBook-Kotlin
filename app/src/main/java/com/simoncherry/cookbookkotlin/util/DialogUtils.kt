package com.simoncherry.cookbookkotlin.util

import android.content.Context
import android.content.DialogInterface
import android.view.View

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class DialogUtils {

    companion object {
        fun showDialog(
                context: Context, title: String, message: String,
                view: View?,
                positiveCallback: DialogInterface.OnClickListener,
                negativeCallback: DialogInterface.OnClickListener?) {
            val builder = android.support.v7.app.AlertDialog.Builder(context)
            builder.setTitle(title)
            builder.setMessage(message)
            if (view != null) {
                view.setPadding(60, 20, 60, 20)
                //builder.setView(view, 60, 20, 60, 20)
                builder.setView(view)
            }
            builder.setPositiveButton("确定", positiveCallback)
            builder.setNegativeButton("取消", negativeCallback)
            builder.show()
        }

        fun showDialog(
                context: Context, title: String, message: String,
                positiveCallback: DialogInterface.OnClickListener,
                negativeCallback: DialogInterface.OnClickListener?) {
            showDialog(context, title, message, null, positiveCallback, negativeCallback)
        }

        fun showDialog(
                context: Context, title: String, message: String, view: View,
                positiveCallback: DialogInterface.OnClickListener) {
            showDialog(context, title, message, view, positiveCallback, null)
        }

        fun showDialog(
                context: Context, title: String, message: String,
                positiveCallback: DialogInterface.OnClickListener) {
            showDialog(context, title, message, positiveCallback, null)
        }
    }
}