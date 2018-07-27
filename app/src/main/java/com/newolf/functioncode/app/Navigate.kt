package com.newolf.functioncode.app

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.net.Uri
import com.blankj.utilcode.util.LogUtils
import com.newolf.functioncode.MainActivity
import com.newolf.functioncode.activity.QuittingTimeActivity
import com.newolf.functioncode.activity.ZxingScanActivity
import com.newolf.functioncode.fingerprint.FingerprintHelperActivity
import com.newolf.functioncode.fingerprint.FingerprintSettingActivity


/**
 * ================================================
 * @author : NeWolf
 * @version : 1.0
 * @date :  2018/3/15
 * 描述:
 * 历史:<br/>
 * ================================================
 */
object Navigate {
    private fun startActivity(context: Context?, clazz: Class<*>) {
        context?.startActivity(Intent(context, clazz))
    }

    fun startInnerH5(content: Context?, url: String) {
        LogUtils.e(url)
        content?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)).setFlags(FLAG_ACTIVITY_CLEAR_TASK))
    }

    fun startMainActivity(content: Context) {
        content.startActivity(Intent(content, MainActivity::class.java).setFlags(FLAG_ACTIVITY_CLEAR_TASK))
    }

    fun startFingerprintSettingActivity(context: Context?) {
        startActivity(context, FingerprintSettingActivity::class.java)
    }

    fun startZxingScanActivity(context: Context?) {
        startActivity(context, ZxingScanActivity::class.java)
    }

    fun startFingerprintHelperActivity(context: Context?) {
        startActivity(context, FingerprintHelperActivity::class.java)
    }

    fun startQuittingTimeActivity(context: Context?) {
        startActivity(context, QuittingTimeActivity::class.java)
    }
}