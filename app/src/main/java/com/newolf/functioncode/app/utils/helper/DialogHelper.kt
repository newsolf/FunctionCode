package com.newolf.functioncode.app.utils.helper

import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.PermissionUtils
import com.newolf.functioncode.R

/**
 * ================================================
 * @author : NeWolf
 * @version : 1.0
 * @date :  2018/3/20
 * 描述:
 * 历史:<br/>
 * ================================================
 */
object DialogHelper {
    fun showRationaleDialog(shouldRequest: PermissionUtils.OnRationaleListener.ShouldRequest) {
        val topActivity = ActivityUtils.getTopActivity() ?: return
        AlertDialog.Builder(topActivity)
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage(R.string.permission_rationale_message)
                .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, which -> shouldRequest.again(true) })
                .setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialog, which -> shouldRequest.again(false) })
                .setCancelable(false)
                .create()
                .show()

    }

    fun showOpenAppSettingDialog() {
        val topActivity = ActivityUtils.getTopActivity() ?: return
        AlertDialog.Builder(topActivity)
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage(R.string.permission_denied_forever_message)
                .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, witch -> PermissionUtils.launchAppDetailsSettings() })
                .setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener { dialog, witch -> })
                .setCancelable(false)
                .create()
                .show()

    }


}