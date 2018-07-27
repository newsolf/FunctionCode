package com.newolf.functioncode.app.utils

import android.text.TextUtils
import com.blankj.utilcode.util.SPUtils
import com.newolf.functioncode.data.QuittingTimeSetting

/**
 * ================================================
 * @author : NeWolf
 * @version : 1.0
 * date :  2018/7/27
 * desc:
 * history:
 * ================================================
 */
object QuittingTimeUtils {
    private const val spName = "QuittingTimeSetting"
    lateinit var setting: QuittingTimeSetting


    fun loadData() {
        val spString = SPUtils.getInstance(spName).getString(spName)
        if (!TextUtils.isEmpty(spString)) {
            val spSetting = GsonHelper.jsonToBean(spString, QuittingTimeSetting::class.java)
            setting = spSetting
        } else {
            setting = QuittingTimeSetting(false,0)
            loadDataFromSD()

        }


    }

    private fun loadDataFromSD() {

    }


    fun saveStartTime(){
        setting = QuittingTimeSetting(true,System.currentTimeMillis())
        val saveString = GsonHelper.getDefault().toJson(setting)
        SPUtils.getInstance(spName).put(spName,saveString)
    }


    fun reset(){
        SPUtils.getInstance(spName).clear()
        setting = QuittingTimeSetting(false,0)
    }
}