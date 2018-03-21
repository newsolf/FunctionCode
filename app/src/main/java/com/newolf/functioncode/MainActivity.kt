package com.newolf.functioncode

import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ToastUtils
import com.newolf.functioncode.app.Navigate
import com.newolf.functioncode.app.base.BaseActivity
import com.newolf.functioncode.app.configs.Constants
import com.newolf.functioncode.app.helper.DialogHelper
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    var lastClick : Long = 0
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_home)
                ToastUtils.showShort(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                ToastUtils.showShort(BuildConfig.VERSION_NAME)
                message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    val mListener = NavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.actionGitHub -> {
                Navigate.startInnerH5(mContext, Constants.GIT_HUB)
                return@OnNavigationItemSelectedListener true
            }
            R.id.actionBlog -> {
                Navigate.startInnerH5(mContext, Constants.BLOG)
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }



    override fun bindLayout(): Int {
        return R.layout.activity_main
//        return R.layout.activity_test
    }

    override fun initView() {
        BarUtils.setStatusBarAlpha(this,0,true)
        request()
    }



    override fun initListener() {
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigationView.setNavigationItemSelectedListener(mListener)
    }


    private fun request() = PermissionUtils.permission(PermissionConstants.STORAGE)
            .rationale { shouldRequest -> DialogHelper.showRationaleDialog(shouldRequest) }
            .rationale { shouldRequest -> ToastUtils.showLong(shouldRequest.toString()) }
            .callback(object : PermissionUtils.FullCallback {
                override fun onGranted(permissionsGranted: List<String>) {
                    LogUtils.d(permissionsGranted)
                }

                override fun onDenied(permissionsDeniedForever: List<String>,
                                      permissionsDenied: List<String>) {
                    if (!permissionsDeniedForever.isEmpty()) {
                        DialogHelper.showOpenAppSettingDialog()
                        ToastUtils.showShort("没有获得权限")
                    }
                    LogUtils.d(permissionsDeniedForever, permissionsDenied)
                }
            })
            .request()

    override fun onBackPressed() {
        if (System.currentTimeMillis() - lastClick<2000){
            super.onBackPressed()
        }else{
            ToastUtils.setBgColor(ContextCompat.getColor(mContext,R.color.colorAccent))
            ToastUtils.showShort(R.string.exit_app)
        }
         lastClick = System.currentTimeMillis();

    }
}
