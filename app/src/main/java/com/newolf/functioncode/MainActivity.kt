package com.newolf.functioncode

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ToastUtils
import com.newolf.functioncode.app.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*





class MainActivity : BaseActivity() {

    var context:Context = this;
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        request()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun request() {

        PermissionUtils.permission(PermissionConstants.STORAGE)
//                .rationale { shouldRequest -> DialogHelper.showRationaleDialog(shouldRequest) }
                .rationale { shouldRequest -> ToastUtils.showLong(shouldRequest.toString()) }
                .callback(object : PermissionUtils.FullCallback {
                    override fun onGranted(permissionsGranted: List<String>) {
                        LogUtils.d(permissionsGranted)
                    }

                    override fun onDenied(permissionsDeniedForever: List<String>,
                                          permissionsDenied: List<String>) {
                        if (!permissionsDeniedForever.isEmpty()) {
//                            DialogHelper.showOpenAppSettingDialog()
                            ToastUtils.showShort("没有获得权限")
                        }
                        LogUtils.d(permissionsDeniedForever, permissionsDenied)
                    }
                })
                .request()


    }
}
