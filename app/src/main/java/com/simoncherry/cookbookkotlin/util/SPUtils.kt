package com.simoncherry.cookbookkotlin.util

import android.content.Context
import android.content.SharedPreferences

/**
 * <pre>
 *     author : Donald
 *     e-mail : xxx@xx
 *     time   : 2017/05/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class SPUtils private constructor() {

    companion object {
        private lateinit var sp: SharedPreferences
        private lateinit var editor: SharedPreferences.Editor

        private var instance : SPUtils? = null

        fun init(context: Context, spName: String) {
            if (instance == null) {
                instance = SPUtils()
                sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
                editor = sp.edit()
                editor.apply()
            }
        }

        /**
         * SPUtils构造函数
         *
         * 在Application中初始化

         * @param spName spName
         */
//        fun SPUtils(context: Context, spName: String){
//            sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE)
//            editor = sp.edit()
//            editor.apply()
//        }

        /**
         * SP中写入String类型value

         * @param key   键
         * *
         * @param value 值
         */
        fun put(key: String, value: String?) {
            editor.putString(key, value).apply()
        }

        /**
         * SP中读取String

         * @param key 键
         * *
         * @return 存在返回对应值，不存在返回默认值`null`
         */
        fun getString(key: String): String {
            return getString(key, null)
        }

        /**
         * SP中读取String

         * @param key          键
         * *
         * @param defaultValue 默认值
         * *
         * @return 存在返回对应值，不存在返回默认值`defaultValue`
         */
        fun getString(key: String, defaultValue: String?): String {
            return sp.getString(key, defaultValue)
        }

        /**
         * SP中写入int类型value

         * @param key   键
         * *
         * @param value 值
         */
        fun put(key: String, value: Int) {
            editor.putInt(key, value).apply()
        }

        /**
         * SP中读取int

         * @param key 键
         * *
         * @return 存在返回对应值，不存在返回默认值-1
         */
        fun getInt(key: String): Int {
            return getInt(key, -1)
        }

        /**
         * SP中读取int

         * @param key          键
         * *
         * @param defaultValue 默认值
         * *
         * @return 存在返回对应值，不存在返回默认值`defaultValue`
         */
        fun getInt(key: String, defaultValue: Int): Int {
            return sp.getInt(key, defaultValue)
        }

        /**
         * SP中写入long类型value

         * @param key   键
         * *
         * @param value 值
         */
        fun put(key: String, value: Long) {
            editor.putLong(key, value).apply()
        }

        /**
         * SP中读取long

         * @param key 键
         * *
         * @return 存在返回对应值，不存在返回默认值-1
         */
        fun getLong(key: String): Long {
            return getLong(key, -1L)
        }

        /**
         * SP中读取long

         * @param key          键
         * *
         * @param defaultValue 默认值
         * *
         * @return 存在返回对应值，不存在返回默认值`defaultValue`
         */
        fun getLong(key: String, defaultValue: Long): Long {
            return sp.getLong(key, defaultValue)
        }

        /**
         * SP中写入float类型value

         * @param key   键
         * *
         * @param value 值
         */
        fun put(key: String, value: Float) {
            editor.putFloat(key, value).apply()
        }

        /**
         * SP中读取float

         * @param key 键
         * *
         * @return 存在返回对应值，不存在返回默认值-1
         */
        fun getFloat(key: String): Float {
            return getFloat(key, -1f)
        }

        /**
         * SP中读取float

         * @param key          键
         * *
         * @param defaultValue 默认值
         * *
         * @return 存在返回对应值，不存在返回默认值`defaultValue`
         */
        fun getFloat(key: String, defaultValue: Float): Float {
            return sp.getFloat(key, defaultValue)
        }

        /**
         * SP中写入boolean类型value

         * @param key   键
         * *
         * @param value 值
         */
        fun put(key: String, value: Boolean) {
            editor.putBoolean(key, value).apply()
        }

        /**
         * SP中读取boolean

         * @param key 键
         * *
         * @return 存在返回对应值，不存在返回默认值`false`
         */
        fun getBoolean(key: String): Boolean {
            return getBoolean(key, false)
        }

        /**
         * SP中读取boolean

         * @param key          键
         * *
         * @param defaultValue 默认值
         * *
         * @return 存在返回对应值，不存在返回默认值`defaultValue`
         */
        fun getBoolean(key: String, defaultValue: Boolean): Boolean {
            return sp.getBoolean(key, defaultValue)
        }

        /**
         * SP中写入String集合类型value

         * @param key    键
         * *
         * @param values 值
         */
        fun put(key: String, values: Set<String>?) {
            editor.putStringSet(key, values).apply()
        }

        /**
         * SP中读取StringSet

         * @param key 键
         * *
         * @return 存在返回对应值，不存在返回默认值`null`
         */
        fun getStringSet(key: String): Set<String> {
            return getStringSet(key, null)
        }

        /**
         * SP中读取StringSet

         * @param key          键
         * *
         * @param defaultValue 默认值
         * *
         * @return 存在返回对应值，不存在返回默认值`defaultValue`
         */
        fun getStringSet(key: String, defaultValue: Set<String>?): Set<String> {
            return sp.getStringSet(key, defaultValue)
        }

        /**
         * SP中获取所有键值对

         * @return Map对象
         */
        fun getAll(): Map<String, *> {
            return sp.all
        }

        /**
         * SP中移除该key

         * @param key 键
         */
        fun remove(key: String) {
            editor.remove(key).apply()
        }

        /**
         * SP中是否存在该key

         * @param key 键
         * *
         * @return `true`: 存在<br></br>`false`: 不存在
         */
        operator fun contains(key: String): Boolean {
            return sp.contains(key)
        }

        /**
         * SP中清除所有数据
         */
        fun clear() {
            editor.clear().apply()
        }
    }
}