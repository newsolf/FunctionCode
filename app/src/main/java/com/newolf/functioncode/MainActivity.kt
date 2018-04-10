package com.newolf.functioncode

import android.support.design.widget.BottomNavigationView
import android.support.v4.content.ContextCompat
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ToastUtils
import com.newolf.functioncode.app.base.BaseActivity
import com.newolf.functioncode.app.utils.helper.DialogHelper
import com.newolf.functioncode.home.HomeFragment
import com.newolf.functioncode.home.OtherFragment
import com.newolf.functioncode.home.SecondFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    var lastClick: Long = 0
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                switchHome()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                LogUtils.e("SecondFragment"+flContent.measuredHeight)
                fragmentManager.beginTransaction().replace(R.id.flContent, SecondFragment()).commit()
                LogUtils.e("SecondFragment"+flContent.measuredHeight)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                LogUtils.e("OtherFragment"+flContent.measuredHeight)
                fragmentManager.beginTransaction().replace(R.id.flContent, OtherFragment()).commit()
                LogUtils.e("OtherFragment"+flContent.measuredHeight)
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }


    override fun bindLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        BarUtils.setStatusBarAlpha(this, 0, true)
        request()


    }


    override fun initListener() {
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        switchHome()
    }

    private fun switchHome() {
        LogUtils.e("switchHome"+flContent.measuredHeight)

        fragmentManager.beginTransaction().replace(R.id.flContent, HomeFragment()).commit()
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
        if (System.currentTimeMillis() - lastClick < 2000) {
            super.onBackPressed()
        } else {
            ToastUtils.setBgColor(ContextCompat.getColor(mContext, R.color.colorAccent))
            ToastUtils.showShort(R.string.exit_app)
        }
        lastClick = System.currentTimeMillis();

    }


}
