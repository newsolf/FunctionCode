package com.newolf.functioncode.app.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder


/**
 * ================================================
 * @author : NeWolf
 * @version : 1.0
 * date :  2018/7/27
 * desc:
 * history:
 * ================================================
 */
object GsonHelper {
    private val sGson: Gson = GsonBuilder()
            .create()

    fun getDefault(): Gson {
        return sGson
    }


    fun <T> jsonToBean(jsonResult: String, clz: Class<T>): T {
        return getDefault().fromJson(jsonResult, clz)
    }


    fun <T> listToJsonString(list: List<T>?): String {
        return if (list == null) "null" else getDefault().toJson(list)
    }
}