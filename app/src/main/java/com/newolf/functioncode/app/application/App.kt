package com.newolf.functioncode.app.application

import android.app.Application
import cn.jpush.android.api.JPushInterface
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.newolf.functioncode.BuildConfig
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta

/**
 * ================================================
 * @author : NeWolf
 * @version : 1.0
 * @date :  2018/3/15
 * 描述:
 * 历史:<br/>
 * ================================================
 */
open class App :Application() {
    override fun onCreate() {
        super.onCreate()
        initUtils(this)
        initBugly(this)
        initPush(this)
    }



    private fun initUtils(app: App) {
        Utils.init(this)
        LogUtils.getConfig().setLogSwitch(BuildConfig.DEBUG)
    }

    private fun initBugly(context: Application) {
        //        CrashReport.setAppChannel(context, getChannel());
        Bugly.init(context, BuildConfig.BUGLY_APP_ID, BuildConfig.DEBUG)
        Beta.autoInit = true
        Beta.autoDownloadOnWifi = true


        //自定义升级弹窗
//        Beta.upgradeDialogLayoutId = R.layout.upgrade_dialog;
    }

    private fun initPush(app: App) {
        JPushInterface.setDebugMode(BuildConfig.DEBUG)
        JPushInterface.init(this)
    }
}