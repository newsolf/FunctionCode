package com.newolf.functioncode.home

import com.newolf.functioncode.R
import com.newolf.functioncode.app.base.BaseFragment

/**
 * ================================================
 * @author : NeWolf
 * @version : 1.0
 * @date :  2018/3/30
 * 描述:
 * 历史:<br/>
 * ================================================
 */
class HomeFragment : BaseFragment() {
    override fun bindLayout(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
//        BarUtils.setStatusBarColor4Drawer(activity,drawerLayout,fakeStatusBar,0,true)
//        BarUtils.setStatusBarAlpha(activity, 0, true)

    }

    override fun onResume() {
        super.onResume()
    }
}