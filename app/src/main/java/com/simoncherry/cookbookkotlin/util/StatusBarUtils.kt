package com.simoncherry.cookbookkotlin.util

import android.annotation.TargetApi
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.readystatesoftware.systembartint.SystemBarTintManager

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class StatusBarUtils {

    companion object {
        /**
         * 修改状态栏为全透明
         * @param activity
         */
        @TargetApi(19)
        fun transparencyBar(activity: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = activity.window
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = Color.TRANSPARENT
                window.navigationBarColor = Color.TRANSPARENT
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val window = activity.window
                window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }

        /**
         * 修改状态栏颜色，支持4.4以上版本
         * @param activity
         * *
         * @param colorId
         */
        fun setStatusBarColor(activity: Activity, colorId: Int) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = activity.window
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = activity.resources.getColor(colorId)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //使用SystemBarTint库使4.4版本状态栏变色，需要先将状态栏设置为透明
                transparencyBar(activity)
                val tintManager = SystemBarTintManager(activity)
                tintManager.isStatusBarTintEnabled = true
                tintManager.setStatusBarTintResource(colorId)
            }
        }

        /**
         * 设置状态栏黑色字体图标，
         * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
         * @param activity
         * *
         * @return 1:MIUUI 2:Flyme 3:android6.0
         */
        fun StatusBarLightMode(activity: Activity): Int {
            var result = 0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (MIUISetStatusBarLightMode(activity.window, true)) {
                    result = 1
                } else if (FlymeSetStatusBarLightMode(activity.window, true)) {
                    result = 2
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    result = 3
                }
            }
            return result
        }

        /**
         * 已知系统类型时，设置状态栏黑色字体图标。
         * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
         * @param activity
         * *
         * @param type 1:MIUUI 2:Flyme 3:android6.0
         */
        fun StatusBarLightMode(activity: Activity, type: Int) {
            if (type == 1) {
                MIUISetStatusBarLightMode(activity.window, true)
            } else if (type == 2) {
                FlymeSetStatusBarLightMode(activity.window, true)
            } else if (type == 3) {
                activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }

        }

        /**
         * 清除MIUI或flyme或6.0以上版本状态栏黑色字体
         */
        fun StatusBarDarkMode(activity: Activity, type: Int) {
            if (type == 1) {
                MIUISetStatusBarLightMode(activity.window, false)
            } else if (type == 2) {
                FlymeSetStatusBarLightMode(activity.window, false)
            } else if (type == 3) {
                activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            }
        }

        /**
         * 设置状态栏图标为深色和魅族特定的文字风格
         * 可以用来判断是否为Flyme用户
         * @param window 需要设置的窗口
         * *
         * @param dark 是否把状态栏字体及图标颜色设置为深色
         * *
         * @return  boolean 成功执行返回true
         */
        fun FlymeSetStatusBarLightMode(window: Window?, dark: Boolean): Boolean {
            var result = false
            if (window != null) {
                try {
                    val lp = window.attributes
                    val darkFlag = WindowManager.LayoutParams::class.java
                            .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                    val meizuFlags = WindowManager.LayoutParams::class.java
                            .getDeclaredField("meizuFlags")
                    darkFlag.isAccessible = true
                    meizuFlags.isAccessible = true
                    val bit = darkFlag.getInt(null)
                    var value = meizuFlags.getInt(lp)
                    if (dark) {
                        value = value or bit
                    } else {
                        value = value and bit.inv()
                    }
                    meizuFlags.setInt(lp, value)
                    window.attributes = lp
                    result = true
                } catch (e: Exception) {

                }

            }
            return result
        }

        /**
         * 设置状态栏字体图标为深色，需要MIUIV6以上
         * @param window 需要设置的窗口
         * *
         * @param dark 是否把状态栏字体及图标颜色设置为深色
         * *
         * @return  boolean 成功执行返回true
         */
        fun MIUISetStatusBarLightMode(window: Window?, dark: Boolean): Boolean {
            var result = false
            if (window != null) {
                val clazz = window.javaClass
                try {
                    var darkModeFlag = 0
                    val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                    val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                    darkModeFlag = field.getInt(layoutParams)
                    val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                    if (dark) {
                        extraFlagField.invoke(window, darkModeFlag, darkModeFlag)//状态栏透明且黑色字体
                    } else {
                        extraFlagField.invoke(window, 0, darkModeFlag)//清除黑色字体
                    }
                    result = true
                } catch (e: Exception) {

                }

            }
            return result
        }
    }
}