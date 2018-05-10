package com.newolf.functioncode

import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ToastUtils
import com.newolf.functioncode.app.base.BaseDrawerActivity
import com.newolf.functioncode.app.utils.helper.DialogHelper
import com.newolf.functioncode.home.HomeFragment
import com.newolf.functioncode.home.OtherFragment
import com.newolf.functioncode.home.SecondFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseDrawerActivity() {
    var lastClick: Long = 0
    var curIndex: Int = 0
    internal var mFragments = mutableListOf<Fragment>()
    private val mOnNavigationItemSelectedListener = OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                LogUtils.e("navigation_home")
                switchHome()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                LogUtils.e("navigation_dashboard")
                showCurrentFragment(1)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                LogUtils.e("navigation_notifications")
                showCurrentFragment(2)
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }


    override fun bindLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        request()

        mFragments.add(HomeFragment())
        mFragments.add(SecondFragment())
        mFragments.add(OtherFragment())

        FragmentUtils.add(supportFragmentManager, mFragments, R.id.flContent, curIndex)
    }


    override fun initListener() {
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
//        switchHome()
    }

    private fun switchHome() {
        LogUtils.e("switchHome" + flContent.measuredHeight)

//        fragmentManager.beginTransaction().replace(R.id.flContent, HomeFragment()).commit()
//        FragmentUtils.add(supportFragmentManager, HomeFragment(),R.id.flContent)
        showCurrentFragment(0)
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

    fun showCurrentFragment(index: Int) {
        curIndex = index
        FragmentUtils.showHide(index, mFragments)
    }


}
